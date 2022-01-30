package at.ac.univie.unet.a01638800.raytracer;

import at.ac.univie.unet.a01638800.raytracer.io.image.ImageReader;
import at.ac.univie.unet.a01638800.raytracer.io.image.ImageReaderException;
import at.ac.univie.unet.a01638800.raytracer.io.image.ImageWriter;
import at.ac.univie.unet.a01638800.raytracer.io.image.ImageWriterException;
import at.ac.univie.unet.a01638800.raytracer.io.scene.SceneParser;
import at.ac.univie.unet.a01638800.raytracer.io.scene.SceneParserException;
import at.ac.univie.unet.a01638800.raytracer.io.wavefront.WavefrontParser;
import at.ac.univie.unet.a01638800.raytracer.io.wavefront.WavefrontParserException;
import at.ac.univie.unet.a01638800.raytracer.scene.Scene;
import at.ac.univie.unet.a01638800.raytracer.scene.surfaces.Sphere;
import at.ac.univie.unet.a01638800.raytracer.scene.surfaces.Surface;
import at.ac.univie.unet.a01638800.raytracer.scene.surfaces.material.Material;
import at.ac.univie.unet.a01638800.raytracer.scene.surfaces.material.SolidMaterial;
import at.ac.univie.unet.a01638800.raytracer.scene.surfaces.material.TexturedMaterial;
import at.ac.univie.unet.a01638800.raytracer.xml.scene.XmlMaterialSolid;
import at.ac.univie.unet.a01638800.raytracer.xml.scene.XmlMaterialTextured;
import at.ac.univie.unet.a01638800.raytracer.xml.scene.XmlMesh;
import at.ac.univie.unet.a01638800.raytracer.xml.scene.XmlScene;
import at.ac.univie.unet.a01638800.raytracer.xml.scene.XmlSphere;
import at.ac.univie.unet.a01638800.raytracer.xml.scene.XmlSurface;
import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(final String[] args) throws SceneParserException, ImageWriterException, WavefrontParserException, ImageReaderException {
        // accept file path as input parameter
        final String filePath = args[0];

        // rest of args are effects
        final String[] effects = Arrays.copyOfRange(args, 1, args.length);

        // anti aliasing (via supersampling) as second parameter
        final int samples = getAntiAliasingSamples(effects);

        // parsers
        final SceneParser sceneParser = SceneParser.getInstance();
        final ImageWriter imageWriter = ImageWriter.getInstance();

        // paths
        final Path xmlFilePath = Path.of(filePath);
        final Path inputPath = xmlFilePath.getParent();
        final Path outputPath = Path.of("./output");

        // parse scene
        final XmlScene xmlScene = sceneParser.parseFile(xmlFilePath.toString());

        // parse surfaces
        final List<Surface> surfaces = getSurfaces(xmlScene, inputPath);

        // create scene
        final Scene scene = new Scene(xmlScene, surfaces.toArray(new Surface[0]), samples);

        // make sure the output path exists
        new File(outputPath.toString()).mkdirs();

        // write image
        imageWriter.writeImage(scene.getImage(), outputPath.resolve(xmlScene.getOutputFile()).toString());
    }

    private static int getAntiAliasingSamples(final String[] effects) {
        int samples = 1;

        for (final String effect : effects) {
            if (effect.startsWith("anti")) {
                final String[] antiAliasingParameters = effect.split("=");

                if (antiAliasingParameters.length > 1) {
                    // sample size is configurable via arguments
                    samples = Integer.parseInt(antiAliasingParameters[1]);
                } else {
                    // default sample size
                    samples = 2;
                }
            }
        }

        return samples;
    }

    private static List<Surface> getSurfaces(final XmlScene xmlScene, final Path inputPath) throws WavefrontParserException, ImageReaderException {
        final WavefrontParser wavefrontParser = WavefrontParser.getInstance();
        final ImageReader imageReader = ImageReader.getInstance();

        // spheres
        final List<Surface> surfaces = xmlScene.getSurfaces().getSurfaces().stream()
                .filter(XmlSphere.class::isInstance)
                .map(XmlSphere.class::cast)
                .map(Sphere::new)
                .collect(Collectors.toList());

        // meshes
        final List<XmlMesh> xmlMeshes =
                xmlScene.getSurfaces().getSurfaces().stream()
                        .filter(XmlMesh.class::isInstance)
                        .map(XmlMesh.class::cast)
                        .collect(Collectors.toList());

        // Wavefront parsing
        for (final XmlMesh xmlMesh : xmlMeshes) {
            final Path meshPath = inputPath.resolve(xmlMesh.getName());
            surfaces.add(wavefrontParser.parseFile(xmlMesh, meshPath.toString()));
        }

        // textures
        for (final Surface surface : surfaces) {
            final XmlSurface xmlSurface = surface.getXmlSurface();
            final XmlMaterialSolid xmlMaterialSolid = xmlSurface.getMaterialSolid();
            final XmlMaterialTextured xmlMaterialTextured = xmlSurface.getMaterialTextured();

            Material material = null;
            if (xmlMaterialSolid != null) {
                material = new SolidMaterial(xmlMaterialSolid);
            } else if (xmlMaterialTextured != null) {
                final Path texturePath = inputPath.resolve(xmlMaterialTextured.getTexture().getName());
                material = new TexturedMaterial(xmlMaterialTextured, imageReader.readImage(texturePath.toString()));
            }

            surface.setMaterial(material);
        }

        return surfaces;
    }

}

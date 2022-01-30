package at.ac.univie.unet.a01638800.raytracer;

import at.ac.univie.unet.a01638800.raytracer.io.image.ImageReader;
import at.ac.univie.unet.a01638800.raytracer.io.image.ImageReaderException;
import at.ac.univie.unet.a01638800.raytracer.io.scene.SceneParser;
import at.ac.univie.unet.a01638800.raytracer.io.scene.SceneParserException;
import at.ac.univie.unet.a01638800.raytracer.io.wavefront.WavefrontParser;
import at.ac.univie.unet.a01638800.raytracer.io.wavefront.WavefrontParserException;
import at.ac.univie.unet.a01638800.raytracer.scene.Scene;
import at.ac.univie.unet.a01638800.raytracer.scene.surfaces.Sphere;
import at.ac.univie.unet.a01638800.raytracer.scene.surfaces.Surface;
import at.ac.univie.unet.a01638800.raytracer.scene.surfaces.material.SolidMaterial;
import at.ac.univie.unet.a01638800.raytracer.scene.surfaces.material.TexturedMaterial;
import at.ac.univie.unet.a01638800.raytracer.scene.surfaces.material.Material;
import at.ac.univie.unet.a01638800.raytracer.io.image.ImageWriter;
import at.ac.univie.unet.a01638800.raytracer.io.image.ImageWriterException;
import at.ac.univie.unet.a01638800.raytracer.xml.scene.*;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(final String[] args) throws SceneParserException, ImageWriterException, WavefrontParserException, ImageReaderException {
        // accept file path as input parameter
        final String filePath = args[0];

        // parsers
        final SceneParser sceneParser = SceneParser.getInstance();
        final WavefrontParser wavefrontParser = WavefrontParser.getInstance();
        final ImageReader imageReader = ImageReader.getInstance();
        final ImageWriter imageWriter = ImageWriter.getInstance();

        // paths
        final Path xmlFilePath = Path.of(filePath);
        final Path inputPath = xmlFilePath.getParent();
        final Path outputPath = Path.of("./output");

        // parse scene
        final XmlScene xmlScene = sceneParser.parseFile(xmlFilePath.toString());

        // parse spheres
        final List<Surface> surfaces = xmlScene.getSurfaces().getSurfaces().stream()
                .filter(XmlSphere.class::isInstance)
                .map(XmlSphere.class::cast)
                .map(Sphere::new)
                .collect(Collectors.toList());

        // parse meshes
        final List<XmlMesh> xmlMeshes =
                xmlScene.getSurfaces().getSurfaces().stream()
                        .filter(XmlMesh.class::isInstance)
                        .map(XmlMesh.class::cast)
                        .collect(Collectors.toList());

        for (final XmlMesh xmlMesh : xmlMeshes) {
            final Path meshPath = inputPath.resolve(xmlMesh.getName());
            surfaces.add(wavefrontParser.parseFile(xmlMesh, meshPath.toString()));
        }

        // parse textures
        for(Surface surface : surfaces) {
            XmlSurface xmlSurface = surface.getXmlSurface();
            XmlMaterialSolid xmlMaterialSolid = xmlSurface.getMaterialSolid();
            XmlMaterialTextured xmlMaterialTextured = xmlSurface.getMaterialTextured();

            Material material = null;
            if(xmlMaterialSolid != null) {
                material = new SolidMaterial(xmlMaterialSolid);
            }
            else if(xmlMaterialTextured != null) {
                final Path texturePath = inputPath.resolve(xmlMaterialTextured.getTexture().getName());
                material = new TexturedMaterial(xmlMaterialTextured, imageReader.readImage(texturePath.toString()));
            }

            surface.setMaterial(material);
        }

        // create scene
        final Scene scene = new Scene(xmlScene, surfaces.toArray(new Surface[0]), DebugMode.NO_DEBUG);

        // make sure the output path exists
        new File(outputPath.toString()).mkdirs();

        // write image
        imageWriter.writeImage(scene.getImage(), outputPath.resolve(xmlScene.getOutputFile()).toString());
    }

}

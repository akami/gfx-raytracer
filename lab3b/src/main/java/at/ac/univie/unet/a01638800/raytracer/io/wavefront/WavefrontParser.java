package at.ac.univie.unet.a01638800.raytracer.io.wavefront;

import at.ac.univie.unet.a01638800.raytracer.geometry.Point;
import at.ac.univie.unet.a01638800.raytracer.geometry.Vector;
import at.ac.univie.unet.a01638800.raytracer.geometry.Vertex;
import at.ac.univie.unet.a01638800.raytracer.scene.surfaces.Face;
import at.ac.univie.unet.a01638800.raytracer.scene.surfaces.Mesh;
import at.ac.univie.unet.a01638800.raytracer.xml.scene.XmlSurface;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

/**
 * Parser for Wavefront (.obj) file to {@link Mesh} instances.
 * Currently only the following data/attributes is/are supported:
 * <ul>
 *     <li>v for vertices</li>
 *     <li>vn for normals</li>
 *     <li>vt for textures</li>
 *     <li>f for faces</li>
 * </ul>
 */
public class WavefrontParser {

    private static WavefrontParser INSTANCE;

    public static WavefrontParser getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new WavefrontParser();
        }

        return INSTANCE;
    }

    /**
     * Parses a Wavefront input file and converts it to a {@link Mesh}
     *
     * @param filePath the path to the Wavefront file
     * @return {@link Mesh} that can be used in the application
     * @throws WavefrontParserException whenever an issue occurs
     */
    public Mesh parseFile(final XmlSurface xmlSurface, final String filePath) throws WavefrontParserException {
        try (final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)))) {
            final List<Face> faces = new LinkedList<>();

            final List<Point> vertices = new LinkedList<>();
            final List<Vector> normals = new LinkedList<>();
            final List<Point> textures = new LinkedList<>();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                final String[] lineParts = line.split(" ");

                // unknown line types are simply ignored (e. g. 'o' for object names or '#' for comments)
                if ("v".equals(lineParts[0])) {
                    vertices.add(new Point(Double.parseDouble(lineParts[1]), Double.parseDouble(lineParts[2]), Double.parseDouble(lineParts[3])));
                } else if ("vn".equals(lineParts[0])) {
                    normals.add(new Vector(Double.parseDouble(lineParts[1]), Double.parseDouble(lineParts[2]), Double.parseDouble(lineParts[3])));
                } else if ("vt".equals(lineParts[0])) {
                    textures.add(new Point(Double.parseDouble(lineParts[1]), Double.parseDouble(lineParts[2]), 0D));
                } else if ("f".equals(lineParts[0])) {
                    final List<Vertex> combinedVertices = new LinkedList<>();

                    for (int i = 1; i < lineParts.length; i++) {
                        final String[] indices = lineParts[i].split("/");
                        final int vertexIndex = Integer.parseInt(indices[0]) - 1;
                        final int textureIndex = Integer.parseInt(indices[1]) - 1;
                        final int normalIndex = Integer.parseInt(indices[2]) - 1;

                        combinedVertices.add(new Vertex(vertices.get(vertexIndex), normals.get(normalIndex), textures.get(textureIndex)));
                    }

                    faces.add(new Face(combinedVertices.toArray(new Vertex[0])));
                }
            }

            return new Mesh(xmlSurface, faces.toArray(new Face[0]));
        } catch (final IOException ex) {
            throw new WavefrontParserException("Could not read file!", ex);
        }
    }

}

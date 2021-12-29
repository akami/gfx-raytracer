
package at.ac.univie.unet.a01638800.raytracer.scene;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for anonymous complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="background_color" type="{}color"/&amp;gt;
 *         &amp;lt;element name="camera" type="{}camera"/&amp;gt;
 *         &amp;lt;element name="lights"&amp;gt;
 *           &amp;lt;complexType&amp;gt;
 *             &amp;lt;complexContent&amp;gt;
 *               &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *                 &amp;lt;choice maxOccurs="unbounded" minOccurs="0"&amp;gt;
 *                   &amp;lt;element name="ambient_light" type="{}ambient_light"/&amp;gt;
 *                   &amp;lt;element name="point_light" type="{}point_light"/&amp;gt;
 *                   &amp;lt;element name="parallel_light" type="{}parallel_light"/&amp;gt;
 *                   &amp;lt;element name="spot_light" type="{}spot_light"/&amp;gt;
 *                 &amp;lt;/choice&amp;gt;
 *               &amp;lt;/restriction&amp;gt;
 *             &amp;lt;/complexContent&amp;gt;
 *           &amp;lt;/complexType&amp;gt;
 *         &amp;lt;/element&amp;gt;
 *         &amp;lt;element name="surfaces"&amp;gt;
 *           &amp;lt;complexType&amp;gt;
 *             &amp;lt;complexContent&amp;gt;
 *               &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *                 &amp;lt;choice maxOccurs="unbounded" minOccurs="0"&amp;gt;
 *                   &amp;lt;element name="sphere" type="{}sphere"/&amp;gt;
 *                   &amp;lt;element name="mesh" type="{}mesh"/&amp;gt;
 *                 &amp;lt;/choice&amp;gt;
 *               &amp;lt;/restriction&amp;gt;
 *             &amp;lt;/complexContent&amp;gt;
 *           &amp;lt;/complexType&amp;gt;
 *         &amp;lt;/element&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *       &amp;lt;attribute name="output_file" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "backgroundColor",
    "camera",
    "lights",
    "surfaces"
})
@XmlRootElement(name = "scene")
public class Scene {

    @XmlElement(name = "background_color", required = true)
    protected Color backgroundColor;
    @XmlElement(required = true)
    protected Camera camera;
    @XmlElement(required = true)
    protected Scene.Lights lights;
    @XmlElement(required = true)
    protected Scene.Surfaces surfaces;
    @XmlAttribute(name = "output_file", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String outputFile;

    /**
     * Gets the value of the backgroundColor property.
     * 
     * @return
     *     possible object is
     *     {@link Color }
     *     
     */
    public Color getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * Sets the value of the backgroundColor property.
     * 
     * @param value
     *     allowed object is
     *     {@link Color }
     *     
     */
    public void setBackgroundColor(Color value) {
        this.backgroundColor = value;
    }

    /**
     * Gets the value of the camera property.
     * 
     * @return
     *     possible object is
     *     {@link Camera }
     *     
     */
    public Camera getCamera() {
        return camera;
    }

    /**
     * Sets the value of the camera property.
     * 
     * @param value
     *     allowed object is
     *     {@link Camera }
     *     
     */
    public void setCamera(Camera value) {
        this.camera = value;
    }

    /**
     * Gets the value of the lights property.
     * 
     * @return
     *     possible object is
     *     {@link Scene.Lights }
     *     
     */
    public Scene.Lights getLights() {
        return lights;
    }

    /**
     * Sets the value of the lights property.
     * 
     * @param value
     *     allowed object is
     *     {@link Scene.Lights }
     *     
     */
    public void setLights(Scene.Lights value) {
        this.lights = value;
    }

    /**
     * Gets the value of the surfaces property.
     * 
     * @return
     *     possible object is
     *     {@link Scene.Surfaces }
     *     
     */
    public Scene.Surfaces getSurfaces() {
        return surfaces;
    }

    /**
     * Sets the value of the surfaces property.
     * 
     * @param value
     *     allowed object is
     *     {@link Scene.Surfaces }
     *     
     */
    public void setSurfaces(Scene.Surfaces value) {
        this.surfaces = value;
    }

    /**
     * Gets the value of the outputFile property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutputFile() {
        return outputFile;
    }

    /**
     * Sets the value of the outputFile property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutputFile(String value) {
        this.outputFile = value;
    }


    /**
     * &lt;p&gt;Java class for anonymous complex type.
     * 
     * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
     * 
     * &lt;pre&gt;
     * &amp;lt;complexType&amp;gt;
     *   &amp;lt;complexContent&amp;gt;
     *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
     *       &amp;lt;choice maxOccurs="unbounded" minOccurs="0"&amp;gt;
     *         &amp;lt;element name="ambient_light" type="{}ambient_light"/&amp;gt;
     *         &amp;lt;element name="point_light" type="{}point_light"/&amp;gt;
     *         &amp;lt;element name="parallel_light" type="{}parallel_light"/&amp;gt;
     *         &amp;lt;element name="spot_light" type="{}spot_light"/&amp;gt;
     *       &amp;lt;/choice&amp;gt;
     *     &amp;lt;/restriction&amp;gt;
     *   &amp;lt;/complexContent&amp;gt;
     * &amp;lt;/complexType&amp;gt;
     * &lt;/pre&gt;
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "lights"
    })
    public static class Lights {

        @XmlElements({
            @XmlElement(name = "ambient_light", type = AmbientLight.class),
            @XmlElement(name = "point_light", type = PointLight.class),
            @XmlElement(name = "parallel_light", type = ParallelLight.class),
            @XmlElement(name = "spot_light", type = SpotLight.class)
        })
        protected List<Light> lights;

        /**
         * Gets the value of the lights property.
         * 
         * &lt;p&gt;
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the lights property.
         * 
         * &lt;p&gt;
         * For example, to add a new item, do as follows:
         * &lt;pre&gt;
         *    getLights().add(newItem);
         * &lt;/pre&gt;
         * 
         * 
         * &lt;p&gt;
         * Objects of the following type(s) are allowed in the list
         * {@link AmbientLight }
         * {@link PointLight }
         * {@link ParallelLight }
         * {@link SpotLight }
         * 
         * 
         */
        public List<Light> getLights() {
            if (lights == null) {
                lights = new ArrayList<Light>();
            }
            return this.lights;
        }

    }


    /**
     * &lt;p&gt;Java class for anonymous complex type.
     * 
     * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
     * 
     * &lt;pre&gt;
     * &amp;lt;complexType&amp;gt;
     *   &amp;lt;complexContent&amp;gt;
     *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
     *       &amp;lt;choice maxOccurs="unbounded" minOccurs="0"&amp;gt;
     *         &amp;lt;element name="sphere" type="{}sphere"/&amp;gt;
     *         &amp;lt;element name="mesh" type="{}mesh"/&amp;gt;
     *       &amp;lt;/choice&amp;gt;
     *     &amp;lt;/restriction&amp;gt;
     *   &amp;lt;/complexContent&amp;gt;
     * &amp;lt;/complexType&amp;gt;
     * &lt;/pre&gt;
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "surfaces"
    })
    public static class Surfaces {

        @XmlElements({
            @XmlElement(name = "sphere", type = Sphere.class),
            @XmlElement(name = "mesh", type = Mesh.class)
        })
        protected List<Surface> surfaces;

        /**
         * Gets the value of the surfaces property.
         * 
         * &lt;p&gt;
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the surfaces property.
         * 
         * &lt;p&gt;
         * For example, to add a new item, do as follows:
         * &lt;pre&gt;
         *    getSurfaces().add(newItem);
         * &lt;/pre&gt;
         * 
         * 
         * &lt;p&gt;
         * Objects of the following type(s) are allowed in the list
         * {@link Sphere }
         * {@link Mesh }
         * 
         * 
         */
        public List<Surface> getSurfaces() {
            if (surfaces == null) {
                surfaces = new ArrayList<Surface>();
            }
            return this.surfaces;
        }

    }

}


package at.ac.univie.unet.a01638800.raytracer.xml.scene;

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
public class XmlScene {

    @XmlElement(name = "background_color", required = true)
    protected XmlColor backgroundColor;
    @XmlElement(required = true)
    protected XmlCamera camera;
    @XmlElement(required = true)
    protected XmlScene.XmlLights lights;
    @XmlElement(required = true)
    protected XmlScene.XmlSurfaces surfaces;
    @XmlAttribute(name = "output_file", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String outputFile;

    /**
     * Gets the value of the backgroundColor property.
     * 
     * @return
     *     possible object is
     *     {@link XmlColor }
     *     
     */
    public XmlColor getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * Sets the value of the backgroundColor property.
     * 
     * @param value
     *     allowed object is
     *     {@link XmlColor }
     *     
     */
    public void setBackgroundColor(XmlColor value) {
        this.backgroundColor = value;
    }

    /**
     * Gets the value of the camera property.
     * 
     * @return
     *     possible object is
     *     {@link XmlCamera }
     *     
     */
    public XmlCamera getCamera() {
        return camera;
    }

    /**
     * Sets the value of the camera property.
     * 
     * @param value
     *     allowed object is
     *     {@link XmlCamera }
     *     
     */
    public void setCamera(XmlCamera value) {
        this.camera = value;
    }

    /**
     * Gets the value of the lights property.
     * 
     * @return
     *     possible object is
     *     {@link XmlScene.XmlLights }
     *     
     */
    public XmlScene.XmlLights getLights() {
        return lights;
    }

    /**
     * Sets the value of the lights property.
     * 
     * @param value
     *     allowed object is
     *     {@link XmlScene.XmlLights }
     *     
     */
    public void setLights(XmlScene.XmlLights value) {
        this.lights = value;
    }

    /**
     * Gets the value of the surfaces property.
     * 
     * @return
     *     possible object is
     *     {@link XmlScene.XmlSurfaces }
     *     
     */
    public XmlScene.XmlSurfaces getSurfaces() {
        return surfaces;
    }

    /**
     * Sets the value of the surfaces property.
     * 
     * @param value
     *     allowed object is
     *     {@link XmlScene.XmlSurfaces }
     *     
     */
    public void setSurfaces(XmlScene.XmlSurfaces value) {
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
    public static class XmlLights {

        @XmlElements({
            @XmlElement(name = "ambient_light", type = XmlAmbientLight.class),
            @XmlElement(name = "point_light", type = XmlPointLight.class),
            @XmlElement(name = "parallel_light", type = XmlParallelLight.class),
            @XmlElement(name = "spot_light", type = XmlSpotLight.class)
        })
        protected List<XmlLight> lights;

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
         * {@link XmlAmbientLight }
         * {@link XmlPointLight }
         * {@link XmlParallelLight }
         * {@link XmlSpotLight }
         * 
         * 
         */
        public List<XmlLight> getLights() {
            if (lights == null) {
                lights = new ArrayList<XmlLight>();
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
    public static class XmlSurfaces {

        @XmlElements({
            @XmlElement(name = "sphere", type = XmlSphere.class),
            @XmlElement(name = "mesh", type = XmlMesh.class)
        })
        protected List<XmlSurface> surfaces;

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
         * {@link XmlSphere }
         * {@link XmlMesh }
         * 
         * 
         */
        public List<XmlSurface> getSurfaces() {
            if (surfaces == null) {
                surfaces = new ArrayList<XmlSurface>();
            }
            return this.surfaces;
        }

    }

}

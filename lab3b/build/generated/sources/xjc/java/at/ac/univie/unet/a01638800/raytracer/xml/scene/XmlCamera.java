
package at.ac.univie.unet.a01638800.raytracer.xml.scene;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for camera complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="camera"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="position" type="{}position"/&amp;gt;
 *         &amp;lt;element name="lookat" type="{}position"/&amp;gt;
 *         &amp;lt;element name="up" type="{}position"/&amp;gt;
 *         &amp;lt;element name="horizontal_fov" type="{}fov"/&amp;gt;
 *         &amp;lt;element name="resolution" type="{}resolution"/&amp;gt;
 *         &amp;lt;element name="max_bounces" type="{}max_bounces"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "camera", propOrder = {
    "position",
    "lookat",
    "up",
    "horizontalFov",
    "resolution",
    "maxBounces"
})
public class XmlCamera {

    @XmlElement(required = true)
    protected XmlPosition position;
    @XmlElement(required = true)
    protected XmlPosition lookat;
    @XmlElement(required = true)
    protected XmlPosition up;
    @XmlElement(name = "horizontal_fov", required = true)
    protected XmlFov horizontalFov;
    @XmlElement(required = true)
    protected XmlResolution resolution;
    @XmlElement(name = "max_bounces", required = true)
    protected XmlMaxBounces maxBounces;

    /**
     * Gets the value of the position property.
     * 
     * @return
     *     possible object is
     *     {@link XmlPosition }
     *     
     */
    public XmlPosition getPosition() {
        return position;
    }

    /**
     * Sets the value of the position property.
     * 
     * @param value
     *     allowed object is
     *     {@link XmlPosition }
     *     
     */
    public void setPosition(XmlPosition value) {
        this.position = value;
    }

    /**
     * Gets the value of the lookat property.
     * 
     * @return
     *     possible object is
     *     {@link XmlPosition }
     *     
     */
    public XmlPosition getLookat() {
        return lookat;
    }

    /**
     * Sets the value of the lookat property.
     * 
     * @param value
     *     allowed object is
     *     {@link XmlPosition }
     *     
     */
    public void setLookat(XmlPosition value) {
        this.lookat = value;
    }

    /**
     * Gets the value of the up property.
     * 
     * @return
     *     possible object is
     *     {@link XmlPosition }
     *     
     */
    public XmlPosition getUp() {
        return up;
    }

    /**
     * Sets the value of the up property.
     * 
     * @param value
     *     allowed object is
     *     {@link XmlPosition }
     *     
     */
    public void setUp(XmlPosition value) {
        this.up = value;
    }

    /**
     * Gets the value of the horizontalFov property.
     * 
     * @return
     *     possible object is
     *     {@link XmlFov }
     *     
     */
    public XmlFov getHorizontalFov() {
        return horizontalFov;
    }

    /**
     * Sets the value of the horizontalFov property.
     * 
     * @param value
     *     allowed object is
     *     {@link XmlFov }
     *     
     */
    public void setHorizontalFov(XmlFov value) {
        this.horizontalFov = value;
    }

    /**
     * Gets the value of the resolution property.
     * 
     * @return
     *     possible object is
     *     {@link XmlResolution }
     *     
     */
    public XmlResolution getResolution() {
        return resolution;
    }

    /**
     * Sets the value of the resolution property.
     * 
     * @param value
     *     allowed object is
     *     {@link XmlResolution }
     *     
     */
    public void setResolution(XmlResolution value) {
        this.resolution = value;
    }

    /**
     * Gets the value of the maxBounces property.
     * 
     * @return
     *     possible object is
     *     {@link XmlMaxBounces }
     *     
     */
    public XmlMaxBounces getMaxBounces() {
        return maxBounces;
    }

    /**
     * Sets the value of the maxBounces property.
     * 
     * @param value
     *     allowed object is
     *     {@link XmlMaxBounces }
     *     
     */
    public void setMaxBounces(XmlMaxBounces value) {
        this.maxBounces = value;
    }

}

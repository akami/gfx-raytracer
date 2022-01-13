
package at.ac.univie.unet.a01638800.raytracer.scene;

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
public class Camera {

    @XmlElement(required = true)
    protected Position position;
    @XmlElement(required = true)
    protected Position lookat;
    @XmlElement(required = true)
    protected Position up;
    @XmlElement(name = "horizontal_fov", required = true)
    protected Fov horizontalFov;
    @XmlElement(required = true)
    protected Resolution resolution;
    @XmlElement(name = "max_bounces", required = true)
    protected MaxBounces maxBounces;

    /**
     * Gets the value of the position property.
     * 
     * @return
     *     possible object is
     *     {@link Position }
     *     
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Sets the value of the position property.
     * 
     * @param value
     *     allowed object is
     *     {@link Position }
     *     
     */
    public void setPosition(Position value) {
        this.position = value;
    }

    /**
     * Gets the value of the lookat property.
     * 
     * @return
     *     possible object is
     *     {@link Position }
     *     
     */
    public Position getLookat() {
        return lookat;
    }

    /**
     * Sets the value of the lookat property.
     * 
     * @param value
     *     allowed object is
     *     {@link Position }
     *     
     */
    public void setLookat(Position value) {
        this.lookat = value;
    }

    /**
     * Gets the value of the up property.
     * 
     * @return
     *     possible object is
     *     {@link Position }
     *     
     */
    public Position getUp() {
        return up;
    }

    /**
     * Sets the value of the up property.
     * 
     * @param value
     *     allowed object is
     *     {@link Position }
     *     
     */
    public void setUp(Position value) {
        this.up = value;
    }

    /**
     * Gets the value of the horizontalFov property.
     * 
     * @return
     *     possible object is
     *     {@link Fov }
     *     
     */
    public Fov getHorizontalFov() {
        return horizontalFov;
    }

    /**
     * Sets the value of the horizontalFov property.
     * 
     * @param value
     *     allowed object is
     *     {@link Fov }
     *     
     */
    public void setHorizontalFov(Fov value) {
        this.horizontalFov = value;
    }

    /**
     * Gets the value of the resolution property.
     * 
     * @return
     *     possible object is
     *     {@link Resolution }
     *     
     */
    public Resolution getResolution() {
        return resolution;
    }

    /**
     * Sets the value of the resolution property.
     * 
     * @param value
     *     allowed object is
     *     {@link Resolution }
     *     
     */
    public void setResolution(Resolution value) {
        this.resolution = value;
    }

    /**
     * Gets the value of the maxBounces property.
     * 
     * @return
     *     possible object is
     *     {@link MaxBounces }
     *     
     */
    public MaxBounces getMaxBounces() {
        return maxBounces;
    }

    /**
     * Sets the value of the maxBounces property.
     * 
     * @param value
     *     allowed object is
     *     {@link MaxBounces }
     *     
     */
    public void setMaxBounces(MaxBounces value) {
        this.maxBounces = value;
    }

}

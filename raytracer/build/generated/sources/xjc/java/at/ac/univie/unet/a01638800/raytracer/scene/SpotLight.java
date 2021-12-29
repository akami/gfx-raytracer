
package at.ac.univie.unet.a01638800.raytracer.scene;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for spot_light complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="spot_light"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{}light"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="position" type="{}position"/&amp;gt;
 *         &amp;lt;element name="direction" type="{}position"/&amp;gt;
 *         &amp;lt;element name="falloff" type="{}falloff"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "spot_light", propOrder = {
    "position",
    "direction",
    "falloff"
})
public class SpotLight
    extends Light
{

    @XmlElement(required = true)
    protected Position position;
    @XmlElement(required = true)
    protected Position direction;
    @XmlElement(required = true)
    protected Falloff falloff;

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
     * Gets the value of the direction property.
     * 
     * @return
     *     possible object is
     *     {@link Position }
     *     
     */
    public Position getDirection() {
        return direction;
    }

    /**
     * Sets the value of the direction property.
     * 
     * @param value
     *     allowed object is
     *     {@link Position }
     *     
     */
    public void setDirection(Position value) {
        this.direction = value;
    }

    /**
     * Gets the value of the falloff property.
     * 
     * @return
     *     possible object is
     *     {@link Falloff }
     *     
     */
    public Falloff getFalloff() {
        return falloff;
    }

    /**
     * Sets the value of the falloff property.
     * 
     * @param value
     *     allowed object is
     *     {@link Falloff }
     *     
     */
    public void setFalloff(Falloff value) {
        this.falloff = value;
    }

}

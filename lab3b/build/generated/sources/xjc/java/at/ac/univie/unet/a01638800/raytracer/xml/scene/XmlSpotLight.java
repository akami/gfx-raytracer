
package at.ac.univie.unet.a01638800.raytracer.xml.scene;

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
public class XmlSpotLight
    extends XmlLight
{

    @XmlElement(required = true)
    protected XmlPosition position;
    @XmlElement(required = true)
    protected XmlPosition direction;
    @XmlElement(required = true)
    protected XmlFalloff falloff;

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
     * Gets the value of the direction property.
     * 
     * @return
     *     possible object is
     *     {@link XmlPosition }
     *     
     */
    public XmlPosition getDirection() {
        return direction;
    }

    /**
     * Sets the value of the direction property.
     * 
     * @param value
     *     allowed object is
     *     {@link XmlPosition }
     *     
     */
    public void setDirection(XmlPosition value) {
        this.direction = value;
    }

    /**
     * Gets the value of the falloff property.
     * 
     * @return
     *     possible object is
     *     {@link XmlFalloff }
     *     
     */
    public XmlFalloff getFalloff() {
        return falloff;
    }

    /**
     * Sets the value of the falloff property.
     * 
     * @param value
     *     allowed object is
     *     {@link XmlFalloff }
     *     
     */
    public void setFalloff(XmlFalloff value) {
        this.falloff = value;
    }

}

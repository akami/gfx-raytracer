
package at.ac.univie.unet.a01638800.raytracer.xml.scene;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for parallel_light complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="parallel_light"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{}light"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="direction" type="{}position"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "parallel_light", propOrder = {
    "direction"
})
public class XmlParallelLight
    extends XmlLight
{

    @XmlElement(required = true)
    protected XmlPosition direction;

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

}

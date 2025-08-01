
package at.ac.univie.unet.a01638800.raytracer.xml.scene;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for material_solid complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="material_solid"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{}material"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="color" type="{}color"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "material_solid", propOrder = {
    "color"
})
public class XmlMaterialSolid
    extends XmlMaterial
{

    @XmlElement(required = true)
    protected XmlColor color;

    /**
     * Gets the value of the color property.
     * 
     * @return
     *     possible object is
     *     {@link XmlColor }
     *     
     */
    public XmlColor getColor() {
        return color;
    }

    /**
     * Sets the value of the color property.
     * 
     * @param value
     *     allowed object is
     *     {@link XmlColor }
     *     
     */
    public void setColor(XmlColor value) {
        this.color = value;
    }

}

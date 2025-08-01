
package at.ac.univie.unet.a01638800.raytracer.xml.scene;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for material_textured complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="material_textured"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{}material"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="texture" type="{}texture"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "material_textured", propOrder = {
    "texture"
})
public class XmlMaterialTextured
    extends XmlMaterial
{

    @XmlElement(required = true)
    protected XmlTexture texture;

    /**
     * Gets the value of the texture property.
     * 
     * @return
     *     possible object is
     *     {@link XmlTexture }
     *     
     */
    public XmlTexture getTexture() {
        return texture;
    }

    /**
     * Sets the value of the texture property.
     * 
     * @param value
     *     allowed object is
     *     {@link XmlTexture }
     *     
     */
    public void setTexture(XmlTexture value) {
        this.texture = value;
    }

}

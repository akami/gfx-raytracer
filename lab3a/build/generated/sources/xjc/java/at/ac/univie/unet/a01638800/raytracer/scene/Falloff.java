
package at.ac.univie.unet.a01638800.raytracer.scene;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * &lt;p&gt;Java class for falloff complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="falloff"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;attribute name="alpha1" use="required" type="{http://www.w3.org/2001/XMLSchema}NMTOKEN" /&amp;gt;
 *       &amp;lt;attribute name="alpha2" use="required" type="{http://www.w3.org/2001/XMLSchema}NMTOKEN" /&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "falloff")
public class Falloff {

    @XmlAttribute(name = "alpha1", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NMTOKEN")
    protected String alpha1;
    @XmlAttribute(name = "alpha2", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NMTOKEN")
    protected String alpha2;

    /**
     * Gets the value of the alpha1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlpha1() {
        return alpha1;
    }

    /**
     * Sets the value of the alpha1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlpha1(String value) {
        this.alpha1 = value;
    }

    /**
     * Gets the value of the alpha2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlpha2() {
        return alpha2;
    }

    /**
     * Sets the value of the alpha2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlpha2(String value) {
        this.alpha2 = value;
    }

}

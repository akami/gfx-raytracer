
package at.ac.univie.unet.a01638800.raytracer.xml.scene;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * &lt;p&gt;Java class for phong complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="phong"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;attribute name="ka" use="required" type="{http://www.w3.org/2001/XMLSchema}NMTOKEN" /&amp;gt;
 *       &amp;lt;attribute name="kd" use="required" type="{http://www.w3.org/2001/XMLSchema}NMTOKEN" /&amp;gt;
 *       &amp;lt;attribute name="ks" use="required" type="{http://www.w3.org/2001/XMLSchema}NMTOKEN" /&amp;gt;
 *       &amp;lt;attribute name="exponent" use="required" type="{http://www.w3.org/2001/XMLSchema}NMTOKEN" /&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "phong")
public class XmlPhong {

    @XmlAttribute(name = "ka", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NMTOKEN")
    protected String ka;
    @XmlAttribute(name = "kd", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NMTOKEN")
    protected String kd;
    @XmlAttribute(name = "ks", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NMTOKEN")
    protected String ks;
    @XmlAttribute(name = "exponent", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NMTOKEN")
    protected String exponent;

    /**
     * Gets the value of the ka property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKa() {
        return ka;
    }

    /**
     * Sets the value of the ka property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKa(String value) {
        this.ka = value;
    }

    /**
     * Gets the value of the kd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKd() {
        return kd;
    }

    /**
     * Sets the value of the kd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKd(String value) {
        this.kd = value;
    }

    /**
     * Gets the value of the ks property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKs() {
        return ks;
    }

    /**
     * Sets the value of the ks property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKs(String value) {
        this.ks = value;
    }

    /**
     * Gets the value of the exponent property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExponent() {
        return exponent;
    }

    /**
     * Sets the value of the exponent property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExponent(String value) {
        this.exponent = value;
    }

}

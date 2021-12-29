
package at.ac.univie.unet.a01638800.raytracer.scene;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for material complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="material"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="phong" type="{}phong"/&amp;gt;
 *         &amp;lt;element name="reflectance" type="{}reflectance"/&amp;gt;
 *         &amp;lt;element name="transmittance" type="{}transmittance"/&amp;gt;
 *         &amp;lt;element name="refraction" type="{}refraction"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "material", propOrder = {
    "phong",
    "reflectance",
    "transmittance",
    "refraction"
})
@XmlSeeAlso({
    MaterialSolid.class,
    MaterialTextured.class
})
public class Material {

    @XmlElement(required = true)
    protected Phong phong;
    @XmlElement(required = true)
    protected Reflectance reflectance;
    @XmlElement(required = true)
    protected Transmittance transmittance;
    @XmlElement(required = true)
    protected Refraction refraction;

    /**
     * Gets the value of the phong property.
     * 
     * @return
     *     possible object is
     *     {@link Phong }
     *     
     */
    public Phong getPhong() {
        return phong;
    }

    /**
     * Sets the value of the phong property.
     * 
     * @param value
     *     allowed object is
     *     {@link Phong }
     *     
     */
    public void setPhong(Phong value) {
        this.phong = value;
    }

    /**
     * Gets the value of the reflectance property.
     * 
     * @return
     *     possible object is
     *     {@link Reflectance }
     *     
     */
    public Reflectance getReflectance() {
        return reflectance;
    }

    /**
     * Sets the value of the reflectance property.
     * 
     * @param value
     *     allowed object is
     *     {@link Reflectance }
     *     
     */
    public void setReflectance(Reflectance value) {
        this.reflectance = value;
    }

    /**
     * Gets the value of the transmittance property.
     * 
     * @return
     *     possible object is
     *     {@link Transmittance }
     *     
     */
    public Transmittance getTransmittance() {
        return transmittance;
    }

    /**
     * Sets the value of the transmittance property.
     * 
     * @param value
     *     allowed object is
     *     {@link Transmittance }
     *     
     */
    public void setTransmittance(Transmittance value) {
        this.transmittance = value;
    }

    /**
     * Gets the value of the refraction property.
     * 
     * @return
     *     possible object is
     *     {@link Refraction }
     *     
     */
    public Refraction getRefraction() {
        return refraction;
    }

    /**
     * Sets the value of the refraction property.
     * 
     * @param value
     *     allowed object is
     *     {@link Refraction }
     *     
     */
    public void setRefraction(Refraction value) {
        this.refraction = value;
    }

}

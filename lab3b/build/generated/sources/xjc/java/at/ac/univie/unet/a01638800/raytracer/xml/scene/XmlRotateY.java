
package at.ac.univie.unet.a01638800.raytracer.xml.scene;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * &lt;p&gt;Java class for rotateY complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="rotateY"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{}transformation"&amp;gt;
 *       &amp;lt;attribute name="theta" use="required" type="{http://www.w3.org/2001/XMLSchema}NMTOKEN" /&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "rotateY")
public class XmlRotateY
    extends XmlTransformation
{

    @XmlAttribute(name = "theta", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NMTOKEN")
    protected String theta;

    /**
     * Gets the value of the theta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTheta() {
        return theta;
    }

    /**
     * Sets the value of the theta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTheta(String value) {
        this.theta = value;
    }

}

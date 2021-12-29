
package at.ac.univie.unet.a01638800.raytracer.scene;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for surface complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="surface"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;choice&amp;gt;
 *           &amp;lt;element name="material_solid" type="{}material_solid"/&amp;gt;
 *           &amp;lt;element name="material_textured" type="{}material_textured"/&amp;gt;
 *         &amp;lt;/choice&amp;gt;
 *         &amp;lt;element name="transform"&amp;gt;
 *           &amp;lt;complexType&amp;gt;
 *             &amp;lt;complexContent&amp;gt;
 *               &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *                 &amp;lt;choice maxOccurs="unbounded" minOccurs="0"&amp;gt;
 *                   &amp;lt;element name="translate" type="{}translate"/&amp;gt;
 *                   &amp;lt;element name="scale" type="{}scale"/&amp;gt;
 *                   &amp;lt;element name="rotateX" type="{}rotateX"/&amp;gt;
 *                   &amp;lt;element name="rotateY" type="{}rotateY"/&amp;gt;
 *                   &amp;lt;element name="rotateZ" type="{}rotateZ"/&amp;gt;
 *                 &amp;lt;/choice&amp;gt;
 *               &amp;lt;/restriction&amp;gt;
 *             &amp;lt;/complexContent&amp;gt;
 *           &amp;lt;/complexType&amp;gt;
 *         &amp;lt;/element&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "surface", propOrder = {
    "materialSolid",
    "materialTextured",
    "transform"
})
@XmlSeeAlso({
    Sphere.class,
    Mesh.class
})
public abstract class Surface {

    @XmlElement(name = "material_solid")
    protected MaterialSolid materialSolid;
    @XmlElement(name = "material_textured")
    protected MaterialTextured materialTextured;
    @XmlElement(required = true)
    protected Surface.Transform transform;

    /**
     * Gets the value of the materialSolid property.
     * 
     * @return
     *     possible object is
     *     {@link MaterialSolid }
     *     
     */
    public MaterialSolid getMaterialSolid() {
        return materialSolid;
    }

    /**
     * Sets the value of the materialSolid property.
     * 
     * @param value
     *     allowed object is
     *     {@link MaterialSolid }
     *     
     */
    public void setMaterialSolid(MaterialSolid value) {
        this.materialSolid = value;
    }

    /**
     * Gets the value of the materialTextured property.
     * 
     * @return
     *     possible object is
     *     {@link MaterialTextured }
     *     
     */
    public MaterialTextured getMaterialTextured() {
        return materialTextured;
    }

    /**
     * Sets the value of the materialTextured property.
     * 
     * @param value
     *     allowed object is
     *     {@link MaterialTextured }
     *     
     */
    public void setMaterialTextured(MaterialTextured value) {
        this.materialTextured = value;
    }

    /**
     * Gets the value of the transform property.
     * 
     * @return
     *     possible object is
     *     {@link Surface.Transform }
     *     
     */
    public Surface.Transform getTransform() {
        return transform;
    }

    /**
     * Sets the value of the transform property.
     * 
     * @param value
     *     allowed object is
     *     {@link Surface.Transform }
     *     
     */
    public void setTransform(Surface.Transform value) {
        this.transform = value;
    }


    /**
     * &lt;p&gt;Java class for anonymous complex type.
     * 
     * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
     * 
     * &lt;pre&gt;
     * &amp;lt;complexType&amp;gt;
     *   &amp;lt;complexContent&amp;gt;
     *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
     *       &amp;lt;choice maxOccurs="unbounded" minOccurs="0"&amp;gt;
     *         &amp;lt;element name="translate" type="{}translate"/&amp;gt;
     *         &amp;lt;element name="scale" type="{}scale"/&amp;gt;
     *         &amp;lt;element name="rotateX" type="{}rotateX"/&amp;gt;
     *         &amp;lt;element name="rotateY" type="{}rotateY"/&amp;gt;
     *         &amp;lt;element name="rotateZ" type="{}rotateZ"/&amp;gt;
     *       &amp;lt;/choice&amp;gt;
     *     &amp;lt;/restriction&amp;gt;
     *   &amp;lt;/complexContent&amp;gt;
     * &amp;lt;/complexType&amp;gt;
     * &lt;/pre&gt;
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "transform"
    })
    public static class Transform {

        @XmlElements({
            @XmlElement(name = "translate", type = Translate.class),
            @XmlElement(name = "scale", type = Scale.class),
            @XmlElement(name = "rotateX", type = RotateX.class),
            @XmlElement(name = "rotateY", type = RotateY.class),
            @XmlElement(name = "rotateZ", type = RotateZ.class)
        })
        protected List<Transformation> transform;

        /**
         * Gets the value of the transform property.
         * 
         * &lt;p&gt;
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the transform property.
         * 
         * &lt;p&gt;
         * For example, to add a new item, do as follows:
         * &lt;pre&gt;
         *    getTransform().add(newItem);
         * &lt;/pre&gt;
         * 
         * 
         * &lt;p&gt;
         * Objects of the following type(s) are allowed in the list
         * {@link Translate }
         * {@link Scale }
         * {@link RotateX }
         * {@link RotateY }
         * {@link RotateZ }
         * 
         * 
         */
        public List<Transformation> getTransform() {
            if (transform == null) {
                transform = new ArrayList<Transformation>();
            }
            return this.transform;
        }

    }

}

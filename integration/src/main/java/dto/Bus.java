//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.01.17 at 08:59:43 AM CET 
//


package dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for bus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="bus">
 *   &lt;complexContent>
 *     &lt;extension base="{}vehicle">
 *       &lt;sequence>
 *         &lt;element name="busType" type="{}busType"/>
 *         &lt;element name="transportType" type="{}transportType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "bus", propOrder = {
    "busType",
    "transportType"
})
public class Bus
    extends Vehicle
{

    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected BusType busType;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected TransportType transportType;

    /**
     * Gets the value of the busType property.
     * 
     * @return
     *     possible object is
     *     {@link BusType }
     *     
     */
    public BusType getBusType() {
        return busType;
    }

    /**
     * Sets the value of the busType property.
     * 
     * @param value
     *     allowed object is
     *     {@link BusType }
     *     
     */
    public void setBusType(BusType value) {
        this.busType = value;
    }

    /**
     * Gets the value of the transportType property.
     * 
     * @return
     *     possible object is
     *     {@link TransportType }
     *     
     */
    public TransportType getTransportType() {
        return transportType;
    }

    /**
     * Sets the value of the transportType property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransportType }
     *     
     */
    public void setTransportType(TransportType value) {
        this.transportType = value;
    }

}

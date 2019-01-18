package utils;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Converter {
    public static XMLGregorianCalendar DateToGregorianCalendar(Date date) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        XMLGregorianCalendar xmlGregorianCalendar = null;
        gregorianCalendar.setTime(date);
        try {
            xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
        return xmlGregorianCalendar;
    }
}
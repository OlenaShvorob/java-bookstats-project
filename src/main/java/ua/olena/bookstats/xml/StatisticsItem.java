package ua.olena.bookstats.xml;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Один елемент статистики:
 * <item>
 *   <value>Romance</value>
 *   <count>2</count>
 * </item>
 */
@XmlType(propOrder = {"value", "count"})
public class StatisticsItem {

    private String value;
    private int count;

    // Порожній конструктор потрібен JAXB
    public StatisticsItem() {
    }

    public StatisticsItem(String value, int count) {
        this.value = value;
        this.count = count;
    }

    @XmlElement(name = "value")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @XmlElement(name = "count")
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

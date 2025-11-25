package ua.olena.bookstats.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Кореневий елемент XML:
 *
 * <statistics>
 *   <item> ... </item>
 *   <item> ... </item>
 * </statistics>
 */
@XmlRootElement(name = "statistics")
@XmlAccessorType(XmlAccessType.FIELD)
public class StatisticsData {

    @XmlElement(name = "item")
    private List<StatisticsItem> items = new ArrayList<>();

    public StatisticsData() {
    }

    public StatisticsData(List<StatisticsItem> items) {
        this.items = items;
    }

    public List<StatisticsItem> getItems() {
        return items;
    }

    public void setItems(List<StatisticsItem> items) {
        this.items = items;
    }
}

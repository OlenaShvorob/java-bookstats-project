package ua.olena.bookstats.xml;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import ua.olena.bookstats.xml.StatisticsData;
import ua.olena.bookstats.xml.StatisticsItem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Створює XML-файл statistics_by_{attribute}.xml у вказаній папці.
 */
public class XmlStatisticsWriter {

    /**
     * Записує статистику у XML-файл.
     *
     * @param stats      мапа: значення атрибуту -> кількість
     * @param attribute  назва атрибуту (author / genre / year_published)
     * @param outputDir  папка, куди зберегти файл (наприклад: Path.of("src/main/resources/output"))
     */
    public void writeStatistics(Map<String, Integer> stats,
                                String attribute,
                                Path outputDir) throws JAXBException, IOException {

        // 1. Переконуємось, що папка існує
        Files.createDirectories(outputDir);

        // 2. Готуємо ім'я файлу: statistics_by_genre.xml, statistics_by_author.xml, ...
        String fileName = "statistics_by_" + attribute + ".xml";
        File outFile = outputDir.resolve(fileName).toFile();

        // 3. Перетворюємо Map -> List<StatisticsItem>, сортуємо за count (від більшого до меншого)
        List<StatisticsItem> items = new ArrayList<>();
        stats.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder()))
                .forEach(entry -> items.add(new StatisticsItem(entry.getKey(), entry.getValue())));

        StatisticsData data = new StatisticsData(items);

        // 4. Налаштовуємо JAXB і маршалимо (записуємо) у файл
        JAXBContext context = JAXBContext.newInstance(StatisticsData.class);
        Marshaller marshaller = context.createMarshaller();

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(data, outFile);
    }

    /**
     * Зручний варіант: передати шлях як String.
     */
    public void writeStatistics(Map<String, Integer> stats,
                                String attribute,
                                String outputDir) throws JAXBException, IOException {
        writeStatistics(stats, attribute, Path.of(outputDir));
    }
}

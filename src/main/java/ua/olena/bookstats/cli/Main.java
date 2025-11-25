package ua.olena.bookstats.cli;

import jakarta.xml.bind.JAXBException;
import ua.olena.bookstats.model.Book;
import ua.olena.bookstats.parser.JsonBookParser;
import ua.olena.bookstats.stats.BookStatisticsCalculator;
import ua.olena.bookstats.xml.XmlStatisticsWriter;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        String folderPath = args.length >= 1
                ? args[0]
                : "src/main/resources/data";

        String attributeFromArgs = args.length >= 2
                ? args[1]
                : null;

        System.out.println("Папка з JSON-файлами: " + folderPath);
        if (attributeFromArgs != null) {
            System.out.println("Атрибут із аргументів: " + attributeFromArgs);
        } else {
            System.out.println("Атрибут не передано — за замовчуванням будуть оброблені: author, genre, year_published");
        }

        Path folder = Paths.get(folderPath);
        JsonBookParser parser = new JsonBookParser();

        int[] threadCounts = {1, 2, 4, 8};
        List<Book> booksForStats = null;

        System.out.println("\n=== Порівняння швидкодії (парсинг JSON) ===");
        for (int threads : threadCounts) {
            try {
                long start = System.currentTimeMillis();
                List<Book> books = parser.parseFolderWithThreads(folder, threads);
                long duration = System.currentTimeMillis() - start;

                System.out.println(
                        "Потоків: " + threads +
                                " | Книг: " + books.size() +
                                " | Час: " + duration + " ms"
                );

                if (booksForStats == null) {
                    booksForStats = books;
                }

            } catch (IOException e) {
                System.out.println("Помилка при парсингу у " + threads + " поток(ах): " + e.getMessage());
                e.printStackTrace();
                return;
            }
        }

        if (booksForStats == null || booksForStats.isEmpty()) {
            System.out.println("Не вдалося завантажити книги для побудови статистики.");
            return;
        }

        System.out.println("\nЗагальна кількість книг (для статистики): " + booksForStats.size());

        BookStatisticsCalculator calculator = new BookStatisticsCalculator();
        XmlStatisticsWriter xmlWriter = new XmlStatisticsWriter();
        Path outputDir = Paths.get("src/main/resources/output");

        try {
            if (attributeFromArgs != null) {
                generateAndWriteStats(booksForStats, attributeFromArgs, calculator, xmlWriter, outputDir);
            } else {
                String[] attributes = {"author", "genre", "year_published"};
                for (String attr : attributes) {
                    generateAndWriteStats(booksForStats, attr, calculator, xmlWriter, outputDir);
                }
            }

            System.out.println("\nГотово! XML-файли зі статистикою збережено у: " +
                    outputDir.toAbsolutePath());

        } catch (Exception e) {
            System.out.println("Помилка під час генерації статистики: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void generateAndWriteStats(
            List<Book> books,
            String attribute,
            BookStatisticsCalculator calculator,
            XmlStatisticsWriter xmlWriter,
            Path outputDir
    ) throws IOException, JAXBException {

        Map<String, Integer> stats = calculator.calculate(books, attribute);

        System.out.println(
                "\nАтрибут: " + attribute +
                        " | Кількість різних значень: " + stats.size()
        );

        xmlWriter.writeStatistics(stats, attribute, outputDir);
    }
}

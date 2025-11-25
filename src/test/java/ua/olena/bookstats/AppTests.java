package ua.olena.bookstats;

import org.junit.jupiter.api.Test;

import ua.olena.bookstats.model.Book;
import ua.olena.bookstats.parser.JsonBookParser;
import ua.olena.bookstats.stats.Attribute;
import ua.olena.bookstats.stats.BookStatisticsCalculator;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class AppTests {

    // Допоміжний метод: читаємо всі книги з resources/data
    private List<Book> readAllBooks() throws Exception {
        Path folder = Path.of("src/main/resources/data");
        JsonBookParser parser = new JsonBookParser();
        return parser.parseFolderWithThreads(folder, 4);
    }

    // === ТЕСТ №1 ===
    // Перевіряємо, що читаються всі 9 книг
    @Test
    void parseFolder_readsAllBooks() throws Exception {
        List<Book> books = readAllBooks();

        assertEquals(9, books.size(), "Кількість книг має бути 9");
    }

    // === ТЕСТ №2 ===
    // Перевіряємо, що унікальних жанрів = 10
    @Test
    void calculateGenreStatistics_has10DifferentGenres() throws Exception {
        List<Book> books = readAllBooks();

        BookStatisticsCalculator calc = new BookStatisticsCalculator();
        Map<String, Integer> stats = calc.calculate(books, Attribute.GENRE);

        assertEquals(10, stats.size(), "Кількість жанрів має бути 10");
    }

    // === ТЕСТ №3 ===
    // Перевірка продуктивності: 4 потоки не повільніші за 1
    @Test
    void parsingWith1And4Threads_givesSameResult() throws Exception {
        Path folder = Path.of("src/main/resources/data");
        JsonBookParser parser = new JsonBookParser();

        // 1 потік
        long start1 = System.currentTimeMillis();
        List<Book> books1 = parser.parseFolderWithThreads(folder, 1);
        long time1 = System.currentTimeMillis() - start1;

        // 4 потоки
        long start4 = System.currentTimeMillis();
        List<Book> books4 = parser.parseFolderWithThreads(folder, 4);
        long time4 = System.currentTimeMillis() - start4;

        // Кількість книг має бути однакова
        assertEquals(books1.size(), books4.size(),
                "Кількість результатів при 1 і 4 потоках повинна співпадати");

        // 4 потоки не можуть бути повільнішими за 1
        assertTrue(time4 <= time1,
                "4 потоки не повинні бути повільнішими за 1 потік");
    }
    @Test
    void calculate_throwsExceptionForUnknownAttribute() {
        BookStatisticsCalculator calc = new BookStatisticsCalculator();
        List<Book> books = List.of();

        assertThrows(IllegalArgumentException.class, () ->
                calc.calculate(books, "wrong_attribute"));
    }

}

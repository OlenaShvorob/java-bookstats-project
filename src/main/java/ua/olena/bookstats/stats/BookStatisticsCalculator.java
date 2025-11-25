package ua.olena.bookstats.stats;

import ua.olena.bookstats.model.Book;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Обчислення статистики за книгами залежно від атрибута.
 */
public class BookStatisticsCalculator {

    /**
     * Основний метод: статистика за enum-атрибутом.
     */
    public Map<String, Integer> calculate(List<Book> books, Attribute attribute) {

        Map<String, Integer> stats = new HashMap<>();

        for (Book book : books) {
            switch (attribute) {

                case AUTHOR:
                    increment(stats, book.getAuthor());
                    break;

                case GENRE:
                    if (book.getGenre() != null) {
                        for (String part : book.getGenre().split(",")) {
                            increment(stats, part.trim());
                        }
                    }
                    break;

                case YEAR_PUBLISHED:
                    increment(stats, String.valueOf(book.getYearPublished()));
                    break;
            }
        }

        return stats;
    }

    /**
     * Варіант для сумісності зі строками.
     */
    public Map<String, Integer> calculate(List<Book> books, String attributeName) {
        Attribute attribute = Attribute.fromString(attributeName);
        return calculate(books, attribute);
    }

    /**
     * Збільшення лічильника для значення.
     */
    private void increment(Map<String, Integer> stats, String key) {
        if (key == null || key.isEmpty()) return;
        stats.merge(key, 1, Integer::sum);
    }
}

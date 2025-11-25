package ua.olena.bookstats.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Модель книги, що відповідає JSON-структурі:
 * {
 *   "title": "...",
 *   "author": "...",
 *   "year_published": 1997,
 *   "genre": "..."
 * }
 */
public class Book {

    private String title;
    private String author;

    @JsonProperty("year_published")
    private int yearPublished;

    private String genre;

    // Порожній конструктор потрібен для Jackson
    public Book() {
    }

    public Book(String title, String author, int yearPublished, String genre) {
        this.title = title;
        this.author = author;
        this.yearPublished = yearPublished;
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYearPublished() {
        return yearPublished;
    }

    public void setYearPublished(int yearPublished) {
        this.yearPublished = yearPublished;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}

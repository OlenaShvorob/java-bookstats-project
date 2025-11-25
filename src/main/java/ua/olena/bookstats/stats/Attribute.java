package ua.olena.bookstats.stats;

/**
 * Атрибути, за якими рахується статистика.
 */
public enum Attribute {

    AUTHOR("author"),
    GENRE("genre"),
    YEAR_PUBLISHED("year_published");

    private final String jsonFieldName;

    Attribute(String jsonFieldName) {
        this.jsonFieldName = jsonFieldName;
    }

    public String getJsonFieldName() {
        return jsonFieldName;
    }

    /**
     * Перетворення текстової назви атрибута у відповідний enum.
     */
    public static Attribute fromString(String name) {
        switch (name) {
            case "author":
                return AUTHOR;
            case "genre":
                return GENRE;
            case "year_published":
                return YEAR_PUBLISHED;
            default:
                throw new IllegalArgumentException("Unknown attribute: " + name);
        }
    }
}

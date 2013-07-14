package nl.jworks.epub.logic.strategy.isbn10;

import nl.jworks.epub.logic.strategy.Score;

public class Isbn10Score extends Score<String> {

    private final String schema;

    public Isbn10Score(String schema, String value, Class source) {
        super(value, source);

        this.schema = schema;
    }

    @Override
    public double getScore() {
        if (getValue() == null || getValue().length() != 10) {
            return 0;
        }

        if (schema.equalsIgnoreCase("isbn")) {
            return 1;
        } else {
            return 0.5;
        }
    }
}


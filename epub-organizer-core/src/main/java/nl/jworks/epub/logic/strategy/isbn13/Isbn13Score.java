package nl.jworks.epub.logic.strategy.isbn13;

import nl.jworks.epub.logic.strategy.Score;

public class Isbn13Score extends Score<String> {

    private String schema;

    public Isbn13Score(String schema, String value, Class source) {
        super(value, source);

        this.schema = schema;
    }

    @Override
    public double getScore() {
        if (getValue() == null || getValue().length() != 13) {
            return 0;
        }

        if (schema.equalsIgnoreCase("isbn")) {
            return 1;
        } else {
            return 0.5;
        }
    }
}


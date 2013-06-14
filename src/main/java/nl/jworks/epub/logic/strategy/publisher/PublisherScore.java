package nl.jworks.epub.logic.strategy.publisher;

import nl.jworks.epub.annotations.NotNull;
import nl.jworks.epub.logic.strategy.Score;

public class PublisherScore extends Score<String> {

    public PublisherScore(@NotNull String value, @NotNull Class source) {
        super(value, source);
    }

    @Override
    public double getScore() {
        return getValue().length();
    }
}

package nl.jworks.epub.logic.strategy.summary;

import nl.jworks.epub.annotations.NotNull;
import nl.jworks.epub.logic.strategy.Score;

public class SummaryScore extends Score<String> {

    public SummaryScore(@NotNull String value, @NotNull Class source) {
        super(value, source);
    }

    @Override
    public double getScore() {
        return getValue().length();
    }
}
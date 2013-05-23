package nl.jworks.epub.logic.strategy.title;

import nl.jworks.epub.logic.strategy.Score;

public class TitleScore extends Score<String> {

    public TitleScore(String value, Class source) {
        super(value, source);
    }

    @Override
    public double getScore() {
        return getValue().length();
    }
}

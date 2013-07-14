package nl.jworks.epub.logic.strategy.cover;

import nl.jworks.epub.annotations.NotNull;
import nl.jworks.epub.domain.Binary;
import nl.jworks.epub.logic.strategy.Score;

public class CoverScore extends Score<Binary> {

    public CoverScore(Binary value, @NotNull Class source) {
        super(value, source);
    }

    @Override
    public double getScore() {
        return 0;
    }
}
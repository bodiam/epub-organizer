package nl.jworks.epub.logic.strategy.publicationdate;

import nl.jworks.epub.annotations.NotNull;
import nl.jworks.epub.logic.strategy.Score;

import java.util.Date;

public class PublicationDateScore extends Score<Date> {

    public PublicationDateScore(Date value, @NotNull Class source) {
        super(value, source);
    }

    @Override
    public double getScore() {
        return 0;
    }
}

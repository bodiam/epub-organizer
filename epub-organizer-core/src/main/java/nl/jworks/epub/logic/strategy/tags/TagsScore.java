package nl.jworks.epub.logic.strategy.tags;

import nl.jworks.epub.annotations.NotNull;
import nl.jworks.epub.domain.Tag;
import nl.jworks.epub.logic.strategy.Score;

import java.util.List;

public class TagsScore extends Score<List<Tag>> {

    public TagsScore(@NotNull List<Tag> value, @NotNull Class source) {
        super(value, source);
    }

    @Override
    public double getScore() {
        return getValue().size();
    }
}

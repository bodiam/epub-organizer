package nl.jworks.epub.logic.strategy;

import nl.jworks.epub.annotations.NotNull;

public interface ScoreStrategy<T> {

    @NotNull
    T score(BookContext context);
}

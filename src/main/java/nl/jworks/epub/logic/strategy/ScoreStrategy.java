package nl.jworks.epub.logic.strategy;

public interface ScoreStrategy<T> {

    T score(BookContext context);
}

package nl.jworks.epub.logic.strategy;

import java.io.File;

public interface ScoreStrategy<T> {

    T score(File file);
}

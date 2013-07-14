package nl.jworks.epub.logic.strategy.language;

import nl.jworks.epub.logic.strategy.AbstractScorer;
import nl.jworks.epub.logic.strategy.ScoreStrategy;

import java.util.Arrays;
import java.util.List;

public class LanguageScorer extends AbstractScorer<LanguageScore> {

    @Override
    public List<? extends ScoreStrategy<LanguageScore>> getStrategies() {
        return Arrays.asList(
                new MetaDataLanguageScoreStrategy(),
                new BookContentLanguageScoreStrategy()
        );
    }
}

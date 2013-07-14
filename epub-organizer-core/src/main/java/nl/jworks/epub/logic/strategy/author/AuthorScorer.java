package nl.jworks.epub.logic.strategy.author;

import nl.jworks.epub.logic.strategy.AbstractScorer;
import nl.jworks.epub.logic.strategy.ScoreStrategy;

import java.util.Arrays;
import java.util.List;

public class AuthorScorer extends AbstractScorer<AuthorScore> {

    @Override
    public List<? extends ScoreStrategy<AuthorScore>> getStrategies() {
        return Arrays.asList(
                new FileNameAuthorScoreStrategy(),
                new MetaDataAuthorScoreStrategy(),
                new ParentDirectoryAuthorScoreStrategy());
    }

}

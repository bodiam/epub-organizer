package nl.jworks.epub.logic.strategy.author;

import com.google.common.collect.Ordering;
import com.google.common.primitives.Doubles;
import nl.jworks.epub.logic.strategy.BookContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AuthorScorer {

    private static Logger log = LoggerFactory.getLogger(AuthorScorer.class);

    private List<AuthorScoreStrategy> authorStrategies = Arrays.asList(
            new FileNameAuthorScoreStrategy(),
            new MetaDataAuthorScoreStrategy(),
            new ParentDirectoryAuthorScoreStrategy()
    );

    public AuthorScore determineBestScore(BookContext context) {
        List<AuthorScore> scores = new ArrayList<>();

        for (AuthorScoreStrategy strategy : authorStrategies) {
            AuthorScore authorScore = strategy.score(context);

            log.debug("{} : {} = {}", authorScore.getSource(), authorScore.getValue(), authorScore.getScore());

            scores.add(authorScore);
        }

        Ordering<AuthorScore> o = new Ordering<AuthorScore>() {
            @Override
            public int compare(AuthorScore left, AuthorScore right) {
                return Doubles.compare(left.getScore(), right.getScore());
            }
        };

        AuthorScore max = o.max(scores);

        log.info("Best author {} with score {} determined using {}", max.getValue(), max.getScore(), max.getSource().getSimpleName());

        return max;
    }
}

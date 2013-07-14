package nl.jworks.epub.logic.strategy;

import com.google.common.collect.Ordering;
import com.google.common.primitives.Doubles;
import nl.jworks.epub.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractScorer<T extends Score> {

    private static Logger log = LoggerFactory.getLogger(AbstractScorer.class);

    @NotNull
    public T determineBestScore(BookImportContext context) {
        List<T> scores = new ArrayList<>();

        List<? extends ScoreStrategy<T>> strategies = getStrategies();
        for (ScoreStrategy<T> strategy : strategies) {
            T score = strategy.score(context);

            log.debug("{} : {} = {}", score.getSource(), score.getValue(), score.getScore());

            scores.add(score);
        }

/**
        List<T> scores = Lists.transform(getStrategies(), new Function<ScoreStrategy<T>, T>() {
            @Override
            public T apply(ScoreStrategy<T> strategy) {
                return strategy.score(context);
            }
        });
  */

        Ordering<T> o = new Ordering<T>() {
            @Override
            public int compare(T left, T right) {
                return Doubles.compare(left.getScore(), right.getScore());
            }
        };

        return o.max(scores);
    }

    public abstract List<? extends ScoreStrategy<T>> getStrategies();

}

package nl.jworks.epub.logic.strategy.title;

import com.google.common.collect.Ordering;
import com.google.common.primitives.Doubles;
import nl.jworks.epub.logic.strategy.BookContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TitleScorer {

    private static Logger log = LoggerFactory.getLogger(TitleScorer.class);

    private List<TitleScoreStrategy> strategies = Arrays.asList(
            new FileNameTitleScoreStrategy(),
            new MetaDataTitleScoreStrategy()
    );

    public TitleScore determineBestScore(BookContext context) {
        List<TitleScore> scores = new ArrayList<>();

        for (TitleScoreStrategy strategy : strategies) {
            TitleScore titleScore = strategy.score(context);

            log.debug("{} : {} = {}", titleScore.getSource(), titleScore.getValue(), titleScore.getScore());

            scores.add(titleScore);
        }

        Ordering<TitleScore> o = new Ordering<TitleScore>() {
            @Override
            public int compare(TitleScore left, TitleScore right) {
                return Doubles.compare(left.getScore(), right.getScore());
            }
        };

        return o.max(scores);
    }

}

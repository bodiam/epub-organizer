package nl.jworks.epub.logic.strategy.isbn10;

import com.google.common.collect.Ordering;
import com.google.common.primitives.Doubles;
import nl.jworks.epub.logic.strategy.BookContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Isbn10Scorerer {

    private static Logger log = LoggerFactory.getLogger(Isbn10Scorerer.class);

    private List<? extends Isbn10ScoreStrategy> strategies = Arrays.asList(
            new MetaDataIsbn10ScoreStrategy()
    );

    public Isbn10Score determineBestScore(BookContext context) {
        List<Isbn10Score> scores = new ArrayList<>();

        for (Isbn10ScoreStrategy strategy : strategies) {
            Isbn10Score isbn10Score = strategy.score(context);

            log.debug("{} : {} = {}", isbn10Score.getSource(), isbn10Score.getValue(), isbn10Score.getScore());

            scores.add(isbn10Score);
        }

        Ordering<Isbn10Score> o = new Ordering<Isbn10Score>() {
            @Override
            public int compare(Isbn10Score left, Isbn10Score right) {
                return Doubles.compare(left.getScore(), right.getScore());
            }
        };

        return o.max(scores);
    }


}

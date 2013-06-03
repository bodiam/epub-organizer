package nl.jworks.epub.logic.strategy.isbn13;

import com.google.common.collect.Ordering;
import com.google.common.primitives.Doubles;
import nl.jworks.epub.logic.strategy.BookContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Isbn13Scorerer {

    private static Logger log = LoggerFactory.getLogger(Isbn13Scorerer.class);

    private List<? extends Isbn13ScoreStrategy> strategies = Arrays.asList(
            new MetaDataIsbn13ScoreStrategy()
    );

    public Isbn13Score determineBestScore(BookContext context) {
        List<Isbn13Score> scores = new ArrayList<>();

        for (Isbn13ScoreStrategy strategy : strategies) {
            Isbn13Score isbn10Score = strategy.score(context);

            log.debug("{} : {} = {}", isbn10Score.getSource(), isbn10Score.getValue(), isbn10Score.getScore());

            scores.add(isbn10Score);
        }

        Ordering<Isbn13Score> o = new Ordering<Isbn13Score>() {
            @Override
            public int compare(Isbn13Score left, Isbn13Score right) {
                return Doubles.compare(left.getScore(), right.getScore());
            }
        };

        return o.max(scores);
    }


}

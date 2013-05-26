package nl.jworks.epub.logic.strategy.language;

import com.google.common.collect.Ordering;
import com.google.common.primitives.Doubles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LanguageScorer {

    private static Logger log = LoggerFactory.getLogger(LanguageScorer.class);

    private List<LanguageScoreStrategy> languageStrategies = Arrays.asList(
            new MetaDataLanguageScoreStrategy(),
            new BookContentLanguageScoreStrategy()
    );


    public LanguageScore determineBestScore(File input) {
        List<LanguageScore> scores = new ArrayList<>();

        for (LanguageScoreStrategy strategy : languageStrategies) {
            LanguageScore languageScore = strategy.score(input);

            log.debug("{} : {} = {}", languageScore.getSource(), languageScore.getValue(), languageScore.getScore());

            scores.add(languageScore);
        }

        Ordering<LanguageScore> o = new Ordering<LanguageScore>() {
            @Override
            public int compare(LanguageScore left, LanguageScore right) {
                return Doubles.compare(left.getScore(), right.getScore());
            }
        };

        LanguageScore max = o.max(scores);

        log.info("Best language {} with score {} determined using {}", max.getValue(), max.getScore(), max.getSource().getSimpleName());

        return max;
    }
}

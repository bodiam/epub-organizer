package nl.jworks.epub.logic.strategy.language;

import nl.jworks.epub.logic.strategy.BookContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BookContentLanguageScoreStrategy implements LanguageScoreStrategy {

    private static Logger log = LoggerFactory.getLogger(BookContentLanguageScoreStrategy.class);

    @Override
    public LanguageScore score(BookContext context) {
        try {
            // TODO implement this
            return new LanguageScore("", BookContentLanguageScoreStrategy.class);

        } catch (Exception e) {
            log.error("Could not determine score for {}", context);

            return new LanguageScore("", BookContentLanguageScoreStrategy.class);
        }
    }


}

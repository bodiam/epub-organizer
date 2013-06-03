package nl.jworks.epub.logic.strategy.language;

import nl.jworks.epub.annotations.NotNull;
import nl.jworks.epub.logic.strategy.BookImportContext;
import nl.jworks.epub.logic.strategy.ScoreStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BookContentLanguageScoreStrategy implements ScoreStrategy<LanguageScore> {

    private static Logger log = LoggerFactory.getLogger(BookContentLanguageScoreStrategy.class);

    @NotNull
    @Override
    public LanguageScore score(BookImportContext context) {
        try {
            // TODO implement this
            return new LanguageScore("", BookContentLanguageScoreStrategy.class);

        } catch (Exception e) {
            log.error("Could not determine score for {}", context);

            return new LanguageScore("", BookContentLanguageScoreStrategy.class);
        }
    }


}

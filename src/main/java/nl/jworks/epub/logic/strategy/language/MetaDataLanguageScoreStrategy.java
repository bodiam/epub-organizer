package nl.jworks.epub.logic.strategy.language;

import nl.jworks.epub.logic.strategy.BookContext;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Metadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MetaDataLanguageScoreStrategy implements LanguageScoreStrategy {

    private static Logger log = LoggerFactory.getLogger(MetaDataLanguageScoreStrategy.class);

    @Override
    public LanguageScore score(BookContext context) {
        try {
            Book epubBook = context.getEpubBook();
            Metadata metadata = epubBook.getMetadata();
            String language = metadata.getLanguage();

            return new LanguageScore(language, MetaDataLanguageScoreStrategy.class);

        } catch (Exception e) {
            log.error("Could not determine score for {}", context);

            return new LanguageScore("", MetaDataLanguageScoreStrategy.class);
        }
    }
}

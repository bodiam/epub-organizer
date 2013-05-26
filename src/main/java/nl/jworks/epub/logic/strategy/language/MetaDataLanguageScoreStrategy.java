package nl.jworks.epub.logic.strategy.language;

import nl.siegmann.epublib.epub.EpubReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;

public class MetaDataLanguageScoreStrategy implements LanguageScoreStrategy {

    private static Logger log = LoggerFactory.getLogger(MetaDataLanguageScoreStrategy.class);

    @Override
    public LanguageScore score(File source) {
        try {
            // read epub file
            EpubReader epubReader = new EpubReader();
            nl.siegmann.epublib.domain.Book epubBook = epubReader.readEpub(new FileInputStream(source));

            String language = epubBook.getMetadata().getLanguage();

            return new LanguageScore(language, MetaDataLanguageScoreStrategy.class);

        } catch (Exception e) {
            log.error("Could not determine score for {}", source);

            return new LanguageScore("", MetaDataLanguageScoreStrategy.class);
        }
    }
}

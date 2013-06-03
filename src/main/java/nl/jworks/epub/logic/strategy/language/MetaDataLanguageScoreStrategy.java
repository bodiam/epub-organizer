package nl.jworks.epub.logic.strategy.language;

import nl.jworks.epub.annotations.NotNull;
import nl.jworks.epub.logic.strategy.BookImportContext;
import nl.jworks.epub.logic.strategy.ScoreStrategy;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Metadata;

public class MetaDataLanguageScoreStrategy implements ScoreStrategy<LanguageScore> {

    @NotNull @Override
    public LanguageScore score(BookImportContext context) {
        Book epubBook = context.getEpubBook();
        Metadata metadata = epubBook.getMetadata();
        String language = metadata.getLanguage();

        return new LanguageScore(language, MetaDataLanguageScoreStrategy.class);
    }
}

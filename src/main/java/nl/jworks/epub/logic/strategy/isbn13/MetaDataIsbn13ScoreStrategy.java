package nl.jworks.epub.logic.strategy.isbn13;

import nl.jworks.epub.annotations.NotNull;
import nl.jworks.epub.logic.strategy.BookImportContext;
import nl.jworks.epub.logic.strategy.ScoreStrategy;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Identifier;
import nl.siegmann.epublib.domain.Metadata;

import java.util.List;

public class MetaDataIsbn13ScoreStrategy implements ScoreStrategy<Isbn13Score> {

    public static final int ISBN_LENGTH = 13;

    @NotNull @Override
    public Isbn13Score score(BookImportContext context) {
        Book epubBook = context.getEpubBook();
        Metadata metadata = epubBook.getMetadata();

        Isbn13Score isbn13UsingScheme = getIsbn13(metadata);
        if(isbn13UsingScheme == null) {
            return new Isbn13Score("", "", MetaDataIsbn13ScoreStrategy.class);
        }
        return isbn13UsingScheme;
    }

    private Isbn13Score getIsbn13(Metadata metadata) {
        List<Identifier> identifiers = metadata.getIdentifiers();
        for (Identifier identifier : identifiers) {
            if (identifier.getValue().length() == ISBN_LENGTH) {
                return new Isbn13Score(identifier.getScheme(), identifier.getValue(), MetaDataIsbn13ScoreStrategy.class);
            }
        }
        return null;
    }
}

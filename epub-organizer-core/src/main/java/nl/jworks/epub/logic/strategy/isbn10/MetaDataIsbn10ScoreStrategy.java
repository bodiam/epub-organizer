package nl.jworks.epub.logic.strategy.isbn10;

import nl.jworks.epub.annotations.NotNull;
import nl.jworks.epub.logic.strategy.BookImportContext;
import nl.jworks.epub.logic.strategy.ScoreStrategy;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Identifier;
import nl.siegmann.epublib.domain.Metadata;

import java.util.List;

public class MetaDataIsbn10ScoreStrategy implements ScoreStrategy<Isbn10Score> {

    public static final int ISBN_LENGTH = 10;

    @NotNull @Override
    public Isbn10Score score(BookImportContext context) {
        Book epubBook = context.getEpubBook();
        Metadata metadata = epubBook.getMetadata();

        Isbn10Score isbn10UsingScheme = getIsbn10(metadata);
        if (isbn10UsingScheme == null) {
            return new Isbn10Score("", "", MetaDataIsbn10ScoreStrategy.class);
        }
        return isbn10UsingScheme;
    }

    private Isbn10Score getIsbn10(Metadata metadata) {
        List<Identifier> identifiers = metadata.getIdentifiers();
        for (Identifier identifier : identifiers) {
            if (identifier.getValue().length() == ISBN_LENGTH) {
                return new Isbn10Score(identifier.getScheme(), identifier.getValue(), MetaDataIsbn10ScoreStrategy.class);
            }
        }
        return null;
    }
}

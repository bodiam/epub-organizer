package nl.jworks.epub.logic.strategy.author;

import nl.jworks.epub.domain.Author;
import nl.jworks.epub.logic.strategy.BookImportContext;
import nl.jworks.epub.logic.strategy.Score;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class MetaDataAuthorScoreStrategyTest {

    @Test
    public void extractAuthorFromMetaData() {
        File file = new File("src/test/resources/epubs/Alexandre Dumas - The countess of Charney.epub");

        BookImportContext bookImportContext = new BookImportContext(file);

        Score<List<Author>> authorScores = new MetaDataAuthorScoreStrategy().score(bookImportContext);

        List<Author> authors = authorScores.getValue();
        double value = authorScores.getScore();

        assertEquals(1.0, value);
        assertEquals(new Author("Alexandre", "Dumas"), authors.get(0));
    }
}

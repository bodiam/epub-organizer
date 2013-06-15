package nl.jworks.epub.logic.strategy.author;

import nl.jworks.epub.domain.Author;
import nl.jworks.epub.logic.strategy.BookImportContext;
import nl.jworks.epub.logic.strategy.Score;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class MetaDataAuthorScoreStrategyTest {

    @Test
    public void extractAuthorFromMetaData() {
        File file = new File("src/test/resources/epubs/Alexandre Dumas - The countess of Charney.epub");

        BookImportContext bookImportContext = new BookImportContext(file);

        Score<List<Author>> authorScores = new MetaDataAuthorScoreStrategy().score(bookImportContext);

        List<Author> authors = authorScores.getValue();

        assertThat(authors, hasItems(new Author("Alexandre", "Dumas")));
        assertThat(authorScores.getScore(), is(1.0));
    }
}

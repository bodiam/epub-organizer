package nl.jworks.epub.logic.strategy.author;

import nl.jworks.epub.domain.Author;
import nl.jworks.epub.logic.strategy.BookImportContext;
import nl.jworks.epub.logic.strategy.Score;
import org.easymock.EasyMockSupport;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertThat;

public class FileNameAuthorScoreStrategyTest extends EasyMockSupport {

    @Test
    public void testExtractAuthorFromFileName() throws Exception {
        BookImportContext bookImportContext = createMock(BookImportContext.class);

        File mock = createMock(File.class);
        expect(mock.getName()).andReturn("Abbott, Megan - Dare Me.epub");
        expect(bookImportContext.getFile()).andReturn(mock);

        replayAll();

        Score<List<Author>> authorScores = new FileNameAuthorScoreStrategy().score(bookImportContext);

        List<Author> authors = authorScores.getValue();

        verifyAll();

        assertThat(authors, contains(new Author("Megan", "Abbott")));
        assertThat(authorScores.getScore(), is(1.0));
    }

    @Test
    public void testExtractAuthorFromOtherFileName() throws Exception {
        BookImportContext bookImportContext = createMock(BookImportContext.class);

        File mock = createMock(File.class);
        expect(mock.getName()).andReturn("P. L. Travers - Mary Poppins From A to Z(epub).epub");
        expect(bookImportContext.getFile()).andReturn(mock);
        replayAll();

        Score<List<Author>> authorScores = new FileNameAuthorScoreStrategy().score(bookImportContext);

        List<Author> authors = authorScores.getValue();

        verifyAll();

        assertThat(authors, hasItems(new Author("P. L.", "Travers")));
        assertThat(authorScores.getScore(), is(1.0));
    }

    @Test
    public void testExtractAuthorFromOnlyLastname() throws Exception {
        BookImportContext bookImportContext = createMock(BookImportContext.class);
        File mock = createMock(File.class);
        expect(mock.getName()).andReturn("Travers - Mary Poppins From A to Z(epub).epub");
        expect(bookImportContext.getFile()).andReturn(mock);
        replayAll();

        Score<List<Author>> authorScores = new FileNameAuthorScoreStrategy().score(bookImportContext);

        List<Author> authors = authorScores.getValue();
        double value = authorScores.getScore();

        verifyAll();

        assertThat(authors, hasItems(new Author("", "Travers")));
        assertThat(authorScores.getScore(), is(1.0));
    }
}

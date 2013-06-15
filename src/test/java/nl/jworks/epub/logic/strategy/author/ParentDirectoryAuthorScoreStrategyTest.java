package nl.jworks.epub.logic.strategy.author;

import nl.jworks.epub.domain.Author;
import nl.jworks.epub.logic.strategy.BookImportContext;
import nl.jworks.epub.logic.strategy.Score;
import org.easymock.EasyMockSupport;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ParentDirectoryAuthorScoreStrategyTest extends EasyMockSupport {

    @Test
    public void testExtractAuthorFromDirectoryName() throws Exception {
        BookImportContext bookImportContext = createMock(BookImportContext.class);

        File mock = createMock(File.class);
        File parent = createMock(File.class);

        expect(mock.getParentFile()).andReturn(parent);
        expect(parent.getName()).andReturn("Christopher, Matt");
        expect(bookImportContext.getFile()).andReturn(mock);

        replayAll();

        Score<List<Author>> authorScores = new ParentDirectoryAuthorScoreStrategy().score(bookImportContext);

        List<Author> authors = authorScores.getValue();

        verifyAll();

        assertThat(authors, hasItems(new Author("Matt", "Christopher")));
        assertThat(authorScores.getScore(), is(1.0));
    }

    @Test
    public void testExtractMultipleAuthorsFromDirectoryName() throws Exception {
        BookImportContext bookImportContext = createMock(BookImportContext.class);

        File mock = createMock(File.class);
        File parent = createMock(File.class);

        expect(mock.getParentFile()).andReturn(parent);
        expect(parent.getName()).andReturn("Jacques, Brian & Chalk, Gary");
        expect(bookImportContext.getFile()).andReturn(mock);

        replayAll();

        Score<List<Author>> authorScores = new ParentDirectoryAuthorScoreStrategy().score(bookImportContext);

        List<Author> authors = authorScores.getValue();
        double value = authorScores.getScore();

        verifyAll();

        assertThat(authors, hasItems(new Author("Brian", "Jacques"), new Author("Gary", "Chalk")));
        assertThat(authorScores.getScore(), is(1.0));
    }

    public void testJunk() {
        // 10th October 2012 - Epub Dump
        // 106. Vernor Vinge - A Deepness in the Sky
        // 1583227652_Intellect
        // A Carriage for the Midwife - Maggie Bennett
        // Ciaphas Cain - Sector Thirteen                       // not an author!
    }
}

package nl.jworks.epub.logic.strategy.author;

import nl.jworks.epub.domain.Author;
import nl.jworks.epub.logic.strategy.Score;
import org.easymock.EasyMockSupport;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.easymock.EasyMock.expect;

public class FileNameAuthorScoreStrategyTest extends EasyMockSupport {

    @Test
    public void testExtractAuthorFromFileName() throws Exception {
        File mock = createMock(File.class);
        expect(mock.getName()).andReturn("Abbott, Megan - Dare Me.epub");

        replayAll();

        Score<List<Author>> authorScores = new FileNameAuthorScoreStrategy().score(mock);

        List<Author> authors = authorScores.getValue();
        double value = authorScores.getScore();

        verifyAll();

        assertEquals(new Author("Megan", "Abbott"), authors.get(0));
        assertEquals(1.0, value);
    }

    @Test
    public void testExtractAuthorFromOtherFileName() throws Exception {

        File mock = createMock(File.class);
        expect(mock.getName()).andReturn("P. L. Travers - Mary Poppins From A to Z(epub).epub");
        replayAll();

        Score<List<Author>> authorScores = new FileNameAuthorScoreStrategy().score(mock);

        List<Author> authors = authorScores.getValue();
        double value = authorScores.getScore();

        verifyAll();
        assertEquals(new Author("P. L.", "Travers"), authors.get(0));
        assertEquals(1.0, value);
    }

    @Test
    public void testExtractAuthorFromOnlyLastname() throws Exception {

        File mock = createMock(File.class);
        expect(mock.getName()).andReturn("Travers - Mary Poppins From A to Z(epub).epub");
        replayAll();

        Score<List<Author>> authorScores = new FileNameAuthorScoreStrategy().score(mock);

        List<Author> authors = authorScores.getValue();
        double value = authorScores.getScore();

        verifyAll();
        assertEquals(new Author("", "Travers"), authors.get(0));
    }
}
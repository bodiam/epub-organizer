package nl.jworks.epub.logic.strategy.author;

import nl.jworks.epub.domain.Author;
import nl.jworks.epub.logic.strategy.Score;
import org.easymock.EasyMockSupport;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.easymock.EasyMock.expect;

public class ParentDirectoryAuthorScoreStrategyTest extends EasyMockSupport {

    @Test
    public void testExtractAuthorFromDirectoryName() throws Exception {
        File mock = createMock(File.class);
        File parent = createMock(File.class);

        expect(mock.getParentFile()).andReturn(parent);
        expect(parent.getName()).andReturn("Christopher, Matt");

        replayAll();

        Score<List<Author>> authorScores = new ParentDirectoryAuthorScoreStrategy().score(mock);

        List<Author> authors = authorScores.getValue();
        double value = authorScores.getScore();

        verifyAll();

        assertEquals(new Author("Matt", "Christopher"), authors.get(0));
        assertEquals(1.0, value);
    }

    @Test
    public void testExtractMultipleAuthorsFromDirectoryName() throws Exception {
        File mock = createMock(File.class);
        File parent = createMock(File.class);

        expect(mock.getParentFile()).andReturn(parent);
        expect(parent.getName()).andReturn("Jacques, Brian & Chalk, Gary");

        replayAll();

        Score<List<Author>> authorScores = new ParentDirectoryAuthorScoreStrategy().score(mock);

        List<Author> authors = authorScores.getValue();
        double value = authorScores.getScore();

        verifyAll();

        assertEquals(new Author("Brian", "Jacques"), authors.get(0));
        assertEquals(new Author("Gary", "Chalk"), authors.get(1));
        assertEquals(1.0, value);
    }

    public void testJunk() {
        // 10th October 2012 - Epub Dump
        // 106. Vernor Vinge - A Deepness in the Sky
        // 1583227652_Intellect
        // A Carriage for the Midwife - Maggie Bennett
        // Ciaphas Cain - Sector Thirteen                       // not an author!
    }
}

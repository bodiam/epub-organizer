package nl.jworks.epub.logic.tags;

import nl.jworks.epub.domain.Tag;
import nl.jworks.epub.logic.strategy.BookImportContext;
import nl.jworks.epub.logic.strategy.tags.TagsMetaDataScoreStrategy;
import nl.jworks.epub.logic.strategy.tags.TagsScore;
import nl.jworks.epub.util.BookBuilder;
import org.junit.Test;

import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertThat;

public class TagsMetaDataScoreStrategyTest {

    @Test
    public void testScoreTags() {

        BookImportContext importContext = new BookBuilder().tags("Fantasy", "Fiction").buildContext();

        TagsMetaDataScoreStrategy strategy = new TagsMetaDataScoreStrategy();

        TagsScore score = strategy.score(importContext);

        assertThat(score.getValue(), hasItems(new Tag("Fantasy"), new Tag("Fiction")));

    }
}

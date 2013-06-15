package nl.jworks.epub.logic.strategy.tags;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import nl.jworks.epub.annotations.NotNull;
import nl.jworks.epub.annotations.Nullable;
import nl.jworks.epub.domain.Tag;
import nl.jworks.epub.logic.strategy.BookImportContext;
import nl.jworks.epub.logic.strategy.ScoreStrategy;
import nl.siegmann.epublib.domain.Metadata;

public class TagsMetaDataScoreStrategy implements ScoreStrategy<TagsScore> {

    @NotNull
    @Override
    public TagsScore score(BookImportContext context) {
        final Metadata metadata = context.getMetadata();

        Function<String, Tag> stringToTags = new Function<String, Tag>() {
            @Override
            public Tag apply(@Nullable String input) {
                return new Tag(input);
            }
        };

        return new TagsScore(Lists.transform(metadata.getSubjects(), stringToTags), this.getClass());
    }
}

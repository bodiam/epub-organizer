package nl.jworks.epub.logic.strategy.publisher;

import nl.jworks.epub.annotations.NotNull;
import nl.jworks.epub.logic.strategy.BookImportContext;
import nl.jworks.epub.logic.strategy.ScoreStrategy;
import nl.siegmann.epublib.domain.Metadata;

import java.util.List;

public class PublisherMetaDataScoreStrategy implements ScoreStrategy<PublisherScore> {

    @NotNull
    @Override
    public PublisherScore score(BookImportContext context) {
        Metadata metadata = context.getMetadata();
        List<String> publishers = metadata.getPublishers();

        String firstPublisher = "";
        if(publishers.size() > 0) {
            firstPublisher = publishers.get(0);
        }

        return new PublisherScore(firstPublisher, PublisherMetaDataScoreStrategy.class);
    }


}

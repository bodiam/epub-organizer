package nl.jworks.epub.logic.strategy.summary;

import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;
import nl.jworks.epub.annotations.NotNull;
import nl.jworks.epub.logic.strategy.BookImportContext;
import nl.jworks.epub.logic.strategy.ScoreStrategy;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Metadata;

import java.util.List;

public class SummaryMetaDataScoreStrategy implements ScoreStrategy<SummaryScore> {

    @NotNull
    @Override
    public SummaryScore score(BookImportContext context) {
        Book epubBook = context.getEpubBook();
        Metadata metadata = epubBook.getMetadata();
        List<String> descriptions = metadata.getDescriptions();

        Ordering<String> o = new Ordering<String>() {
            @Override
            public int compare(String left, String right) {
                return Ints.compare(left.length(), right.length());
            }
        };

        String longestDescription;
        if (descriptions.isEmpty()) {
            longestDescription = "";
        } else {
            longestDescription = o.max(descriptions);
        }

        return new SummaryScore(longestDescription, SummaryMetaDataScoreStrategy.class);
    }
}

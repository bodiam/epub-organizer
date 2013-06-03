package nl.jworks.epub.logic.strategy.title;

import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;
import nl.jworks.epub.annotations.NotNull;
import nl.jworks.epub.logic.strategy.BookImportContext;
import nl.jworks.epub.logic.strategy.ScoreStrategy;
import nl.siegmann.epublib.domain.Book;

import java.util.List;

public class MetaDataTitleScoreStrategy implements ScoreStrategy<TitleScore> {

    @NotNull @Override
    public TitleScore score(BookImportContext context) {
        // print the first title
        Book epubBook = context.getEpubBook();
        List<String> epubTitles = epubBook.getMetadata().getTitles();

        Ordering<String> o = new Ordering<String>() {
            @Override
            public int compare(String left, String right) {
                return Ints.compare(left.length(), right.length());
            }
        };

        String longestTitle = o.max(epubTitles);

        return new TitleScore(longestTitle, MetaDataTitleScoreStrategy.class);
    }
}

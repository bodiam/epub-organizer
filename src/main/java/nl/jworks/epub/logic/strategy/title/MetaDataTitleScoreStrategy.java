package nl.jworks.epub.logic.strategy.title;

import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;
import nl.siegmann.epublib.epub.EpubReader;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

public class MetaDataTitleScoreStrategy implements TitleScoreStrategy{

    @Override
    public TitleScore score(File source) {
        try {
            // read epub file
            EpubReader epubReader = new EpubReader();
            nl.siegmann.epublib.domain.Book epubBook = epubReader.readEpub(new FileInputStream(source));

            // print the first title
            List<String> epubTitles = epubBook.getMetadata().getTitles();

            Ordering<String> o = new Ordering<String>() {
                @Override
                public int compare(String left, String right) {
                    return Ints.compare(left.length(), right.length());
                }
            };

            String longestTitle = o.max(epubTitles);

            return new TitleScore(longestTitle, MetaDataTitleScoreStrategy.class);

        } catch (Exception e) {
            throw new RuntimeException("Could not get title score for " + source, e);
        }
    }
}

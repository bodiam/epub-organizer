package nl.jworks.epub.logic.strategy.cover;

import nl.jworks.epub.annotations.NotNull;
import nl.jworks.epub.domain.Binary;
import nl.jworks.epub.logic.strategy.BookImportContext;
import nl.jworks.epub.logic.strategy.ScoreStrategy;
import nl.siegmann.epublib.domain.Resource;

import java.io.IOException;

public class MetaDataCoverScoreStrategy implements ScoreStrategy<CoverScore> {

    @NotNull
    @Override
    public CoverScore score(BookImportContext context) {

        Resource coverImage = context.getEpubBook().getCoverImage();

        if (coverImage != null) {
            try {
                byte[] data = coverImage.getData();
                return new CoverScore(new Binary(data), this.getClass());
            } catch (IOException e) {
                return new CoverScore(null, this.getClass());
            }
        }

        return new CoverScore(null, this.getClass());
    }
}

package nl.jworks.epub.logic.strategy.publicationdate;

import nl.jworks.epub.annotations.NotNull;
import nl.jworks.epub.logic.strategy.BookImportContext;
import nl.jworks.epub.logic.strategy.ScoreStrategy;
import nl.siegmann.epublib.domain.Date;
import nl.siegmann.epublib.domain.Metadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class PublicationDateMetaDataScoreStrategy implements ScoreStrategy<PublicationDateScore> {

    private static Logger log = LoggerFactory.getLogger(PublicationDateMetaDataScoreStrategy.class);

    @NotNull
    @Override
    public PublicationDateScore score(BookImportContext context) {
        Metadata metadata = context.getMetadata();

        Date publicationDate = findPublicationDate(metadata);
        java.util.Date scoreDate = null;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            scoreDate = sdf.parse(publicationDate.getValue());
        } catch (ParseException e) {
            log.warn("Could not parse date {}", publicationDate);
        }

        return new PublicationDateScore(scoreDate, this.getClass());
    }

    private Date findPublicationDate(Metadata metadata) {
        List<Date> dates = metadata.getDates();
        for (Date date : dates) {
            if (date.getEvent() == Date.Event.PUBLICATION) {
                return date;
            }
        }

        return null;
    }
}

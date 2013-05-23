package nl.jworks.epub.logic.strategy.title;

import org.springframework.util.Assert;

import java.io.File;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.indexOf;
import static org.apache.commons.lang3.StringUtils.substring;

/**
 * Extracts the Title from a filename and calculates a score for the result.
 * <p/>
 * For example, it will translate the following:
 * <p/>
 * Abbott, Megan - Dare Me.epub => Dare Me
 */
public class FileNameTitleScoreStrategy implements TitleScoreStrategy {

    public static final String SEPARATOR = "-";

    @Override
    public TitleScore score(File source) {

        String fileName = source.getName();

        String title = extractTitlePart(fileName);

        return new TitleScore(title, FileNameTitleScoreStrategy.class);
    }

    private String extractTitlePart(String fileName) {
        Assert.isTrue(fileName.contains(SEPARATOR), "Format in " + fileName + " not understood, doesn't contain separator");

        return substring(fileName, indexOf(fileName, SEPARATOR), fileName.length());
    }
}
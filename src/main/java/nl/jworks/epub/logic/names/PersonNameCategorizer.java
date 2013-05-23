package nl.jworks.epub.logic.names;

import org.apache.commons.lang3.Validate;

import java.util.Arrays;
import java.util.List;

/**
 * This is a calculator for determining if a String should either be a first name or
 * a last name.
 *
 */
public class PersonNameCategorizer {

    private static List<String> firstNames = Arrays.asList(
            "Erik",
            "Brian",
            "Davenport",
            "Gary",
            "Lieke",
            "Andrew",
            "Andry",
            "Matt",
            "Megan"
    );

    private static List<String> lastNames = Arrays.asList(
            "Smith",
            "Johnson",
            "Williams",
            "Jones",
            "Brown",
            "Davis",
            "Miller",
            "Abbott",
            "Wilson",
            "Travers",
            "Dumas",
            "Adams"
    );

    /**
     * Categorize the input.
     */
    public Name categorize(String[] names) {
        Validate.isTrue(names.length == 2, "Number of arguments must be 2, but input was size %s: %s", names.length, Arrays.toString(names));

        double scoreFirstNameLastName = determineScoreForFirstName(names[0]) + determineScoreForLastName(names[1]);
        double scoreLastNameFirstName = determineScoreForFirstName(names[1]) + determineScoreForLastName(names[0]);

        if (scoreFirstNameLastName > scoreLastNameFirstName) {
            return new Name(names[0], names[1]);
        } else if (scoreFirstNameLastName < scoreLastNameFirstName) {
            return new Name(names[1], names[0]);
        } else {
            throw new IllegalArgumentException("Could not determine unique score for names " + names[0] + " and " + names[1]);
        }
    }

    private double determineScoreForFirstName(String name) {
        return determineScore(name, firstNames);
    }

    private double determineScoreForLastName(String name) {
        return determineScore(name, lastNames);
    }

    private double determineScore(String name, List<String> names) {
        double score = names.indexOf(name);

        if(score > 0) {
            score = score / (double) names.size();
        }

        return score;
    }
}

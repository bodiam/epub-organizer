package nl.jworks.epub.logic.names;

import nl.jworks.epub.domain.Author;

import static org.apache.commons.lang3.StringUtils.indexOf;
import static org.apache.commons.lang3.StringUtils.substring;

public class FileExtractSupport {

    protected Author extractAuthorFromFileName(String fileName) {
        String authorIncludingSpecialCharacters = extractAuthorPart(fileName);
        String authorExcludingSpecialCharacters = stripSpecialCharacters(authorIncludingSpecialCharacters);
        String[] tokens = splitFirstnameAndLastname(authorExcludingSpecialCharacters);

        Name name = new PersonNameCategorizer().categorize(tokens);

        return new Author(name.getFirstName(), name.getLastName());
    }

    protected String extractAuthorPart(String fileName) {
        return substring(fileName, 0, indexOf(fileName, "-"));
    }

    protected String stripSpecialCharacters(String authorIncludingSpecialCharacters) {
        return authorIncludingSpecialCharacters.replaceAll("[,\\.]", "");
    }

    protected String[] splitFirstnameAndLastname(String authorExcludingSpecialCharacters) {
        return authorExcludingSpecialCharacters.split("\\s");
    }
    
    
}

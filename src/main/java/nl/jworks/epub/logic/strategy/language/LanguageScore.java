package nl.jworks.epub.logic.strategy.language;

import nl.jworks.epub.logic.strategy.Score;

public class LanguageScore extends Score<String> {

    public LanguageScore(String value, Class source) {
        super(value, source);
    }

    @Override
    public double getScore() {
        // TODO: make better version of this.
        if(getValue().equalsIgnoreCase("EN") || getValue().equalsIgnoreCase("NL")) {
            return 1.0;
        } else {
            return 0;
        }
    }
}

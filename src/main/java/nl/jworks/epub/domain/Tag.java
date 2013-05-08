package nl.jworks.epub.domain;

import com.google.common.base.Objects;

public class Tag {

    private String text;


    private Tag() {}

    public Tag(String text) {

        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("text", text)
                .toString();
    }
}

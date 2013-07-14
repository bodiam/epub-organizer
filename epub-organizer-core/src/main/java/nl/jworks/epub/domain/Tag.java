package nl.jworks.epub.domain;

import com.google.common.base.Objects;

public class Tag {

    private String text;

    private Tag() {
    }

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

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        final Tag other = (Tag) o;
        return java.util.Objects.equals(this.text, other.text);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(text);
    }
}

package nl.jworks.epub.logic.names;

public abstract class Score<T> {

    private T source;

    public Score(T source) {
        this.source = source;
    }

    public T getSource() {
        return source;
    }

    public abstract double getValue();
}

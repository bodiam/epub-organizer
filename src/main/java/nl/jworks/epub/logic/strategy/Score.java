package nl.jworks.epub.logic.strategy;

public abstract class Score<T> {

    private T value;
    private Class source;

    public Score(T value, Class source) {
        this.value = value;
        this.source = source;
    }

    public T getValue() {
        return value;
    }

    public Class getSource() {
        return source;
    }

    public abstract double getScore();
}

package nl.jworks.epub.logic.strategy;

import nl.jworks.epub.annotations.NotNull;

import static com.google.common.base.Preconditions.checkNotNull;

public abstract class Score<T> {

    private T value;
    private Class source;

    public Score(@NotNull T value, @NotNull Class source) {
        this.value = checkNotNull(value);
        this.source = checkNotNull(source);
    }

    public T getValue() {
        return value;
    }

    public Class getSource() {
        return source;
    }

    public abstract double getScore();
}

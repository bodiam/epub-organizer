package nl.jworks.epub.util;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.concurrent.TimeUnit;

public class Debug {

    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    private int sleepInMilliSeconds = 5_000;
    private int matches;

    private static Debug debug = new Debug();

    public static Debug getInstance() {
        return debug;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void sleep() {
        try {
            TimeUnit.MILLISECONDS.sleep(sleepInMilliSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setSleepInMilliSeconds(int sleepInMilliSeconds) {
        this.sleepInMilliSeconds = sleepInMilliSeconds;
    }

    public int getSleepInMilliSeconds() {
        return sleepInMilliSeconds;
    }

    public void setMatches(int matches) {
        int oldMatches = this.matches;
        this.matches = matches;

        this.propertyChangeSupport.firePropertyChange("matches", oldMatches, matches);
    }
}

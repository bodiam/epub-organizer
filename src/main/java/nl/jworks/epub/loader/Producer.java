package nl.jworks.epub.loader;

import nl.jworks.epub.util.Debug;
import nl.jworks.epub.util.DebugView;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.FileVisitResult.CONTINUE;

public class Producer implements Runnable {

    private Broker<File> broker;

    public Producer(Broker<File> broker) {
        this.broker = broker;
    }

    @Override
    public void run() {
        try {
            Path startingDir = Paths.get("/Users/erikp/Downloads/Torrents/Complete");
            String pattern = "*.epub";

            Finder finder = new Finder(pattern);
            Files.walkFileTree(startingDir, finder);
            finder.done();

            this.broker.continueProducing = Boolean.FALSE;
            System.out.println("Producer finished its job; terminating.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //http://docs.oracle.com/javase/tutorial/essential/io/find.html
    class Finder extends SimpleFileVisitor<Path> {

        private final PathMatcher matcher;
        private int numMatches = 0;

        Finder(String pattern) {
            matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
        }

        // Compares the glob pattern against the file or directory name.
        void find(Path file) {
            Path name = file.getFileName();
            if (name != null && matcher.matches(name)) {
                numMatches++;
                System.out.println("Producer got file " + file);

                try {
                    broker.put(file.toFile());

                    Debug instance = Debug.getInstance();
                    instance.sleep();
                    instance.setMatches(numMatches);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

        // Prints the total number of matches to standard out.
        void done() {
            System.out.println("Matched: " + numMatches);
        }

        // Invoke the pattern matching method on each file.
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
            find(file);
            return CONTINUE;
        }

        // Invoke the pattern matching method on each directory.
        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
            find(dir);
            return CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) {
            System.err.println(exc);
            return CONTINUE;
        }
    }

}
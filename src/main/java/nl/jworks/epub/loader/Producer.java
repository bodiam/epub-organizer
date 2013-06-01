package nl.jworks.epub.loader;

import nl.jworks.epub.util.Debug;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.FileVisitResult.CONTINUE;

public class Producer implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(Producer.class);
    
    private String location;
    private Broker<File> broker;

    public Producer(String location, Broker<File> broker) {
        this.location = location;
        this.broker = broker;
    }

    @Override
    public void run() {
        try {
            logger.info("Processing from {}", location);

            Path startingDir = Paths.get(location);
            String pattern = "*.epub";

            Finder finder = new Finder(pattern);
            Files.walkFileTree(startingDir, finder);
            finder.done();

            this.broker.continueProducing = Boolean.FALSE;
            logger.info("Producer finished its job; terminating.");
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
                logger.debug("Producer got file " + file);

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
            logger.debug("Matched: " + numMatches);
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
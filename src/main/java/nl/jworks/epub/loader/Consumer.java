package nl.jworks.epub.loader;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.epub.EpubReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Consumer implements Runnable {

    private String name;
    private Broker<File> broker;

    public Consumer(String name, Broker<File> broker) {
        this.name = name;
        this.broker = broker;
    }

    @Override
    public void run() {
        try {
            File data = broker.get();

            while (broker.continueProducing || data != null) {
                System.out.println("Consumer " + this.name + " processed data from broker: " + data);

                processEpub(data);

                data = broker.get();
            }

            System.out.println("Comsumer " + this.name + " finished its job; terminating.");
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private void processEpub(File data) {
        try {
            EpubReader epubReader = new EpubReader();
            Book book = epubReader.readEpub(new FileInputStream(data));

            System.out.println("**************** " + data + " **************************");
            System.out.println("Authors: "+ book.getMetadata().getAuthors());
            System.out.println("Dates: " +book.getMetadata().getDates());
            System.out.println("Descriptions: " +book.getMetadata().getDescriptions());
            System.out.println("First title: " + book.getMetadata().getFirstTitle());
            System.out.println("Format: " +book.getMetadata().getFormat());
            System.out.println("Identifiers: " +book.getMetadata().getIdentifiers());
            System.out.println("Language: " + book.getMetadata().getLanguage());
            System.out.println("Other properties: " + book.getMetadata().getOtherProperties());
            System.out.println("Publishers: " +book.getMetadata().getPublishers());
            System.out.println("Rights: "+ book.getMetadata().getRights());
            System.out.println("Subjects: "+ book.getMetadata().getSubjects());
            System.out.println("Types: " + book.getMetadata().getTypes());
            System.out.println("Titles: " + book.getMetadata().getTitles());
        } catch (IOException e) {

        }
    }


}

package nl.jworks.epub.mongodb.util;

import nl.jworks.epub.domain.Book;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class BookViewer {


    public static void view(final Book book) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(book);
            }
        });
    }

    private static void createAndShowGUI(Book book) {
        //Create and set up the window.
        JFrame frame = new JFrame(book.getTitle());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //Set up the content pane.
        addComponentsToPane(frame.getContentPane(), book);

        //Display the window.
        frame.setLocation(500, 500);
        frame.pack();
        frame.setVisible(true);
    }

    private static void addComponentsToPane(Container contentPane, Book book) {
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        contentPane.add(new JLabel(readImage(book.getCover().getContents())));

        contentPane.add(new JLabel("Source : " + book.getSource()));
        contentPane.add(new JLabel("Title : " + book.getTitle()));
        contentPane.add(new JLabel("Language : " + book.getLanguage()));
        contentPane.add(new JLabel("ISBN : " + book.getIsbn()));
//        contentPane.add(new JLabel("Summary : " + book.getSummary()));
        contentPane.add(new JLabel("Publisher : " + book.getPublisher()));
        contentPane.add(new JLabel("Publication date : " + book.getPublicationDate()));
        contentPane.add(new JLabel("Date added : " + book.getDateAdded()));
    }

    private static ImageIcon readImage(byte[] imageData) {
        try {
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageData));
            return new ImageIcon(image);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

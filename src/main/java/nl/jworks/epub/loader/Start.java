package nl.jworks.epub.loader;

import nl.jworks.epub.util.DebugView;

import java.io.File;
import java.util.concurrent.*;

public class Start {

    public static void main(String[] args) {

        new DebugView().show();

        try {
            Broker<File> broker = new Broker<File>();

            ExecutorService threadPool = Executors.newFixedThreadPool(3);

            threadPool.execute(new Consumer("1", broker));
            threadPool.execute(new Consumer("2", broker));
            Future producerStatus = threadPool.submit(new Producer(broker));

            // this will wait for the producer to finish its execution.
            producerStatus.get();

            threadPool.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

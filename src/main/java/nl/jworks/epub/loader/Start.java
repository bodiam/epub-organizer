package nl.jworks.epub.loader;

import nl.jworks.epub.configuration.ApplicationConfiguration;
import nl.jworks.epub.util.DebugView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Component
public class Start {

    private static Logger log = LoggerFactory.getLogger(Start.class);

    private final Consumer consumer1;
    private final Consumer consumer2;
    private final Producer producer;

    @Autowired
    public Start(Consumer consumer1,
                 Consumer consumer2,
                 Producer producer) {

        this.consumer1 = consumer1;
        this.consumer2 = consumer2;
        this.producer = producer;
    }

    public static void main(String[] args) {

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.getEnvironment().setActiveProfiles("smalldata");
        ctx.register(ApplicationConfiguration.class);
        ctx.refresh();

        Start start = ctx.getBean(Start.class);
        start.start();
    }

    public void start() {


        new DebugView().show();

        long startTime = System.currentTimeMillis();

        try {
            ExecutorService threadPool = Executors.newFixedThreadPool(3);

            threadPool.execute(consumer1);
            threadPool.execute(consumer2);
            Future producerStatus = threadPool.submit(producer);

            // this will wait for the producer to finish its execution.
            producerStatus.get();

            threadPool.shutdown();

            long endTime = System.currentTimeMillis();

            log.info("Processing all books took {} ms", (endTime-startTime));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

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
public abstract class Start {

    private static Logger log = LoggerFactory.getLogger(Start.class);

    private final Producer producer;

    @Autowired
    public Start(Producer producer) {
        this.producer = producer;
    }

    public static void main(String[] args) {

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.getEnvironment().setActiveProfiles("bigdata");
        ctx.register(ApplicationConfiguration.class);
        ctx.refresh();

        Start start = ctx.getBean(Start.class);
        start.start();
    }

    public void start() {

        new DebugView().show();

        long startTime = System.currentTimeMillis();

        try {
            ExecutorService threadPool = Executors.newFixedThreadPool(5);

            threadPool.execute(createConsumer());
            threadPool.execute(createConsumer());
            threadPool.execute(createConsumer());
            threadPool.execute(createConsumer());
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

    protected abstract Consumer createConsumer();
}

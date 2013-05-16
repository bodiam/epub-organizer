package nl.jworks.epub.loader;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Broker<T> {
    public ArrayBlockingQueue<T> queue = new ArrayBlockingQueue<T>(100);
    public Boolean continueProducing = Boolean.TRUE;

    public void put(T data) throws InterruptedException {
        this.queue.put(data);
    }

    public T get() throws InterruptedException {
//        return this.queue.poll(1, TimeUnit.SECONDS);
        return this.queue.take();
    }
}
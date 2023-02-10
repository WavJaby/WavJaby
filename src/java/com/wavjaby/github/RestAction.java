package com.wavjaby.github;

import java.io.InputStream;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public abstract class RestAction<T> {
    private static final Consumer<Throwable> onError = Throwable::printStackTrace;

    public void queue() {
        this.queue(null, null);
    }

    public void queue(Consumer<? super T> success) {
        this.queue(success, null);
    }

    public void queue(Consumer<? super T> success, Consumer<? super Throwable> failed) {
        createRestRequest(success, failed);
    }

    public T complete() {
        CountDownLatch lock = new CountDownLatch(1);
        AtomicReference<T> result = new AtomicReference<>();
        final Consumer<T> onData = (data) -> {
            result.set(data);
            lock.countDown();
        };
        createRestRequest(onData, onError);
        try {
            lock.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result.get();
    }

    abstract protected T newResult(InputStream inputStream);

    abstract protected void createRestRequest(Consumer<? super T> success, Consumer<? super Throwable> failed);
}

package server;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class CustomThreadPool {
    private final BlockingQueue<Runnable> taskQueue;
    private final Thread[] workerThreads;

    public CustomThreadPool(int numberOfThreads) {
        taskQueue = new LinkedBlockingQueue<>();
        workerThreads = new Thread[numberOfThreads];

        for (int i = 0; i < numberOfThreads; i++) {
            workerThreads[i] = new WorkerThread(taskQueue);
            workerThreads[i].start();
        }
    }

    public void submit(Runnable task) {
        try {
            taskQueue.put(task);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        for (Thread workerThread : workerThreads) {
            workerThread.interrupt();
        }
    }

    private static class WorkerThread extends Thread {
        private final BlockingQueue<Runnable> taskQueue;

        public WorkerThread(BlockingQueue<Runnable> taskQueue) {
            this.taskQueue = taskQueue;
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    // take a task from the queue and execute it
                    Runnable task = taskQueue.take();
                    task.run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }
}

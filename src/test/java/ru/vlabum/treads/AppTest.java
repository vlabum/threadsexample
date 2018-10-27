package ru.vlabum.treads;

import org.junit.Test;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Unit test for simple App.
 */
public class AppTest 
{

    private class NumberValue  {

        long value;

    }

    private class TaskFirst extends Thread {

        private final NumberValue numberValue;

        public TaskFirst (final NumberValue numberValue) {
            this.numberValue = numberValue;
        }

        @Override
        public void run() {
            System.out.println(numberValue.value);
        }

    }

    private  class TaskSecond extends Thread {

        private final NumberValue numberValue;

        public TaskSecond (final NumberValue numberValue) {
            this.numberValue = numberValue;
        }

        @Override
        public void run() {
            numberValue.value = 1;
        }

    }

    @Test
    public void testCall() {
        final NumberValue numberValue = new NumberValue();
        final ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(new TaskSecond(numberValue));
        executorService.submit(new TaskFirst(numberValue));
        System.out.println(numberValue.value + " main");
    }

}

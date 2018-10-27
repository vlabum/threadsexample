package ru.vlabum.treads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Создаются 3 потока A, B, C. После каждой итерации поток пробуждает соседа и засыпает.
 * Потоки B и C сразу засыпают, чтобы гарантированно первым отработал поток A.
 * После итерации цикла, поток A пробуждает поток B и засыпает.
 * Поток B в свою очередь пробуждает C.
 * Ну а C пробуждает A.
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        for (int i = 0; i < 10; i++) {
            printABC();
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println();
        }
    }

    private static void printABC() {
        final LiteralPrinter literalA = new LiteralPrinter("A", false);
        final LiteralPrinter literalB = new LiteralPrinter("B", true);
        final LiteralPrinter literalC = new LiteralPrinter("C", true);
        literalA.setNext(literalB);
        literalB.setNext(literalC);
        literalC.setNext(literalA);
        final ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(literalA);
        executorService.submit(literalB);
        try {
            executorService.submit(literalC);
        } catch (Exception e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }
}

package ru.vlabum.treads;

public class LiteralPrinter implements Runnable {

    private static int COUNT_PRINT = 5;

    private final String literal;
    private LiteralPrinter next;
    private boolean suspendedFlag;

    public LiteralPrinter(final String literal, final boolean suspendedFlag){
        this.literal = literal;
        this.suspendedFlag = suspendedFlag;
    }

    public void setNext(LiteralPrinter next) {
        this.next = next;
    }

    @Override
    public void run() {
        for (int i = 0; i < COUNT_PRINT; i++) {
            synchronized (this) {
                while (suspendedFlag) {
                    try { wait(); }
                    catch (InterruptedException e) { e.printStackTrace(); }
                }
                System.out.print(literal);
            }
            if (i < COUNT_PRINT - 1) suspend();
            if (next != null) next.resume();
        }
    }

    synchronized void suspend() { suspendedFlag = true; }

    synchronized void resume() {
        suspendedFlag = false;
        notify();
    }

}

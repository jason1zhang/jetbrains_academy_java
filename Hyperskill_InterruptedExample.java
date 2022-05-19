
/**
 * 
 * This problem is from Jetbrains Academy topic - Interruptions
 * 
 * @author Jason Zhang
 * @version 1.0
 * @since 2022-05-19
 *
 * Problem statement:
 *  Interrupting a thread
 *      You are dealing with the class Worker that extends Thread . The class overrides the method run to do something useful. 
 *      You need to start an instance of this class (in a new thread), wait for it a little (2000-3000 milliseconds) and interrupt this new thread.
 * 
 *      If you do not interrupt the thread after starting, something bad will happen.
*/

class Hyperskill_InterruptedExample {

    private static long mainThreadId = Thread.currentThread().getId();

    public static void main(String[] args) throws InterruptedException {

        Worker worker = new Worker();

        // write your code here
        worker.start();
        Thread.sleep(3000);
        worker.interrupt();
    }

    // Don't change the code below
    static class Worker extends Thread {

        @Override
        public void run() {

            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException("You need to wait longer!", e);
            }

            final long currentId = Thread.currentThread().getId();

            if (currentId == mainThreadId) {
                throw new RuntimeException("You must start a new thread!");
            }

            while (true) {
                if (isInterrupted()) {
                    System.out.println("Interrupted");
                    break;
                }
            }
        }
    }
}
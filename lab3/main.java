import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

class MyTask implements Callable<String> {
    @Override
    public String call() {
        try {
            Thread.sleep(2000); // Simulate a long-running task
            return "Task completed!";
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "error";
        }
    }
}

public class main {
    public static void main(String[] args) {
        Callable<String> task = new MyTask();
        FutureTask<String> futureTask = new FutureTask<>(task);

        Thread thread = new Thread(futureTask);
        thread.start();

        try {
            System.out.println("Waiting for the task to complete...");
            String result = futureTask.get();
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
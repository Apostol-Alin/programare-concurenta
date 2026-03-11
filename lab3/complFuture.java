
import java.util.concurrent.CompletableFuture;

public class complFuture {
    public static void main(String[] args) {
        int n = 30;
        System.out.println("Calculating Fibonacci of " + n);
        CompletableFuture<Long> future = CompletableFuture.supplyAsync(() -> fibonacci(n));
        future.thenAccept(result -> {
            System.out.println("Result is: " + result);
        });
        System.out.println("Doing other work while waiting for the result...");
        future.join();
    }

    private static long fibonacci(int n){
        if (n <= 1) {
            return n;
        }
        return fibonacci(n - 1) + fibonacci(n - 2);
    }
}

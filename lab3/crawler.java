import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

class runner implements Callable<String>{
    private String url;
    public runner(String _url){
        this.url = _url;
    }
    @Override
    public String call(){
        try {
            StringBuilder content = new StringBuilder();
            HttpURLConnection connection = (HttpURLConnection) new URI(this.url).toURL().openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            int status = connection.getResponseCode();
            if (status == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                reader.close();
                return content.toString();
            }
            return "Could not get page";
        } catch (Exception e) {
            e.printStackTrace();
            return "error for url: " + this.url;
        }
    }
}

public class crawler {

    public static void main(String[] args) {
        List<String> urls = List.of(
                "https://www.google.com",
                "https://www.github.com",
                "https://fmi.unibuc.ro"
        );
        List<FutureTask<String>> futureTasks = new ArrayList<FutureTask<String>>();
        for (String url : urls){
            FutureTask<String> futureTask = new FutureTask<>(new runner(url));
            futureTasks.add(futureTask);
            Thread thread = new Thread(futureTask);
            thread.start();
        }
        for (FutureTask<String> futureTask : futureTasks){
            try {
                String result = futureTask.get();
                System.out.println(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}

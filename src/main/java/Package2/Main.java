package Package2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> feedUrls = new ArrayList<>();
        final String end = "done";

        String url = "";

        System.out.println("Enter RSS feed URLs (type 'done' to finish):");
        while (!url.equals(end)) {
            url = scanner.nextLine();
            if (!url.equalsIgnoreCase(end)) {
                feedUrls.add(url);
            }
        }

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(feedUrls.size());

        for (String currentUrl : feedUrls) {
            RssFeedChecker checker = new RssFeedChecker(currentUrl);
            executorService.scheduleAtFixedRate(checker, 0, 10, TimeUnit.SECONDS);
        }

    }
}

// https://feeds.bbci.co.uk/news/world/rss.xml
// https://www.nytimes.com/svc/collections/v1/publish/https://www.nytimes.com/section/world/rss.xml
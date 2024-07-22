package Package2;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * RSSFeedChecker is a Runnable class that checks an RSS feed for updates periodically.
 */
public class RssFeedChecker implements Runnable {
    private String feedUrl;

    /**
     * Constructor for RSSFeedChecker.
     *
     * @param feedUrl The URL of the RSS feed.
     */
    public RssFeedChecker(String feedUrl) {
        this.feedUrl = feedUrl;
    }

    /**
     * Implements the run method from the Runnable interface.
     * Initiates checkFeed().
     */
    @Override
    public void run() {
        checkFeed();
    }

    /**
     * Checks the RSS feed for updates.
     * This method parses the XML content from the feed and prints recent items if updated.
     */
    public void checkFeed() {
        try {
            URL url = new URL(feedUrl);
            //XML Document building
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(url.openStream());

            //This is how you work with XML - you do not need to modify this!
            NodeList itemList = doc.getElementsByTagName("item");
            List<RSSItem> items = new ArrayList<>();

            for (int i = 0; i < itemList.getLength(); i++) {
                Node itemNode = itemList.item(i);
                if (itemNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element itemElement = (Element) itemNode;
                    String title = itemElement.getElementsByTagName("title").item(0).getTextContent();
                    String link = itemElement.getElementsByTagName("link").item(0).getTextContent();
                    String pubDate = itemElement.getElementsByTagName("pubDate").item(0).getTextContent();
                    items.add(new RSSItem(title, link, pubDate));

                }
            }

            synchronized (System.out) {
                System.out.println("-- Update from RSS Feed: " + feedUrl);
                System.out.println();
                for (int i = 0; i < Math.min(3, items.size()); i++) {
                    RSSItem item = items.get(i);
                    System.out.println("Title: " + item.getTitle());
                    System.out.println("Link: " + item.getLink());
                    System.out.println("Published Date: " + item.getPubDate());
                    System.out.println();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

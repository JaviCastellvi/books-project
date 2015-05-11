package edu.upc.eetac.dsa.javicastellvi.books.api;

/**
 * Created by Javi on 10/04/2015.
 */
import java.util.HashMap;
import java.util.Map;

public class BooksRootAPI {

    private Map<String, Link> links;

    public BooksRootAPI() {
        links = new HashMap<String, Link>();
    }

    public Map<String, Link> getLinks() {
        return links;
    }

}

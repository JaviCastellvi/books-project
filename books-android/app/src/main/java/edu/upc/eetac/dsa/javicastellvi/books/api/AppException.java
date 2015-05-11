package edu.upc.eetac.dsa.javicastellvi.books.api;

/**
 * Created by Javi on 10/04/2015.
 */
public class AppException extends Exception {
    public AppException() {
        super();
    }

    public AppException(String detailMessage) {
        super(detailMessage);
    }
}

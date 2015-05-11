package edu.upc.eetac.dsa.javicastellvi.books;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.ArrayList;

import edu.upc.eetac.dsa.javicastellvi.books.api.AppException;
import edu.upc.eetac.dsa.javicastellvi.books.api.Book;
import edu.upc.eetac.dsa.javicastellvi.books.api.BooksAPI;
import edu.upc.eetac.dsa.javicastellvi.books.api.BooksCollection;
import edu.upc.eetac.dsa.javicastellvi.books.api.Critica;


public class BooksMainActivity extends ListActivity {

    private class FetchBooksTask extends
            AsyncTask<Void, Void, BooksCollection> {
        private ProgressDialog pd;

        @Override
        protected BooksCollection doInBackground(Void... params) {
            BooksCollection books = null;
            try {
                books = BooksAPI.getInstance(BooksMainActivity.this)
                        .getBooks();
            } catch (AppException e) {
                e.printStackTrace();
            }
            return books;
        }
        @Override
        /*protected void onPostExecute(LibroCollection result) {
            ArrayList<Libro> libros = new ArrayList<Libro>(result.getLibros());
            for (Libro s : libros) {
                Log.d(TAG, s.getAutor() + "-" + s.getTitulo());
            }
            if (pd != null) {
                pd.dismiss();
            }
        }*/

        protected void onPostExecute(BooksCollection result) {
            addBooks(result);
            if (pd != null) {
                pd.dismiss();
            }
        }

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(BooksMainActivity.this);
            pd.setTitle("Searching...");
            pd.setCancelable(false);
            pd.setIndeterminate(true);
            pd.show();
        }

    }


   /* public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libros_main);
        Authenticator.setDefault(new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("admin", "admin"
                        .toCharArray());
            }
        });
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, items);
        setListAdapter(adapter);
        (new FetchStingsTask()).execute();
    }*/


    private final static String TAG = BooksMainActivity.class.toString();
    private static final String[] items = { "lorem", "ipsum", "dolor", "sit",
            "amet", "consectetuer", "adipiscing", "elit", "morbi", "vel",
            "ligula", "vitae", "arcu", "aliquet", "mollis", "etiam", "vel",
            "erat", "placerat", "ante", "porttitor", "sodales", "pellentesque",
            "augue", "purus" };
    //  private ArrayAdapter<String> adapter;
    private ArrayList<Book> booksList;
    private ArrayList<Critica> criticasList;
    private BookAdapter adapter;

    /** Called when the activity is first created. */

   /* public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_main);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, items);
        setListAdapter(adapter);
    }*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_main);

        booksList = new ArrayList<Book>();
        adapter = new BookAdapter(this, booksList);
        setListAdapter(adapter);

        Authenticator.setDefault(new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("admin", "admin"
                        .toCharArray());
            }
        });
        (new FetchBooksTask()).execute();
    }
    /* @Override
     protected void onListItemClick(ListView l, View v, int position, long id) {
         Libro libro = librosList.get(position);
         Log.d(TAG, libro.getLinks().get("self").getTarget());
     }*/
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Book book = booksList.get(position);
        Log.d(TAG, book.getLinks().get("self").getTarget());

        Intent intent = new Intent(this, BookDetailActivity.class);
        intent.putExtra("url", book.getLinks().get("self").getTarget());
        startActivity(intent);
    }
    private void addBooks(BooksCollection books) {
        booksList.addAll(books.getBooks());
        adapter.notifyDataSetChanged();
    }

}

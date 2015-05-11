package edu.upc.eetac.dsa.javicastellvi.books;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import edu.upc.eetac.dsa.javicastellvi.books.api.AppException;
import edu.upc.eetac.dsa.javicastellvi.books.api.Book;
import edu.upc.eetac.dsa.javicastellvi.books.api.BooksAPI;
import edu.upc.eetac.dsa.javicastellvi.books.api.Critica;


/**
 * Created by Javi on 02/05/2015.
 */
public class BookDetailActivity extends Activity {
    private final static String TAG = BookDetailActivity.class.getName();
    String urlBook  = null;
    String idlibro = null;
   // @Override
   /* protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.libro_detail_layout);
    }*/
    private void loadLibro(Book book) {
        TextView tvDetailTitle = (TextView) findViewById(R.id.tvDetailTitle);
        TextView tvDetailAuthor = (TextView) findViewById(R.id.tvDetailAuthor);
        TextView tvDetailPublisher = (TextView) findViewById(R.id.tvDetailPublisher);
        TextView tvDetailDate = (TextView) findViewById(R.id.tvDetailDate);
        TextView tvDetailEdition = (TextView) findViewById(R.id.tvDetailEdition);
        TextView tvDetailLanguage = (TextView) findViewById(R.id.tvDetailLanguage);

        tvDetailTitle.setText(book.getTitulo());
        tvDetailAuthor.setText("Autor: " + book.getAutor());
        tvDetailPublisher.setText(book.getEditorial());
        long val = book.getFecha_edicion();
        Date date=new Date(val);
        SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yy");
        String dateText = df2.format(date);
        tvDetailDate.setText(dateText);
        tvDetailEdition.setText("Edicion: " + book.getEdicion());
        tvDetailLanguage.setText("Idioma: " + book.getIdioma());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_detail_layout);
        urlBook = (String) getIntent().getExtras().get("url");
        String[] arrayUrlBook = urlBook.split("/");
        idlibro = arrayUrlBook[5];
        System.out.println(idlibro);


        (new FetchBookTask()).execute(urlBook);
    }
    private class FetchBookTask extends AsyncTask<String, Void, Book> {
        private ProgressDialog pd;

        @Override
        protected Book doInBackground(String... params) {
            Book book = null;
            try {
                book = BooksAPI.getInstance(BookDetailActivity.this)
                        .getBook(params[0]);
            } catch (AppException e) {
                Log.d(TAG, e.getMessage(), e);
            }
            return book;
        }

        @Override
        protected void onPostExecute(Book result) {
            loadLibro(result);
            if (pd != null) {
                pd.dismiss();
            }
        }

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(BookDetailActivity.this);
            pd.setTitle("Loading...");
            pd.setCancelable(false);
            pd.setIndeterminate(true);
            pd.show();
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_books_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.criticas:// del menu_libros_xml
                Intent intent = new Intent(this, CriticaActivity.class);

                intent.putExtra("idlibro", idlibro);

                startActivity(intent);
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }


}

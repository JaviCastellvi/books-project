package edu.upc.eetac.dsa.javicastellvi.books;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;

import java.util.ArrayList;

import edu.upc.eetac.dsa.javicastellvi.books.api.AppException;
import edu.upc.eetac.dsa.javicastellvi.books.api.BooksAPI;
import edu.upc.eetac.dsa.javicastellvi.books.api.Critica;

/**
 * Created by Javi on 07/05/2015.
 */
public class WriteCriticaActivity extends Activity {
    private final static String TAG = WriteCriticaActivity.class.getName();

    private class PostCriticaTask extends AsyncTask<String, Void, Critica> {
        private ProgressDialog pd;

        @Override
        protected Critica doInBackground(String... params) {
            Critica critica = null;

            try {
                critica = BooksAPI.getInstance(WriteCriticaActivity.this)
                        .createCritica(params[0]);
            } catch (AppException e) {
                Log.e(TAG, e.getMessage(), e);
            }
            return critica;
        }

        @Override
        protected void onPostExecute(Critica result) {
            showCriticas(result);
            if (pd != null) {
                pd.dismiss();
            }
        }

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(WriteCriticaActivity.this);

            pd.setCancelable(false);
            pd.setIndeterminate(true);
            pd.show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_critica_layout);

    }

    public void cancel(View v) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void postCritica(View v) {

        EditText etContent = (EditText) findViewById(R.id.etTexto);


        String texto = etContent.getText().toString();

        (new PostCriticaTask()).execute(texto);
    }

    private void showCriticas(Critica result) {
        String json = new Gson().toJson(result);
        Bundle data = new Bundle();
        data.putString("json-critica", json);
        Intent intent = new Intent();
        intent.putExtras(data);
        setResult(RESULT_OK, intent);
        finish();
    }


}

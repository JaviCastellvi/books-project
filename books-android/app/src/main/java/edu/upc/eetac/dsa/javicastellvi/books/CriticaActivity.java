package edu.upc.eetac.dsa.javicastellvi.books;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.gson.Gson;

import java.util.ArrayList;

import edu.upc.eetac.dsa.javicastellvi.books.api.AppException;
import edu.upc.eetac.dsa.javicastellvi.books.api.BooksAPI;
import edu.upc.eetac.dsa.javicastellvi.books.api.Critica;
import edu.upc.eetac.dsa.javicastellvi.books.api.CriticasCollection;


/**
 * Created by Javi on 03/05/2015.
 */
public class CriticaActivity extends ListActivity {
    private final static String TAG = CriticaActivity.class.toString();
    ArrayList<Critica> criticaList;
    private CriticaAdapter adapter;
    String idlibro;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criticas);
        idlibro = (String) getIntent().getExtras().get("idlibro");

        criticaList = new ArrayList<Critica>();
        adapter = new CriticaAdapter(this, criticaList);
        setListAdapter(adapter);
        (new FetchCriticasTask()).execute(idlibro);
    }

    private void addOpiniones(CriticasCollection criticas){
        criticaList.addAll(criticas.getCriticas());
        adapter.notifyDataSetChanged();

    }




    private class FetchCriticasTask extends
            AsyncTask<String, Void, CriticasCollection> {
        private ProgressDialog pd;

        @Override
        protected CriticasCollection doInBackground(String... params) {
            CriticasCollection criticas = null;
            try {
                criticas = BooksAPI.getInstance(CriticaActivity.this)
                        .getCritica(params[0]);


            } catch (AppException e) {
                e.printStackTrace();
            }
            return criticas;
        }

        @Override
        protected void onPostExecute(CriticasCollection result) {
            addOpiniones(result);
            if (pd != null) {
                pd.dismiss();
            }
        }

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(CriticaActivity.this);
            pd.setTitle("Searching...");
            pd.setCancelable(false);
            pd.setIndeterminate(true);
            pd.show();
        }



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_criticas_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miWrite:
                Intent intent = new Intent(this, WriteCriticaActivity.class);
                startActivityForResult(intent, WRITE_ACTIVITY);
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private final static int WRITE_ACTIVITY = 0;
    private ArrayList<Critica> criticasList;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {

            case WRITE_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    Bundle res = data.getExtras();
                    String jsonCritica = res.getString("json-critica");
                    Critica critica = new Gson().fromJson(jsonCritica, Critica.class);
                    criticasList.add(0, critica);
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }

}

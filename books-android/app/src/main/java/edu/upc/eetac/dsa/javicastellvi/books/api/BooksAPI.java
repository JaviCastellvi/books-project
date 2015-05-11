package edu.upc.eetac.dsa.javicastellvi.books.api;

/**
 * Created by Javi on 10/04/2015.
 */

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class BooksAPI {
    private final static String TAG = BooksAPI.class.getName();
    private static BooksAPI instance = null;
    private URL url;
    private String id;

    private BooksRootAPI rootAPI = null;

    private BooksAPI(Context context) throws IOException, AppException {
        super();

        AssetManager assetManager = context.getAssets();
        Properties config = new Properties();
        config.load(assetManager.open("config.properties"));
        String urlHome = config.getProperty("books.home");
        url = new URL(urlHome);

        Log.d("LINKS", url.toString());
        getRootAPI();
    }

    public final static BooksAPI getInstance(Context context) throws AppException {
        if (instance == null)
            try {
                instance = new BooksAPI(context);
            } catch (IOException e) {
                throw new AppException(
                        "Can't load configuration file");
            }
        return instance;
    }

    private void getRootAPI() throws AppException {
        Log.d(TAG, "getRootAPI()");
        rootAPI = new BooksRootAPI();
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.connect();
        } catch (IOException e) {
            throw new AppException(
                    "Can't connect to Books API Web Service");
        }

        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            JSONObject jsonObject = new JSONObject(sb.toString());
            JSONArray jsonLinks = jsonObject.getJSONArray("links");
            parseLinks(jsonLinks, rootAPI.getLinks());
        } catch (IOException e) {
            throw new AppException(
                    "Can't get response from Books API Web Service");
        } catch (JSONException e) {
            throw new AppException("Error parsing Books Root API");
        }

    }

    public BooksCollection getBooks() throws AppException {

        Log.d(TAG, "getBooks()");
        BooksCollection books = new BooksCollection();

        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) new URL(rootAPI.getLinks()
                    .get("books").getTarget()).openConnection();

            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.connect();
            System.out.println("hemos conectado");
        } catch (IOException e) {
            throw new AppException(
                    "Can't connect to Books API Web Service");
        }

        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            JSONObject jsonObject = new JSONObject(sb.toString());

            JSONArray jsonLinks = jsonObject.getJSONArray("links");

            parseLinks(jsonLinks, books.getLinks());

            books.setNewestTimestamp(jsonObject.getLong("newestTimestamp"));
            books.setOldestTimestamp(jsonObject.getLong("oldestTimestamp"));
            JSONArray jsonBooks = jsonObject.getJSONArray("books");

            for (int i = 0; i < jsonBooks.length(); i++) {
                Book book = new Book();
                JSONObject jsonBook = jsonBooks.getJSONObject(i);
                book.setTitulo(jsonBook.getString("titulo"));
                book.setAutor(jsonBook.getString("autor"));
                book.setIdioma(jsonBook.getString("idioma"));
                book.setEdicion(jsonBook.getString("edicion"));
                book.setIdlibro(jsonBook.getString("idlibro"));
                book.setFecha_edicion(jsonBook.getLong("fecha_edicion"));
                book.setFecha_impresion(jsonBook.getLong("fecha_impresion"));
                book.setEditorial(jsonBook.getString("editorial"));
                jsonLinks = jsonBook.getJSONArray("links");
                parseLinks(jsonLinks, book.getLinks());
                books.getBooks().add(book);
            }
        } catch (IOException e) {
            throw new AppException(
                    "Can't get response from Books API Web Service");
        } catch (JSONException e) {
            throw new AppException("Error parsing Books Root API");
        }

        return books;
    }
    public Book getBook(String urlLibro) throws AppException {
        Book book = new Book();
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(urlLibro);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            JSONObject jsonBook = new JSONObject(sb.toString());
            book.setTitulo(jsonBook.getString("titulo"));
            book.setAutor(jsonBook.getString("autor"));
            book.setIdioma(jsonBook.getString("idioma"));
            book.setEdicion(jsonBook.getString("edicion"));
            book.setIdlibro(jsonBook.getString("idlibro"));
            book.setFecha_edicion(jsonBook.getLong("fecha_edicion"));
            book.setFecha_impresion(jsonBook.getLong("fecha_impresion"));
            book.setEditorial(jsonBook.getString("editorial"));
            JSONArray jsonLinks = jsonBook.getJSONArray("links");
            parseLinks(jsonLinks, book.getLinks());
        } catch (MalformedURLException e) {
            Log.e(TAG, e.getMessage(), e);
            throw new AppException("Bad sting url");
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
            throw new AppException("Exception when getting the sting");
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
            throw new AppException("Exception parsing response");
        }

        return book;
    }
    private void parseLinks(JSONArray jsonLinks, Map<String, Link> map)
            throws AppException, JSONException {
        for (int i = 0; i < jsonLinks.length(); i++) {
            Link link = null;
            try {
                link = SimpleLinkHeaderParser
                        .parseLink(jsonLinks.getString(i));
            } catch (Exception e) {
                throw new AppException(e.getMessage());
            }
            String rel = link.getParameters().get("rel");
            String rels[] = rel.split("\\s");
            for (String s : rels)
                map.put(s, link);
        }
    }
    public CriticasCollection getCritica(String idlibro) throws AppException {
        CriticasCollection criticas = new CriticasCollection();
    id= idlibro;

        HttpURLConnection urlConnection = null;
        try {
            String preURL = rootAPI.getLinks().get("books").getTarget();
            String URL = preURL + "/criticas/" + idlibro;
            System.out.println(URL);
            urlConnection = (HttpURLConnection) new URL(URL).openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.connect();
            System.out.println("hemos conectado");
        } catch (IOException e) {
            throw new AppException(
                    "Can't connect to Beeter API Web Service");
        }
        BufferedReader reader;

        try {
            reader = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            JSONObject jsonObject = new JSONObject(sb.toString());

            JSONArray jsonLinks = jsonObject.getJSONArray("links");

            parseLinks(jsonLinks, criticas.getLinks());


            JSONArray jsonCriticas = jsonObject.getJSONArray("criticas");


            for (int i = 0; i < jsonCriticas.length(); i++) {
                Critica critica = new Critica();
                JSONObject jsonCritica = jsonCriticas.getJSONObject(i);
                critica.setId(jsonCritica.getString("idcritica"));
                critica.setUsername(jsonCritica.getString("name"));
                critica.setIdLibro(jsonCritica.getInt("libro"));
                critica.setContenido(jsonCritica.getString("texto"));
                critica.setLastModified(jsonCritica.getLong("lastModified"));
             // jsonLinks = jsonCritica.getJSONArray("links");
             // parseLinks(jsonLinks, critica.getLinks());
                criticas.getCriticas().add(critica);
            }
        } catch (IOException e) {
            throw new AppException(
                    "Can't get response from Books API Web Service");
        } catch (JSONException e) {
            throw new AppException("Error parsing Books Root API");
        }

        return criticas;
    }
    public Critica createCritica(String texto) throws AppException {
        Critica critica = new Critica();
        int libro = Integer.parseInt(id);
        critica.setIdLibro(libro);
        critica.setName("Test");
        critica.setUsername("test");
        critica.setContenido(texto);
        HttpURLConnection urlConnection = null;

        try {
            JSONObject jsonCritica = createJsonCritica(critica);
            String preURL = rootAPI.getLinks().get("books").getTarget();
            String URL = preURL + "/criticas/" + id;
       //    int code = urlConnection.getResponseCode();
            System.out.println(URL);
            urlConnection = (HttpURLConnection) new URL(URL).openConnection();
            urlConnection.setRequestProperty("Accept",
                    MediaType.BOOKS_API_CRITICA);
            urlConnection.setRequestProperty("Content-Type",
                    MediaType.BOOKS_API_CRITICA);
            urlConnection.setRequestMethod("POST");

            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.connect();
            System.out.println("LLEGANDOALFINAL");
            PrintWriter writer = new PrintWriter(
                    urlConnection.getOutputStream());
            writer.println(jsonCritica.toString());
            writer.close();
            int rc = urlConnection.getResponseCode();

                BufferedReader reader = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            jsonCritica = new JSONObject(sb.toString());
            critica.setId(jsonCritica.getString("idcritica"));
            critica.setLastModified(jsonCritica.getLong("lastModified"));
            critica.setIdLibro(jsonCritica.getInt("libro"));
            critica.setName(jsonCritica.getString("name"));
            critica.setContenido(jsonCritica.getString("texto"));
            critica.setUsername(jsonCritica.getString("username"));
            //  JSONArray jsonLinks = jsonCritica.getJSONArray("links");
            //  parseLinks(jsonLinks, critica.getLinks());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
            throw new AppException("Error parsing response");
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
            throw new AppException("Error getting response");
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
        return critica;
    }

    private JSONObject createJsonCritica(Critica critica) throws JSONException {
        JSONObject jsonCritica = new JSONObject();
        jsonCritica.put("libro", critica.getIdLibro());
        jsonCritica.put("name", critica.getName());
        jsonCritica.put("texto", critica.getContenido());
        jsonCritica.put("username", critica.getUsername());


        return jsonCritica;
    }



}

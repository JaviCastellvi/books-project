package edu.upc.eetac.dsa.javicastellvi.books.api;

/**
 * Created by Javi on 10/04/2015.
 */
import java.util.HashMap;
import java.util.Map;

public class Book {



    private String idlibro;
    private String titulo;
    private String autor;
    private String idioma;
    private String edicion;
    private int idautor;
    private long fecha_edicion;
    private long fecha_impresion;
    private String editorial;
    private String ETag;
    private Map<String, Link> links = new HashMap<String, Link>();

    public long getFecha_edicion() {
        return fecha_edicion;
    }

    public void setFecha_edicion(long fecha_edicion) {
        this.fecha_edicion = fecha_edicion;
    }

    public long getFecha_impresion() {
        return fecha_impresion;
    }

    public void setFecha_impresion(long fecha_impresion) {
        this.fecha_impresion = fecha_impresion;
    }






    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getIdlibro() {
        return idlibro;
    }

    public void setIdlibro(String idlibro) {
        this.idlibro = idlibro;
    }

    public int getIdautor() {
        return idautor;
    }

    public void setIdautor(int idautor) {
        this.idautor = idautor;
    }






    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }



    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }




    public String getEdicion() {
        return edicion;
    }

    public void setEdicion(String edicion) {
        this.edicion = edicion;
    }
    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getETag() {
        return ETag;
    }

    public void setETag(String ETag) {
        this.ETag = ETag;
    }


    public Map<String, Link> getLinks() {
        return links;
    }
}

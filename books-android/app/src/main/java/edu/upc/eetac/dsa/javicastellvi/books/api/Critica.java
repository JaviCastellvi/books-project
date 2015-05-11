package edu.upc.eetac.dsa.javicastellvi.books.api;

/**
 * Created by Javi on 02/05/2015.
 */

import java.util.HashMap;
import java.util.Map;

public class Critica {
    private Map<String, Link> links = new HashMap<>();


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;
    private String username;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    private String fecha;
    private String contenido;
    private int idLibro;
    private long lastModified;

    public Map<String, Link> getLinks() {
        return links;
    }
    public void setLinks(Map<String, Link> links) {
        this.links = links;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public String getContenido() {
        return contenido;
    }
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
    public int getIdLibro() {
        return idLibro;
    }
    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }
    public long getLastModified() {
        return lastModified;
    }
    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }


}

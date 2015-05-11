package edu.upc.eetac.dsa.javicastellvi.books.api;

/**
 * Created by Javi on 02/05/2015.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CriticasCollection {

    private Map<String, Link> links = new HashMap<>();
    private List<Critica> criticas = new ArrayList<>();;

    public Map<String, Link> getLinks() {
        return links;
    }
    public void setLinks(Map<String, Link> links) {
        this.links = links;
    }
    public List<Critica> getCriticas() {
        return criticas;
    }
    public void setCriticas(List<Critica> opiniones) {
        this.criticas = criticas;
    }
    public void addCritica (Critica critica) {
        criticas.add(critica);
    }




}

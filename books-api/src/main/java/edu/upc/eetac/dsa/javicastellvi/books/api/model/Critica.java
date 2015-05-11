package edu.upc.eetac.dsa.javicastellvi.books.api.model;

import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLink.Style;
import org.glassfish.jersey.linking.InjectLinks;

import edu.upc.eetac.dsa.javicastellvi.books.api.BooksResource;
import edu.upc.eetac.dsa.javicastellvi.books.api.CriticasResource;
import edu.upc.eetac.dsa.javicastellvi.books.api.MediaType;

public class Critica {
//@InjectLinks({
	//@InjectLink(value = "/books/criticas/{libro}", style = Style.ABSOLUTE, rel = "focus", title = "Criticas Libro", type = MediaType.BOOKS_API_CRITICA_COLLECTION, bindings = { @Binding(name = "libro", value = "${instance.libro}") }) })

	
	private int libro;
	public int getlibro() {
		return libro;
	}
	public void setlibro(int libro) {
		this.libro = libro;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	
	public long getLastModified() {
		return lastModified;
	}
	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}
	public int getIdcritica() {
		return idcritica;
	}
	public void setIdcritica(int idcritica) {
		this.idcritica = idcritica;
	}
	private int idcritica;
	
	private String username;
	private String name;
	private String texto;
	private long lastModified;
}

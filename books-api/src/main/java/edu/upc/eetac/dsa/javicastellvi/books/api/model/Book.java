package edu.upc.eetac.dsa.javicastellvi.books.api.model;
import java.util.List;
import javax.ws.rs.core.Link;

import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLink.Style;
import org.glassfish.jersey.linking.InjectLinks;
 
import edu.upc.eetac.dsa.javicastellvi.books.api.MediaType;
import edu.upc.eetac.dsa.javicastellvi.books.api.BooksResource;


 
public class Book {
	
	@InjectLinks({
	@InjectLink(resource = BooksResource.class, style = Style.ABSOLUTE, rel = "books", title = "Consulta ultimos libros", type = MediaType.BOOKS_API_BOOK_COLLECTION),
	@InjectLink(resource = BooksResource.class, style = Style.ABSOLUTE, rel = "self edit", title = "Accede a este libro", type = MediaType.BOOKS_API_BOOK, method = "getBook", bindings = @Binding(name = "idlibro", value = "${instance.idlibro}")),
	@InjectLink(value = "/books/criticas/{idlibro}", style = Style.ABSOLUTE, rel = "focus", title = "Criticas Libro", type = MediaType.BOOKS_API_BOOK, bindings = { @Binding(name = "idlibro", value = "${instance.idlibro}") }) })
private List<Link> links;
	
	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
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

	public String getEditorial() {
		return editorial;
	}
	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}

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


	public int getIdautor() {
		return idautor;
	}
	public void setIdautor(int idautor) {
		this.idautor = idautor;
	}

	

	public int getIdlibro() {
		return idlibro;
	}

	public void setIdlibro(int idlibro) {
		this.idlibro = idlibro;
	}



	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}



	public List<Critica> getCriticas() {
		return criticas;
	}

	public void setCriticas(List<Critica> criticas) {
		this.criticas = criticas;
	}

	public void addCritica(Critica critica) {
		criticas.add(critica);
	}

	private int idlibro;
	private String titulo;
	private String autor;
	private String idioma;
	private String edicion;
	private int idautor;
	private long fecha_edicion;
	private long fecha_impresion;
	private String editorial;
	private List<Critica> criticas;
	
}

package edu.upc.eetac.dsa.javicastellvi.books.api.model;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Link;

import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;
import org.glassfish.jersey.linking.InjectLink.Style;

import edu.upc.eetac.dsa.javicastellvi.books.api.BooksResource;
import edu.upc.eetac.dsa.javicastellvi.books.api.MediaType;

public class BooksCollection {

		@InjectLinks({
			@InjectLink(resource = BooksResource.class, style = Style.ABSOLUTE, rel = "create-book", title = "Crear libro", type = MediaType.BOOKS_API_BOOK),
			@InjectLink(value = "/books/criticas", style = Style.ABSOLUTE, rel = "criticas", title = "Mostras todas la criticas", type = MediaType.BOOKS_API_BOOK),
			@InjectLink(value = "/books?before={before}", style = Style.ABSOLUTE, rel = "previous", title = "Libros Anteriores", type = MediaType.BOOKS_API_BOOK_COLLECTION, bindings = { @Binding(name = "before", value = "${instance.oldestTimestamp}") }),
			@InjectLink(value = "/books?after={after}", style = Style.ABSOLUTE, rel = "current", title = "Libros Nuevos", type = MediaType.BOOKS_API_BOOK_COLLECTION, bindings = { @Binding(name = "after", value = "${instance.newestTimestamp}") }) })
		
		private List<Link> links;
	private List<Book> books;
	private long newestTimestamp;
	private long oldestTimestamp;
 
	
	public List<Link> getLinks() {
		return links;
	}
 
	public void setLinks(List<Link> links) {
		this.links = links;
	}
	
	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}
	
	public void addBook(Book book){
		books.add(book);
	}

		
		public BooksCollection() {
			super();
			books = new ArrayList<>();
		}

		public long getNewestTimestamp() {
			return newestTimestamp;
		}

		public void setNewestTimestamp(long newestTimestamp) {
			this.newestTimestamp = newestTimestamp;
		}

		public long getOldestTimestamp() {
			return oldestTimestamp;
		}

		public void setOldestTimestamp(long oldestTimestamp) {
			this.oldestTimestamp = oldestTimestamp;
		}

	
	 

	
}

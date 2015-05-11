package edu.upc.eetac.dsa.javicastellvi.books.api.model;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Link;

import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;
import org.glassfish.jersey.linking.InjectLink.Style;

import edu.upc.eetac.dsa.javicastellvi.books.api.MediaType;
import edu.upc.eetac.dsa.javicastellvi.books.api.CriticasResource;

public class CriticasCollection {
	@InjectLinks({
		@InjectLink(value = "/books/criticas", style = Style.ABSOLUTE, rel = "focus", title = "Criticas Libro", type = MediaType.BOOKS_API_CRITICA_COLLECTION) })

	
		private List<Link> links;
	

		
	
		private List<Critica> criticas;
		
		
		public List<Link> getLinks() {
			return links;
		}

		public void setLinks(List<Link> links) {
			this.links = links;
		}
		
		public CriticasCollection() {
			super();
			criticas = new ArrayList<>();
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

		

	
	
}

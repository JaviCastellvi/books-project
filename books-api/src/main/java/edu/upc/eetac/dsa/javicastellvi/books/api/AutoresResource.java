package edu.upc.eetac.dsa.javicastellvi.books.api;

import javax.sql.DataSource;
import javax.ws.rs.Path;

import java.io.Console;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import edu.upc.eetac.dsa.javicastellvi.books.api.model.Autor;
import edu.upc.eetac.dsa.javicastellvi.books.api.model.Book;
import edu.upc.eetac.dsa.javicastellvi.books.api.model.Critica;

@Path("/books")
public class AutoresResource {
	private DataSource ds = DataSourceSPA.getInstance().getDataSource();
	@Context
	private SecurityContext security;
	private String GET_AUTOR_ID_QUERY = "SELECT * FROM autores where idautor=?";
	
	private void validateAutor(Autor autor) {
		if (autor.getAutor() != null && autor.getAutor().length() > 70)
			throw new BadRequestException(
					"Title can't be greater than 70 characters.");
		
	}
	private String INSERT_AUTOR_QUERY = "INSERT INTO autores(autor) VALUES (?)";
	 
	@POST
	@Path("/autores")
	@Consumes(MediaType.BOOKS_API_AUTOR)
	@Produces(MediaType.BOOKS_API_AUTOR)
	public Autor createAutor(Autor autor) {
		validateAutor(autor);
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
	 
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(INSERT_AUTOR_QUERY,
					Statement.RETURN_GENERATED_KEYS);
	 
			stmt.setString(1, autor.getAutor());			
			
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				int idautor = rs.getInt(1);
	 
				autor = getAutorFromDatabase(Integer.toString(idautor));
			} else {
				// Something has failed...
			}
		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}
	 
		return autor;
	}
	
	private String DELETE_AUTOR_QUERY = "delete from autores where idautor=?";
	 
	@DELETE
	@Path("/autores/{idautor}")
	public void deleteAutor(@PathParam("idautor") String idautor) {
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
	 
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(DELETE_AUTOR_QUERY);
			stmt.setInt(1, Integer.valueOf(idautor));
	 
			int rows = stmt.executeUpdate();
			if (rows == 0)
				throw new NotFoundException("There's no libros with idautor="
						+ idautor);
				;// Deleting inexistent sting
		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}
	}
	private void validateUpdateAutor(Autor autor) {
		if (autor.getAutor() != null && autor.getAutor().length() > 70)
			throw new BadRequestException(
					"Title can't be greater than 70 characters.");
		
	}
	private String UPDATE_AUTOR_QUERY = "update autores set autor=ifnull(?, autor) where idautor=?";
	 
	@PUT
	@Path("/autores/{idautor}")
	@Consumes(MediaType.BOOKS_API_AUTOR)
	@Produces(MediaType.BOOKS_API_AUTOR)
	public Autor updateAutor(@PathParam("idautor") String idautor, Autor autor) {
		validateUpdateAutor(autor);
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
	 
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(UPDATE_AUTOR_QUERY);
			stmt.setString(1, autor.getAutor());
			stmt.setInt(2, Integer.valueOf(idautor));
	 
			int rows = stmt.executeUpdate();
			if (rows == 1)
				autor = getAutorFromDatabase(idautor);
			else {
				throw new NotFoundException("There's no libros with idautor="
						+ idautor);
				// Updating inexistent sting
			}
	 
		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}
	 
		return autor;
		}
	
	// Método para recuperar autor de la BD
		private Autor getAutorFromDatabase(String idautor) {
			Autor autor = new Autor();

			Connection conn = null;
			try {
				conn = ds.getConnection();
			} catch (SQLException e) {
				throw new ServerErrorException("Could not connect to the database",
						Response.Status.SERVICE_UNAVAILABLE);
			}

			PreparedStatement stmt = null;
			try {
				stmt = conn.prepareStatement(GET_AUTOR_ID_QUERY);
				stmt.setString(1, idautor);
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {
					autor.setIdautor(rs.getInt("idautor"));
					
				} else {
					throw new NotFoundException("There's no author with idautor="
							+ idautor);
				}
			} catch (SQLException e) {
				throw new ServerErrorException(e.getMessage(),
						Response.Status.INTERNAL_SERVER_ERROR);
			} finally {
				try {
					if (stmt != null)
						stmt.close();
					conn.close();
				} catch (SQLException e) {
				}
			}
			return autor;
		}
}

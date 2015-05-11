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

import edu.upc.eetac.dsa.javicastellvi.books.api.model.Critica;
import edu.upc.eetac.dsa.javicastellvi.books.api.model.CriticasCollection;

@Path("/books")
public class CriticasResource {
	private DataSource ds = DataSourceSPA.getInstance().getDataSource();
	@Context
	private SecurityContext security;
	private String GET_CRITICAS_QUERY = "SELECT * FROM critica";
	 
	@GET
	@Path("/criticas")
	@Produces(MediaType.BOOKS_API_CRITICA_COLLECTION)
	public CriticasCollection getCriticas() {
		CriticasCollection criticas = new CriticasCollection();
		System.out.println("hola");
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
	 
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(GET_CRITICAS_QUERY);
			
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Critica critica = new Critica();
				critica.setIdcritica(rs.getInt("id"));
				critica.setUsername(rs.getString("username"));
				critica.setName(rs.getString("name"));
				critica.setlibro(rs.getInt("libro"));
				critica.setTexto(rs.getString("texto"));
				critica.setLastModified(rs.getTimestamp("last_modified")
						.getTime());
				
				criticas.addCritica(critica);
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
	 
		return criticas;
	}
	
	private String GET_CRITICA_BY_ID_QUERY = "SELECT * FROM critica WHERE critica.libro=?";
	 
	@GET
	@Path("/criticas/{libro}")
	@Produces(MediaType.BOOKS_API_CRITICA_COLLECTION)
	public CriticasCollection getCriticaCollection(@PathParam("libro") String libro) {
		
		CriticasCollection criticas = new CriticasCollection();
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
	 
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(GET_CRITICA_BY_ID_QUERY);
			stmt.setInt(1, Integer.valueOf(libro));
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				Critica critica = new Critica();
				critica.setIdcritica(rs.getInt("id"));
				critica.setUsername(rs.getString("username"));
				critica.setName(rs.getString("name"));
				critica.setlibro(rs.getInt("libro"));
				critica.setTexto(rs.getString("texto"));
				critica.setLastModified(rs.getTimestamp("last_modified")
						.getTime());
				criticas.addCritica(critica);
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
	 
		return criticas;
	}
	private Critica getCriticaFromDatabase(String id) {
		Critica critica = new Critica();
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
	 
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(GET_CRITICA_ID_QUERY);
			stmt.setInt(1, Integer.valueOf(id));
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				
				critica.setIdcritica(rs.getInt("id"));
				critica.setUsername(rs.getString("username"));
				critica.setName(rs.getString("name"));
				critica.setlibro(rs.getInt("libro"));
				critica.setTexto(rs.getString("texto"));
				critica.setLastModified(rs.getTimestamp("last_modified")
						.getTime());
				
			}else{
				throw new NotFoundException("There's no review with id="
						+ id);
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
	 
		return critica;
	}
	private String GET_CRITICA_ID_QUERY = "SELECT * FROM critica where id=?";
	//public Critica getCritica( String id) {
		
		
	//}
	public Response getCritica(@PathParam("id") String id,
			@Context Request request) {
		// Create CacheControl
		CacheControl cc = new CacheControl();
	 
		Critica critica = getCriticaFromDatabase(id);
	 
		// Calculate the ETag on last modified date of user resource
		EntityTag eTag = new EntityTag(Long.toString(critica.getLastModified()));
	 
		// Verify if it matched with etag available in http request
		Response.ResponseBuilder rb = request.evaluatePreconditions(eTag);
	 
		// If ETag matches the rb will be non-null;
		// Use the rb to return the response without any further processing
		if (rb != null) {
			return rb.cacheControl(cc).tag(eTag).build();
		}
	 
		// If rb is null then either it is first time request; or resource is
		// modified
		// Get the updated representation and return with Etag attached to it
		rb = Response.ok(critica).cacheControl(cc).tag(eTag);
	 
		return rb.build();
	}
	private void validateCritica(Critica critica) {
		if (critica.getUsername() != null && critica.getUsername().length() > 20)
			throw new BadRequestException(
					"Username can't be greater than 100 characters.");
		if (critica.getName() != null && critica.getName().length() > 70)
			throw new BadRequestException(
					"Name can't be greater than 100 characters.");
		if (critica.getTexto() != null && critica.getTexto().length() > 500)
			throw new BadRequestException(
					"Text can't be greater than 500 characters.");
		
	}
	private String INSERT_CRITICA_IDBOOK_QUERY = "INSERT INTO critica (username, name, libro, texto) VALUES (?, ?, ?, ?)";
	 
	@POST
	@Path("/criticas/{libro}")
	@Consumes(MediaType.BOOKS_API_CRITICA)
	@Produces(MediaType.BOOKS_API_CRITICA)
	public Critica createCritica(Critica critica) {
	/*	if (!security.isUserInRole("registrado")) {
			throw new ForbiddenException("No estas registrado, no puedes crear opiniones");
			}*/
		validateCritica(critica);
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
	 
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(INSERT_CRITICA_IDBOOK_QUERY,
					Statement.RETURN_GENERATED_KEYS);
	 
			stmt.setString(1, critica.getUsername());
			//stmt.setString(1, security.getUserPrincipal().getName());
			stmt.setString(2, critica.getName());
			stmt.setInt(3, critica.getlibro());
			stmt.setString(4, critica.getTexto());
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				int id = rs.getInt(1);
				System.out.println(id);
				critica = getCriticaFromDatabase(Integer.toString(id));
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
	 
		return critica;
	}
	
	private String DELETE_CRITICA_QUERY = "delete from critica where id=?";
	 
	@DELETE
	@Path("/criticas/{id}")
	public void deleteCritica(@PathParam("id") String id) {
		if (!security.isUserInRole("registrado")) {
			throw new ForbiddenException("No estas registrado, no puedes borrar opiniones");
			}
	
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
	 
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(DELETE_CRITICA_QUERY);
			stmt.setInt(1, Integer.valueOf(id));
	 
			int rows = stmt.executeUpdate();
			if (rows == 0)
				throw new NotFoundException("There's no reviwe with id="
						+ id);
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
	
	private void validateUpdateCritica(Critica critica) {
		if (critica.getTexto() != null && critica.getTexto().length() > 500)
			throw new BadRequestException(
					"Title can't be greater than 500 characters.");
		
	}
	private String UPDATE_CRITICA_IDBOOK_QUERY = "update critica set texto=ifnull(?, texto) where id=?";
	 
	@PUT
	@Path("criticas/{id}")
	@Consumes(MediaType.BOOKS_API_CRITICA)
	@Produces(MediaType.BOOKS_API_CRITICA)
	public Critica updateBook(@PathParam("id") String id, Critica critica) {
		if (!security.isUserInRole("registrado")) {
			throw new ForbiddenException("No estas registrado, no puedes borrar opiniones");
			}
	
		validateUpdateCritica(critica);
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
	 
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(UPDATE_CRITICA_IDBOOK_QUERY);
			stmt.setString(1, critica.getTexto());
			stmt.setInt(2, Integer.valueOf(id));
		    int rows = stmt.executeUpdate();
			
			if (rows == 1)
				critica = getCriticaFromDatabase(id);
			else {
				throw new NotFoundException("There's no review with id="
						+id);
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
	 
		return critica;
		}
		
}

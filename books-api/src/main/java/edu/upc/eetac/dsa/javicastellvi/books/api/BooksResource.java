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

import edu.upc.eetac.dsa.javicastellvi.books.api.model.Book;
import edu.upc.eetac.dsa.javicastellvi.books.api.model.BooksCollection;

@Path("/books")
public class BooksResource {
	private DataSource ds = DataSourceSPA.getInstance().getDataSource();
	@Context
	private SecurityContext security;
	//private String GET_BOOKS_QUERY = "SELECT * FROM libros";
	
	//private String GET_BOOKS_QUERY = "SELECT * FROM libro WHERE idlibro > ifnull(?, 1) order by idlibro asc limit ?";
	//private String GET_BOOKS_QUERY_FROM_LAST = "SELECT * FROM libro where idlibro < ifnull(?, 10) order by idlibro desc limit ?";
	private String GET_BOOKS_QUERY_FROM_LAST = "SELECT * FROM libro WHERE fecha_edicion > ? ORDER BY fecha_edicion DESC";
	private String GET_BOOKS_QUERY = "SELECT * FROM libro WHERE fecha_edicion < ifnull(?, now()) ORDER BY fecha_edicion DESC LIMIT ?";
	private String GET_BOOKS_BY_TITULO = "SELECT * FROM libro WHERE titulo like ? and idlibro > ifnull(?, 1) order by idlibro asc limit ?";
	private String GET_BOOKS_BY_TITULO_FROM_LAST = "SELECT * FROM libro WHERE titulo like ? and idlibro < ifnull(?, 10) order by idlibro desc limit ?";
	private String GET_BOOKS_BY_AUTOR = "SELECT * FROM libro where idautor like ? and idlibro > ifnull(?, 1) order by idlibro asc limit ?";
	private String GET_BOOKS_BY_AUTOR_FROM_LAST = "SELECT * FROM libro where idautor like ? and idlibro < ifnull(?, 10) order by idlibro desc limit ?";
	@GET
	@Produces(MediaType.BOOKS_API_BOOK_COLLECTION)
	/*public BooksCollection getBooks() {
		BooksCollection books = new BooksCollection();
	 
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
	 
		PreparedStatement stmt = null;
		try {
		
			stmt = conn.prepareStatement(GET_BOOKS_QUERY);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Book book = new Book();
				book.setIdlibro(rs.getInt("idlibros"));
				book.setTitulo(rs.getString("titulo"));
				book.setIdioma(rs.getString("idioma"));
				book.setEdicion(rs.getString("edicion"));
				book.setFecha_edicion(rs.getTimestamp("fecha_edicion").getTime()); 
				book.setFecha_impresion(rs.getTimestamp("fecha_impresion").getTime());
				book.setEditorial(rs.getString("editorial"));
				book.setIdautor(rs.getInt("idautor"));
				books.addBook(book);

			}
			
		} catch (SQLException e) {
			System.out.println("hola");
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
	 
		return books;
	}*/
	
	
	public BooksCollection getBooks(@QueryParam("length") int length,
			@QueryParam("before") long before, @QueryParam("after") long after) {
		BooksCollection books = new BooksCollection();
		
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
	 
		PreparedStatement stmt = null;
		try {
			boolean updateFromLast = after > 0;
			stmt = updateFromLast ? conn
					.prepareStatement(GET_BOOKS_QUERY_FROM_LAST) : conn
					.prepareStatement(GET_BOOKS_QUERY);
			if (updateFromLast) {
				stmt.setTimestamp(1, new Timestamp(after));
			} else {
				if (before > 0)
					stmt.setTimestamp(1, new Timestamp(before));
				else
					stmt.setTimestamp(1, null);
				length = (length <= 0) ? 20 : length;
				stmt.setInt(2, length);
			}
		
			
			ResultSet rs = stmt.executeQuery();
			boolean first = true;
			long oldestTimestamp = 0;
			while (rs.next()) {
				Book book = new Book();
				book.setIdlibro(rs.getInt("idlibro"));
				book.setTitulo(rs.getString("titulo"));
				book.setAutor(rs.getString("autor"));
				book.setIdioma(rs.getString("idioma"));
				book.setEdicion(rs.getString("edicion"));
				book.setFecha_edicion(rs.getTimestamp("fecha_edicion").getTime()); 
				book.setFecha_impresion(rs.getTimestamp("fecha_impresion").getTime());
				book.setEditorial(rs.getString("editorial"));
				book.setIdautor(rs.getInt("idautor"));
				oldestTimestamp= rs.getTimestamp("fecha_edicion").getTime();
				

				if (first) {
					first = false;
					books.setNewestTimestamp(book.getFecha_edicion());
				}
				books.addBook(book);

			}
			books.setOldestTimestamp(oldestTimestamp);
			
		} catch (SQLException e) {
			System.out.println("hola");
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
	 
		return books;
	}
	private Book getBookFromDatabase(String idlibro) {
		Book book = new Book();
		
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
	 
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(GET_BOOK_BY_ID_QUERY);
			stmt.setInt(1, Integer.valueOf(idlibro));
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				book.setIdlibro(rs.getInt("idlibro"));
				book.setTitulo(rs.getString("titulo"));
				book.setAutor(rs.getString("autor"));
				book.setIdioma(rs.getString("idioma"));
				book.setEdicion(rs.getString("edicion"));
				book.setFecha_edicion(rs.getTimestamp("fecha_edicion").getTime()); 
				book.setFecha_impresion(rs.getTimestamp("fecha_impresion").getTime());
				book.setEditorial(rs.getString("editorial"));
				book.setIdautor(rs.getInt("idautor"));
			}else{
				throw new NotFoundException("There's no libro with idlibro="
						+ idlibro);
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
	 
		return book;
	}
	
	private String GET_BOOK_BY_ID_QUERY = "SELECT * FROM libro WHERE idlibro=?";
	 
	@GET
	@Path("/{idlibro}")
	@Produces(MediaType.BOOKS_API_BOOK)
	//public Book getBook(@PathParam("idlibros") String idlibros) {
	
	//}
	
	public Response getBook(@PathParam("idlibro") String idlibro,
			@Context Request request) {
		// Create CacheControl
		CacheControl cc = new CacheControl();
		
		Book book = getBookFromDatabase(idlibro);
	 
		// Calculate the ETag on last modified date of user resource
		EntityTag eTag = new EntityTag(Long.toString(book.getFecha_edicion()));
	 
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
		rb = Response.ok(book).cacheControl(cc).tag(eTag);
	 
		return rb.build();
	}
	
	
	private void validateBook(Book book) {
		if (book.getTitulo() != null && book.getTitulo().length() > 100)
			throw new BadRequestException(
					"Title can't be greater than 100 characters.");
		if (book.getIdioma() != null && book.getIdioma().length() > 100)
			throw new BadRequestException(
					"Language can't be greater than 100 characters.");
		if (book.getEditorial() != null && book.getEditorial().length() > 100)
			throw new BadRequestException(
					"Editorial can't be greater than 100 characters.");
		if (book.getEdicion() != null && book.getEdicion().length() > 100)
			throw new BadRequestException(
					"Edition can't be greater than 100 characters.");
		
	}
	private String INSERT_BOOK_QUERY = "INSERT INTO libro(titulo, autor, idioma, edicion, editorial, idautor) VALUES (?, ?, ?, ?, ?, ?)";
	 
	@POST
	@Consumes(MediaType.BOOKS_API_BOOK)
	@Produces(MediaType.BOOKS_API_BOOK)
	public Book createSting(Book book) {
		validateBook(book);
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
	 
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(INSERT_BOOK_QUERY,
					Statement.RETURN_GENERATED_KEYS);
	 
			stmt.setString(1, book.getTitulo());
			stmt.setString(2, book.getAutor());
			stmt.setString(3, book.getIdioma());
			stmt.setString(4, book.getEdicion());
			stmt.setString(5, book.getEditorial());
			stmt.setInt(6, book.getIdautor());
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				int idlibro = rs.getInt(1);
	 
				book = getBookFromDatabase(Integer.toString(idlibro));
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
	 
		return book;
	}
	private String DELETE_BOOK_QUERY = "delete from libro where idlibro=?";
	 
	@DELETE
	@Path("/{idlibro}")
	public void deleteLibro(@PathParam("idlibro") String idlibro) {
		System.out.println(security.getUserPrincipal().getName());
		if (!security.getUserPrincipal().getName().equals("admin")) {
			throw new ForbiddenException("No eres el Admin, no puedes borrar libros");
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
			stmt = conn.prepareStatement(DELETE_BOOK_QUERY);
			stmt.setInt(1, Integer.valueOf(idlibro));
	 
			int rows = stmt.executeUpdate();
			if (rows == 0)
				throw new NotFoundException("There's no libros with idlibro="
						+ idlibro);
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
	private void validateUpdateBook(Book book) {
		if (book.getTitulo() != null && book.getTitulo().length() > 100)
			throw new BadRequestException(
					"Title can't be greater than 100 characters.");
		if (book.getIdioma() != null && book.getIdioma().length() > 100)
			throw new BadRequestException(
					"Language can't be greater than 100 characters.");
		if (book.getEdicion() != null && book.getEdicion().length() > 100)
			throw new BadRequestException(
					"Edicion can't be greater than 100 characters.");
	}
	private String UPDATE_BOOK_QUERY = "update libro set titulo=ifnull(?, titulo), idioma=ifnull(?, idioma), edicion=ifnull(?, edicion) where idlibro=?";
	 
	@PUT
	@Path("/{idlibro}")
	@Consumes(MediaType.BOOKS_API_BOOK)
	@Produces(MediaType.BOOKS_API_BOOK)
	public Book updateBook(@PathParam("idlibro") String idlibro, Book book) {
		System.out.println(security.getUserPrincipal());
		if (!security.isUserInRole("administrador")) {
			throw new ForbiddenException("No eres el Admin, no puedes actualizar libros");
			}
		validateUpdateBook(book);
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
		
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(UPDATE_BOOK_QUERY);
			
			stmt.setString(1, book.getTitulo());
			stmt.setString(2, book.getIdioma());
			stmt.setString(3, book.getEdicion());
			
			stmt.setInt(4, Integer.valueOf(idlibro));
			
			int rows = stmt.executeUpdate();
			if (rows == 1)
				book = getBookFromDatabase(idlibro);
			else {
				throw new NotFoundException("There's no libros with idlibro="
						+ idlibro);
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
	 
		return book;
		}
}

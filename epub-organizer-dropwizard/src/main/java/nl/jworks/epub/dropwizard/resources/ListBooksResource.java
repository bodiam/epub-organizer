package nl.jworks.epub.dropwizard.resources;

import com.yammer.metrics.annotation.Timed;
import nl.jworks.epub.domain.Book;
import org.bson.types.ObjectId;
import org.mongojack.JacksonDBCollection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
public class ListBooksResource {

    private JacksonDBCollection<Book, ObjectId> books;

    public ListBooksResource(JacksonDBCollection<Book, ObjectId> books) {
        this.books = books;
    }

    @GET
    @Timed
    public List<Book> listAll() {
        return books.find().toArray();

    }
}
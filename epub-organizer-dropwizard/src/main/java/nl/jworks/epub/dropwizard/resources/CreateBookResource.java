package nl.jworks.epub.dropwizard.resources;

import com.google.common.base.Optional;
import com.yammer.metrics.annotation.Timed;
import nl.jworks.epub.domain.Book;
import nl.jworks.epub.dropwizard.core.Saying;
import org.bson.types.ObjectId;
import org.mongojack.JacksonDBCollection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/hello-world")
@Produces(MediaType.APPLICATION_JSON)
public class CreateBookResource {

    private JacksonDBCollection<Book, ObjectId> books;

    public CreateBookResource(JacksonDBCollection<Book, ObjectId> books) {
        this.books = books;
    }

    @GET
    @Timed
    public Saying sayHello(@QueryParam("name") Optional<String> name) {
        return null;
    }
}
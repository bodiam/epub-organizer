package nl.jworks.epub.persistence.spring;

import nl.jworks.epub.domain.Author;
import nl.jworks.epub.domain.Book;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * An example repository interface that inherits the default CRUD methods from {@link org.springframework.data.mongodb.repository.MongoRepository}.
 */
@Repository
public interface BookRepository extends MongoRepository<Book, ObjectId> {

    List<Book> findByTitle(String title);

    @Query("{ 'title' : ?0 } ")
    List<Book> exampleOfCustomQueryUsingTitle(String title);

    @Query("{ 'authors.lastName' : ?0 } ")
    List<Book> findAllByAuthorLastname(String name);

    @Query("{ $and: [ { 'authors.firstName' : ?0 }, { 'authors.lastName' : ?1 } ] }")
    List<Book> findAllByAuthorFirstnameAndLastname(String firstName, String lastName);

    List<Book> findAllByAuthors(Author author);

    List<Book> findAllByPublicationDateAfterAndNumberOfPagesLessThan(Date publicationDate, int numberOfPages);
}

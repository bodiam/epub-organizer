package nl.jworks.epub.persistence.spring;

import nl.jworks.epub.domain.Binary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * An example repository interface that inherits the default CRUD methods from {@link org.springframework.data.mongodb.repository.MongoRepository}.
 */
@Repository
public interface BinaryRepository extends MongoRepository<Binary, String> {

}

package nl.jworks.epub.mongodb.plain;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import org.junit.Test;

import java.util.Set;

public class MongoJavaDriverTest {


    @Test
    public void connect() throws Exception {


        MongoClient mongoClient = new MongoClient();

        DB db = mongoClient.getDB( "epub" );

        Set<String> colls = db.getCollectionNames();

        for (String s : colls) {
            System.out.println(s);
        }

        System.out.println(db.getCollection("books").count());
    }
}

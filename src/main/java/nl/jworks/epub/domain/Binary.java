package nl.jworks.epub.domain;

import com.github.jmkgreen.morphia.annotations.Entity;
import com.github.jmkgreen.morphia.annotations.Id;
import org.bson.types.ObjectId;

@Entity("binaries")
public class Binary {

    @Id
    ObjectId id;

    private byte[] contents;

    private Binary() {
    }

    public Binary(byte[] contents) {
        this.contents = contents;
    }

    public byte[] getContents() {
        return contents;
    }
}

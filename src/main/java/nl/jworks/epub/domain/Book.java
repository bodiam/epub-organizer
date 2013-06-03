package nl.jworks.epub.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.jmkgreen.morphia.annotations.Entity;
import com.github.jmkgreen.morphia.annotations.Id;
import com.github.jmkgreen.morphia.annotations.Reference;
import com.google.common.base.Objects;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties({"firstAuthor"}) // mongojack
@Entity("books")                       // morphia
@Document(collection="books")          // spring
public class Book {

//    @Id                                         // morphia
//    @org.springframework.data.annotation.Id     // spring
//    ObjectId id;

    @Id
    @org.springframework.data.annotation.Id     // spring
    @javax.persistence.Id                       // mongojack
    public ObjectId id;

    private String source;

    private String title;
    private String language;
    private String isbn10;
    private String isbn13;
    private String summary;
    private String publisher;
    private Date publicationDate;
    private Date dateAdded = new Date();

    // private String dewey; // ddc

    private int numberOfPages;
    private int fileSizeInKb;

    // private String contents;

    private List<Author> authors = new ArrayList<>();
    private List<Tag> tags = new ArrayList<>();

    @Reference("cover")
    @DBRef
    private Binary cover;

    @Reference("epub")
    @DBRef
    private Binary epub;


    public void addAuthor(Author author) {
        this.authors.add(author);
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public Author getFirstAuthor() {
        if(authors.isEmpty()) {
            return null;
        } else {
            return authors.get(0);
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getFileSizeInKb() {
        return fileSizeInKb;
    }

    public void setFileSizeInKb(int fileSizeInKb) {
        this.fileSizeInKb = fileSizeInKb;
    }

    public String getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void addTag(Tag tag) {
        tags.add(tag);
    }

    public List<Tag> getTags() {
        return tags;
    }

    public Binary getEpub() {
        return epub;
    }

    public void setEpub(Binary epub) {
        this.epub = epub;
    }

    public Binary getCover() {
        return cover;
    }

    public void setCover(Binary cover) {
        this.cover = cover;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("authors", authors)
                .add("id", id)
                .add("title", title)
                .add("source", source)
                .add("language", language)
                .add("isbn10", isbn10)
                .add("isbn13", isbn13)
                .add("publisher", publisher)
                .add("publicationDate", publicationDate)
                .add("numberOfPages", numberOfPages)
                .add("fileSizeInKb", fileSizeInKb)
                .add("tags", tags)
                .add("cover", cover)
                .toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Book other = (Book) obj;

        return Objects.equal(this.title, other.title) &&
                Objects.equal(this.isbn10, other.isbn10) &&
                Objects.equal(this.isbn13, other.isbn13) &&
                Objects.equal(this.language, other.language) &&
                Objects.equal(this.authors, other.authors);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(title, isbn10, isbn13, language, authors);
    }
}

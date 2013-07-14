package nl.jworks.epub.domain;

import com.google.common.base.Objects;

import java.util.Date;
import java.util.List;

public class Author {

    private String firstName;
    private String lastName;
    private Date birthDate;

    private String biography;

    private List<Binary> binaries;

    private Author() { }

    public String getBiography() {
        return biography;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("firstName", firstName)
                .add("lastName", lastName)
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
        final Author other = (Author) obj;

        return Objects.equal(this.firstName, other.firstName) &&
                Objects.equal(this.lastName, other.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(firstName, lastName);
    }
}

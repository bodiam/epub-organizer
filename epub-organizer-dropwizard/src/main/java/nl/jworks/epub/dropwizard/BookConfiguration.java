package nl.jworks.epub.dropwizard;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class BookConfiguration extends Configuration {

    @JsonProperty
    @NotEmpty
    public String mongohost = "localhost";

    @Min(1)
    @Max(65535)
    @JsonProperty
    public int mongoport = 27017;

    @JsonProperty
    @NotEmpty
    public String mongodb = "epub";

    @JsonProperty
    public boolean prettyPrint = false;

    public String getMongodb() {
        return mongodb;
    }

    public String getMongohost() {
        return mongohost;
    }

    public int getMongoport() {
        return mongoport;
    }

    public boolean getPrettyPrint() {
        return prettyPrint;
    }
}

package nl.jworks.epub.scratchpad;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class JSoupTest {

    @Test
    public void extractText() {
        String html = "<html><body><p>hello!<div>hello world</div><br /><p></p><p>copyright erik</p></body>";

        Document document = Jsoup.parse(html);

        String text = document.body().text();

        assertThat(text, is("hello! hello world copyright erik"));
    }

}

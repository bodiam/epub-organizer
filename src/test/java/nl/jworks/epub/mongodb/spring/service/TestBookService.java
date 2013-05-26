package nl.jworks.epub.mongodb.spring.service;

import nl.jworks.epub.domain.Book;
import nl.jworks.epub.mongodb.spring.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestBookService {

    private BookRepository bookRepository;

    @Autowired
    public TestBookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book save(Book book) {
        return bookRepository.save(book);

    }
}

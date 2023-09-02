package com.example.demo.bookinventory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
public class BookInventoryController {

    @Autowired
    private BookInventoryRepository bookInvRepo;

    @GetMapping()
    public List<BookInventory> getAllBooks() {
        try {
            return bookInvRepo.findAll();
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/{id}")
    public BookInventory getBookById(@PathVariable Long id) {
        try {
            return bookInvRepo.findById(id).get();
        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping
    public BookInventory createBook(@RequestBody BookInventory book) {
        try {
            return bookInvRepo.save(book);
        } catch (Exception e) {
            return null;
        }
    }

    @PutMapping("/{id}")
    public BookInventory updateBook(@PathVariable Long id, @RequestBody BookInventory book) {
        try {
            BookInventory existingBook = bookInvRepo.findById(id).get();
            existingBook.setTitle(book.getTitle());
            existingBook.setAuthor(book.getAuthor());
            existingBook.setIsbn(book.getIsbn());
            existingBook.setPrice(book.getPrice());
            existingBook.setStock(book.getStock());
            existingBook.setPublicationDate(book.getPublicationDate());
            return bookInvRepo.save(existingBook);
        } catch (Exception e) {
            return null;
        }
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable Long id) {
        try {
            bookInvRepo.findById(id).get();
            bookInvRepo.deleteById(id);
            return "Book deleted successfully";
        } catch (Exception e) {
            return "Book not found";
        }
    }
}

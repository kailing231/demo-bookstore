package com.example.demo.bookinventory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.bookinventory.util.GlobalConstants;
import com.example.demo.bookinventory.util.PatchStockInput;

@RestController
@RequestMapping("/api/books")
public class BookInventoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookInventoryController.class);

    @Autowired
    private BookInventoryRepository bookInvRepo;

    @GetMapping()
    public ResponseEntity<?> getAllBooks() {
        LOGGER.info("Called BookInventoryController.getAllBooks");
        try {
            return new ResponseEntity<>(bookInvRepo.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error: exception = ", e.getMessage());
            return new ResponseEntity<>(GlobalConstants.ERROR_GENERAL, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(@PathVariable Long id) {
        LOGGER.info("Called BookInventoryController.getBookById");
        try {
            return new ResponseEntity<>(bookInvRepo.findById(id).get(), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error: id = {}, exception = {}", id, e.getMessage());
            return new ResponseEntity<>(GlobalConstants.ERROR_GENERAL, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/stock/{id}")
    public ResponseEntity<?> getStockById(@PathVariable Long id) {
        LOGGER.info("Called BookInventoryController.getStockById");
        try {
            Integer stockVal = bookInvRepo.findStockById(id);
            if(stockVal == null)
                return new ResponseEntity<>(GlobalConstants.ERROR_NOT_FOUND, HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(stockVal, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error: id = {}, exception = {}", id, e.getMessage());
            return new ResponseEntity<>(GlobalConstants.ERROR_GENERAL, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<?> createBook(@RequestBody BookInventory book) {
        LOGGER.info("Called BookInventoryController.createBook");
        try {
            return new ResponseEntity<>(bookInvRepo.save(book), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error: exception = {}", e.getMessage());
            return new ResponseEntity<>(GlobalConstants.ERROR_GENERAL, HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping(value = "/stock", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateStockValue(
            @RequestBody PatchStockInput input) {
        LOGGER.info("Called BookInventoryController.updateStockValue");
        boolean hasError = false;
        for (BookInventory book : input.getBooks()) {
            try {
                BookInventory existingBook = bookInvRepo.findById(book.getId()).get();
                existingBook.setStock(existingBook.getStock() + book.getStock());
                bookInvRepo.save(existingBook);
            } catch (Exception e) {
                hasError = true;
                LOGGER.error("exception = {}", e.getMessage());
            }
        }

        if (hasError)
            return new ResponseEntity<>(GlobalConstants.ERROR_GENERAL, HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>(GlobalConstants.SUCCESS, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        LOGGER.info("Called BookInventoryController.deleteBook");
        try {
            bookInvRepo.findById(id).get();
            bookInvRepo.deleteById(id);
            return new ResponseEntity<>(GlobalConstants.SUCCESS, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error: id = {}, exception = {}", id, e.getMessage());
            return new ResponseEntity<>(GlobalConstants.ERROR_GENERAL, HttpStatus.BAD_REQUEST);
        }
    }
}

package com.example.demo.bookinventory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookInventoryRepository extends JpaRepository<BookInventory, Long>{

    @Query(value = "SELECT b.stock FROM books b WHERE b.id = :id", nativeQuery = true)
    Integer findStockById(@Param("id") Long id);
}


package com.example.demo.bookinventory.util;

import java.util.List;

import com.example.demo.bookinventory.BookInventory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatchStockInput {

    private List<BookInventory> books;

}

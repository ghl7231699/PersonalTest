package com.mmc.sample;

import com.mmc.sample.Book;

interface BookController {

     List<Book> getBookList();

     void addBookInOut(inout Book book);
}

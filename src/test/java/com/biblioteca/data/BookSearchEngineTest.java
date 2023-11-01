/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.biblioteca.data;

import com.biblioteca.ControllerClass;
import com.biblioteca.data.entities.Book;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author kkraft
 */
@SpringBootTest(classes = ControllerClass.class)
public class BookSearchEngineTest {
    @Autowired
    ArquivoRepository arquivoRepository;
    
    BookSearchEngine booksearchengine = new BookSearchEngine("O Pequeno Principe",arquivoRepository);
    @BeforeEach
    public void beforeall(){
        booksearchengine = new BookSearchEngine("O Pequeno Principe",arquivoRepository);
    }
    public void StringFormatTest(){
        
    }
    @Test
    public void SearchTestTrue(){
        Optional<Book> book = booksearchengine.Search();
        assert book.isPresent() == true;
        
    }
}

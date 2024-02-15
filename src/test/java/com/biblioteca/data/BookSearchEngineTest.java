/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.biblioteca.data;

import com.biblioteca.data.repository.LivrosRepository;
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
    LivrosRepository arquivoRepository;
    
    //BookSearchEngine booksearchengine = new BookSearchEngine(arquivoRepository);
    @BeforeEach
    public void beforeall(){
        //booksearchengine = new BookSearchEngine(arquivoRepository);
        System.out.println("before each");
    }
    //@Test
   /* public void SearchTest(){
        System.out.println("books found:");
        System.out.println(booksearchengine.tituloSearch("O Pequeno Principe").isPresent());
        for(Book b:booksearchengine.tituloSearch("O Pequeno Principe").get()){
            System.out.println(b.getTitulo());
        }
    }*/
}

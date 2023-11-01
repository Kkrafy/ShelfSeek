/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.biblioteca.data;

import com.biblioteca.data.entities.Book;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author kkraft
 */
public class BookSearchEngine {
    public ArquivoRepository arquivoRepository;
    
    public String nomedolivroraw;
    public BookSearchEngine(String nomedolivro, ArquivoRepository arquivoRepository){
        this.nomedolivroraw = nomedolivro;
        this.arquivoRepository = arquivoRepository;
    }
    
  /*  public String nomeFormatter(){
        nomedolivroraw.
    }*/
    
    public Optional<Book> Search(){
        Iterable<Book> allbooks = arquivoRepository.findAll();
        for(Book b:allbooks){
            if(b.getNome().equals(nomedolivroraw)){
                return Optional.of(b);
            }
        }       
        return Optional.empty();
    }
    
    
    
}

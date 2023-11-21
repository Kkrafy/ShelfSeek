/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.biblioteca.data;

import com.biblioteca.data.entities.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author kkraft
 * to aqui javadoc
 */
public class BookSearchEngine {
    public ArquivoRepository arquivoRepository;
    
    public String nomedolivroraw;
    public String[] nomedolivroseparado;
    public BookSearchEngine(String nomedolivro, ArquivoRepository arquivoRepository){
        this.nomedolivroraw = nomedolivro;
        this.arquivoRepository = arquivoRepository;
        this.nomedolivroseparado = nomedolivroraw.split(" ");
    }
    
    
    public Optional<List<Book>> Search(){
        Iterable<Book> allbooks = arquivoRepository.findAll();
        List<Book> livros = new ArrayList<Book>();
        for(Book b:allbooks){
            if(b.getNome().equals(nomedolivroraw)){
                livros.add(0, b);
                continue;
            }
            String bnomeseparado[] = b.getNome().split(" ");
            for(String bnome:bnomeseparado){
                for(String nome:nomedolivroseparado){
                    if (nome.length() > 2 & bnome.length() > 2 & nome.equals(bnome)){
                        livros.add(b);
                        continue;
                    }
                }
            }
        }   
        if(!livros.isEmpty()){
            return Optional.of(livros);
        }else{
            return Optional.empty();
        }
    }
    
    
    
}

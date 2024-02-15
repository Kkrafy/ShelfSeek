/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.biblioteca.data;

import com.biblioteca.data.repository.LivrosRepository;
import com.biblioteca.data.entities.Autor;
import com.biblioteca.data.entities.Book;
import com.biblioteca.data.repository.AutorRepository;
import com.biblioteca.pojo.SearchResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author kkraft
 * to aqui javadoc
 */
public class BookSearchEngine {
     LivrosRepository arquivoRepository;
     AutorRepository autorRepository;
    
    String nomedolivroraw;
    String[] nomedolivroseparado;
    public BookSearchEngine(LivrosRepository arquivoRepository, AutorRepository autorRepository){
        this.arquivoRepository = arquivoRepository;
        this.autorRepository = autorRepository;
    }
    
    //TODO:Duas palavras iguais ter prioridade e organizar livros por quantidadde de espaços
    public Optional<SearchResult> tituloSearch(String nomedolivro){
        
        SearchResult sr = new SearchResult();
        
        this.nomedolivroraw = nomedolivro;
        this.nomedolivroseparado = nomedolivroraw.split(" ");    
        
        Iterable<Book> allbooks = arquivoRepository.findAll();
        Iterable<Autor> todosautores = autorRepository.findAll();
        for(Book b:allbooks){
            if(b.getTitulo().equals(nomedolivroraw)){
                sr.classificar(b, 5);
                continue;
            }
            String bnomeseparado[] = b.getTitulo().split(" ");
            byte palavrasiguais = 0;
            for(String bnome:bnomeseparado){
                for(String nome:nomedolivroseparado){
                    if (nome.length() > 2 & bnome.length() > 2 & nome.equals(bnome)){
                        palavrasiguais++;
                        continue;
                    }
                }
            }
            if(palavrasiguais == 1){
                sr.classificar(b,2);
            }else if(palavrasiguais >= 2){
                sr.classificar(b,3);
            }
        }
        
        
        for(Autor v_autor:todosautores){
            
            String[] autornomeseparado = v_autor.getNome().split(",");
            String[] sobrenomeseparado = autornomeseparado[0].split(" ");  
            String nome_usual = autornomeseparado[1] + " " + autornomeseparado[0];
            
            if(nome_usual.equals(nomedolivroraw)){
                sr.classificar(v_autor, 5);
                continue;
            }
            
            
            boolean primeiro_nome_contido = false;
            boolean sobrenome_contido= false;
            boolean partesobrenome_contido = false;
            byte loop = 0;
            for(String anome:autornomeseparado){ //anome significa autornome
                loop++;
                for(String nome:nomedolivroseparado){
                    if (nome.length() > 2 & anome.length() > 2 & nome.equals(anome)){
                        if(loop==1){sobrenome_contido=true; continue;}                        
                        if(loop==2){primeiro_nome_contido=true;}
                    }
                    
                    if(loop == 1 & sobrenomeseparado.length > 1){
                        for(String vsbs:sobrenomeseparado){
                            if(vsbs.length() > 2 & vsbs.equals(nome)){
                                partesobrenome_contido = true;
                            }
                        }
                    }
                }
            }
            if(sobrenome_contido & primeiro_nome_contido){
                 sr.classificar(v_autor, 4);
            }else if(sobrenome_contido){
                sr.classificar(v_autor, 3);
            }else if(partesobrenome_contido || primeiro_nome_contido){
                sr.classificar(v_autor, 2);                
            }
        }
        
        if(sr.livro_registrado || sr.autor_registrado){
            return Optional.of(sr);
        }else{
            return Optional.empty();
        }
    }
    
    public List<Book> autorSearch(String autor){
        List<Book> listafinal = new ArrayList<Book>();
        for(Book b:arquivoRepository.findByAutor(autor)){
            listafinal.add(b);
        }
        return listafinal;
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.biblioteca.pojo;

import com.biblioteca.data.entities.Book;
import java.util.List;

/**
 *
 * @author Mateus Rocha(Kkrafy)
 */
public class JsonTools {
       public static String bookListToJson(List<Book> lista){
        String jsonfinal = "{\"livros\":[";
        int currentindex = -1;
        for(Book b:lista){
            jsonfinal += "{\"isbn\":\""+b.getIsbn()+"\",\"nome\":\""+ b.getTitulo() + "\", \"sinopse\":\"" + b.getSinopse() + "\",\"autor\":\"" + b.getAutor_nome()+"\",\"autorid\":\""+ b.getAutor() + "\"}";
            currentindex+= 1;
            if(currentindex != lista.size() - 1){
                jsonfinal += ",";
            }
        }
        jsonfinal += "]}";
        return jsonfinal;
    }
}

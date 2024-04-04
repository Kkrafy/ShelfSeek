/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.shelfseek.model.businesslayer;

import com.shelfseek.model.dataacesslayer.entities.Book;
import java.text.Normalizer;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Mateus Rocha(Kkrafy)
 */
public class StaticTools {
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
    
    /**
     * A função funciona separando diacritics pela normalizer e tirando eles do prompt pela pattern
     * @param src String para tirar os diacritics
     * @param tirar_pontuacao auto explicativo
     * @return String sem os diacritics
     */
    public static String tirarDiacritics(String src,boolean tirar_pontuacao){
        String promptraw = Normalizer.normalize(src,Normalizer.Form.NFD);
        String prompt = "";
        Matcher matcher;
        if(tirar_pontuacao){
            matcher = Pattern.compile("[^\\p{M}^\\p{P}]").matcher(promptraw);
        }else{
            matcher = Pattern.compile("[^\\p{M}]").matcher(promptraw);
        }    
        while(matcher.find()){prompt += matcher.group();}
        return prompt;
    }   
}

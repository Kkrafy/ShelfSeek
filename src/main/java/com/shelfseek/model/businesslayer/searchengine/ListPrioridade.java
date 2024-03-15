/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.shelfseek.model.businesslayer.searchengine;

import com.shelfseek.model.businesslayer.BookOuAutor;
import java.util.ArrayList;


/**
 *
 * @author Mateus Rocha(Kkrafy)
 */
class ListPrioridade<T> extends ArrayList<T> {
    final protected Integer prioridade;
    final protected BookOuAutor bookouautor;
    ListPrioridade(Integer prioridade,BookOuAutor e){
        this.prioridade = prioridade;
        this.bookouautor = e;        
    }
    
}

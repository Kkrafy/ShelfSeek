/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.shelfseek.model.businesslayer.searchengine;

import java.util.ArrayList;


/**
 *
 * @author Mateus Rocha(Kkrafy)
 */
class ListPrioridade<T> extends ArrayList<T> {
    final protected Float prioridade;
    final protected BookOuAutor bookouautor;
    private BookOuSinopse bookousinose;
    
    ListPrioridade(Float prioridade,BookOuAutor e){
        this.prioridade = prioridade;
        this.bookouautor = e;        
    }
    ListPrioridade(Float prioridade,BookOuAutor bookouautor,BookOuSinopse bookousinopse){
        this.prioridade = prioridade;
        this.bookouautor = bookouautor;        
        this.bookousinose = bookousinopse;
    }    
    
}

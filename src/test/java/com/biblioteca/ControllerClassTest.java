/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.biblioteca;

import com.biblioteca.data.entities.Book;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author kkraft
 */
public class ControllerClassTest {
    
    ControllerClass controllerclass = new ControllerClass();
    
    @BeforeEach()
    public void beforeEach(){
        controllerclass = new ControllerClass();
    }
    
   /* @Test
    public void buscaAcervoTest(){
        HttpServletRequest requestmock = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse responsemock = Mockito.mock(HttpServletResponse.class);
        PrintWriter writermock = Mockito.mock(PrintWriter.class);
        Mockito.when(requestmock.getParameter("nomedolivro")).thenReturn("O Pequeno Principe");
        controllerclass.buscaAcervo(requestmock, responsemock);
    }*/
    
   // @Test
    public void jsonGeneratorTest(){
        List<Book> lista = new ArrayList<Book>();
        Book livroteste1 = new Book();
        Book livroteste2 = new Book();
        livroteste1.setNome("O pequeno teste");
        livroteste1.setSinopse("batata é bão");
        livroteste2.setNome("eu sou o teste");
        livroteste2.setSinopse("voce não é o teste");     
        lista.add(livroteste1);
        lista.add(livroteste2);
        controllerclass.jsonGenerator(lista);
    }
}

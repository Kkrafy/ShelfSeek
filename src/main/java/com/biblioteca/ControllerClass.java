
package com.biblioteca;

import com.biblioteca.data.ArquivoRepository;
import com.biblioteca.data.BookSearchEngine;
import com.biblioteca.data.entities.Book;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import java.util.Optional;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
/**
 *
 * @author kkraft
 * Serve como controller do projeto
 */

@EnableAutoConfiguration
@Controller
@ComponentScan(basePackages = "com.biblioteca.data")
@EntityScan(basePackages = "com.biblioteca.data.entities")

public class ControllerClass {
    Logger logger;
    @Autowired
    public ArquivoRepository arquivoRepository;
    
    public static void main(String[] args){
        SpringApplication.run(ControllerClass.class);
    }
    
    public ControllerClass(){
        logger = LogManager.getLogger();
    }
    
    /**
     * 
     * @return 
     */
    @GetMapping("/busca-acervo")
    public void buscaAcervo(HttpServletRequest request, HttpServletResponse response){
        String nomedolivro = request.getParameter("nomedolivro");
        BookSearchEngine booksearchengine = new BookSearchEngine(nomedolivro,arquivoRepository);
        Optional<Book> optionalbook = booksearchengine.Search();
        if (optionalbook.isPresent()){
            Book book = optionalbook.get();
            logger.info("Optionalbook is present");
            logger.debug("{\"nome\":\""+ book.getNome() +"\",\"sinopse\":\"" + book.getSinopse() + "\"}");
            try{
            response.getWriter().write("{\"nome\":\""+ book.getNome() +"\",\"sinopse\":\"" + book.getSinopse() + "\"}");
            }catch(Exception e){
                
            }
        }
        else{
        logger.info("Optionalbook is NOT present");
        }
    }
}

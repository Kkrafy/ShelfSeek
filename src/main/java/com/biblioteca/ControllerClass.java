
package com.biblioteca;

import com.biblioteca.data.ArquivoRepository;
import com.biblioteca.data.BookSearchEngine;
import com.biblioteca.data.CoverService;
import com.biblioteca.data.entities.Book;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
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
    
    public String jsonGenerator(List<Book> lista){
        String jsonfinal = "{\"livros\":[";
        int currentindex = -1;
        for(Book b:lista){
            jsonfinal += "{\"isbn\":\""+b.getIsbn()+"\",\"nome\":\""+ b.getTitulo() + "\", \"sinopse\":\"" + b.getSinopse() + "\"}";
            currentindex+= 1;
            if(currentindex != lista.size() - 1){
                jsonfinal += ",";
            }
        }
        jsonfinal += "]}";
        logger.info(jsonfinal);
        return jsonfinal;
    }
    /**
     * Recebe uma solicitação ajax e instancia a
     * 
     */
    @GetMapping("/busca-acervo")
    public void buscaAcervo(HttpServletRequest request, HttpServletResponse response){
        String nomedolivro = request.getParameter("nomedolivro");
        BookSearchEngine booksearchengine = new BookSearchEngine(nomedolivro,arquivoRepository);
        Optional<List<Book>> optionalbook = booksearchengine.Search();
        if (optionalbook.isPresent()){
            List<Book> booklista = optionalbook.get();
            logger.info("Optionalbook/s is/are present");
           // logger.debug("{\"nome\":\""+ book.getNome() +"\",\"sinopse\":\"" + book.getSinopse() + "\"}");
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");  
            String json = jsonGenerator(booklista);
            try{          
            response.getWriter().write(json);
            }catch(Exception e){
                
            }
        }
        else{
        logger.info("Optionalbook is NOT present");
        }
    }
    
    @GetMapping("/findcover")
    public void findCoverByISBN(HttpServletRequest request,HttpServletResponse response){
        CoverService coverservice = new CoverService();
        String requested_isbn = request.getParameter("isbn");
        byte[] cover = coverservice.findCover(requested_isbn);
        logger.debug(requested_isbn);
       /* for(byte b:cover){
            System.out.print(b);    
            System.out.print(" ");
        }*/
        response.setContentType("image/jpg");
        OutputStream stream;
        try{
            stream = response.getOutputStream();
            stream.write(cover);
        }
        catch(IOException e){logger.error("IOException no output stream de achar capa");}
        
    }
    
}

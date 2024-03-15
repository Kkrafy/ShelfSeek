
package com.shelfseek;

import com.shelfseek.model.dataacesslayer.repository.LivrosRepository;
import com.shelfseek.model.dataacesslayer.repository.AutorRepository;
import com.shelfseek.model.businesslayer.searchengine.BookSearchEngine;
import com.shelfseek.model.businesslayer.CoverService;
import com.shelfseek.model.dataacesslayer.entities.Book;
import com.shelfseek.model.businesslayer.JsonTools;
import com.shelfseek.model.businesslayer.searchengine.SearchResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import java.util.Optional;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
/**
 *
 * @author Mateus Rocha(Kkrafy)
 * Serve como controller do projeto
 */

@EnableAutoConfiguration
@Controller
@ComponentScan(basePackages = "com.shelfseek.model")
@EntityScan(basePackages = "com.shelfseek.model.dataacesslayer.entities")

public class ControllerClass {
    Logger logger;
    @Autowired
    //@Qualifier("LivrosRepository")
    public LivrosRepository arquivoRepository;
    
    @Autowired
    public AutorRepository autorRepository;
    
    public static void main(String[] args){
        SpringApplication.run(ControllerClass.class);
    }
    
    public ControllerClass(){
        logger = LogManager.getLogger();
    }
    
 
    /**
     * Recebe uma solicitação ajax e instancia a
     * 
     */
    @GetMapping("/busca-acervo")
    public void buscaAcervo(HttpServletRequest request, HttpServletResponse response){
        String nomedolivro = request.getParameter("nomedolivro");
        BookSearchEngine booksearchengine = new BookSearchEngine(arquivoRepository,autorRepository);
        Optional<SearchResult> optionalsr = booksearchengine.tituloSearch(nomedolivro);
        if (optionalsr.isPresent()){
            SearchResult sr = optionalsr.get();
            logger.info("SearchResult is present");
           // logger.debug("{\"nome\":\""+ book.getNome() +"\",\"sinopse\":\"" + book.getSinopse() + "\"}");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");  
            String json = sr.getJSON();
            try{          
            response.getWriter().write(json);
            }catch(Exception e){
                
            }
        }
        else{
        logger.info("SearchResult is NOT present");
        }
    }
    
    @GetMapping("/findcover")
    public void findCoverByISBN(HttpServletRequest request,HttpServletResponse response){
        CoverService coverservice = new CoverService();
        String requested_isbn = request.getParameter("isbn");
        byte[] cover = coverservice.findCover(requested_isbn);
        //logger.debug(requested_isbn);
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
    
    @GetMapping("/autor")
    public void autorPage(HttpServletRequest request,HttpServletResponse response){
        String autorid = request.getParameter("autor");
        String autor = autorRepository.getNomeAutorById(autorid);
        System.out.println("autor = " + autorid);
        BookSearchEngine b_engine = new BookSearchEngine(arquivoRepository,autorRepository);
        List<Book> lista = b_engine.autorSearch(autorid);
        String jsonfinal = JsonTools.bookListToJson(lista);
        try {
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("<!DOCTYPE html>\n" +
"<html>\n" +
"    <head>\n" +
"        <title>Livros de \""+autor+"</title>\n" +
"        <meta charset=\"utf-8\">\n" +
"        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
"        <link href=\"indexstyle.css\" rel=\"stylesheet\"/>\n" +
"    </head>\n" +
"    <body>\n" +
"        \n" +
"         <a href=\"/\" style=\"font-size: xx-large;color: darkblue;\">ShelfSeek</a>\n" +
"        <h1 style=\"position:relative;font-family:monospace;font-weight:lighter;font-size: xxx-large;\">Livros de "+autor+":</h1>\n" +
"         <div class = \"acervodiv\" id=\"bookdiv\">  <!-- Clonada para cada livro encontrado --> \n" +
"            <image id=\"bookimage\">\n" +
"        </div>           \n" +
"        <script>\n" +
"            \n" +
"        //Adapted by Kkraft(from my own script .-)\n" +
"\n" +
"\n" +
        "function inicioredirect(){"
        + "window.location.assign(\"/\")"
        + "}"
        + "var bookjson ="+jsonfinal+"\n" +
"    \n" +
"\n" +
"        bookjson.livros.forEach(addBooks)\n" +
"\n" +
"\n" +
"        function addBooks(bookjson){\n" +
"            const div = document.getElementById(\"bookdiv\").cloneNode(true)\n" +
"            const nomeparagrafo = document.createElement(\"p\")   \n" +
"            const sinopseparagrafo = document.createElement(\"p\")\n" +
"            const autorlink = document.createElement(\"a\")    \n" +
"            nomeparagrafo.style.display = \"inline-block\"\n" +
"            nomeparagrafo.style.position = \"absolute\"   \n" +
"            nomeparagrafo.style.color = \"white\"\n" +
"            sinopseparagrafo.style.display = \"inline-block\"\n" +
"            sinopseparagrafo.style.position = \"absolute\"      \n" +
"            sinopseparagrafo.style.color = \"white\"\n" +
"            sinopseparagrafo.style.top = \"30px\";\n" +
"            sinopseparagrafo.style.width = \"350px\";   \n" +
"            autorlink.style.position = \"relative\"\n" +
"            autorlink.style.bottom = \"4px\"\n" +
"            nomeparagrafo.innerHTML = bookjson.nome\n" +
"            sinopseparagrafo.innerHTML = bookjson.sinopse\n" +
"            autorlink.innerHTML = bookjson.autor\n" +
"            autorlink.setAttribute(\"href\",\"/autor?autor=\" + bookjson.autorid)\n" +
"            div.appendChild(nomeparagrafo)\n" +
"            div.appendChild(sinopseparagrafo)\n" +
"            div.appendChild(autorlink)\n" +
"            div.style.display = \"inline-block\"\n" +
"            div.querySelector(\"#bookimage\").setAttribute(\"src\",\"/findcover?isbn=\" + bookjson.isbn)\n" +
"            div.querySelector(\"#bookimage\").style.display = \"inline-block\";\n" +
"            document.body.appendChild(div)\n" +
"            console.log(bookjson.nome)\n" +
"            console.log(bookjson.sinopse)\n" +
"            } \n" +
"        </script>\n" +
"    </body>\n" +
"</html>\n" +
"");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}

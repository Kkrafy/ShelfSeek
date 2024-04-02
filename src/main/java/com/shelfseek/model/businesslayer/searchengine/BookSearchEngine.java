/*

 */
package com.shelfseek.model.businesslayer.searchengine;

import com.shelfseek.model.businesslayer.searchengine.SearchResult;
import com.shelfseek.model.dataacesslayer.entities.Autor;
import com.shelfseek.model.dataacesslayer.entities.Book;
import com.shelfseek.model.dataacesslayer.repository.AutorRepository;
import com.shelfseek.model.dataacesslayer.repository.LivrosRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Mateus Rocha(Kkrafy)
 * to aqui javadoc
 */
public class BookSearchEngine {
    LivrosRepository arquivoRepository;
    AutorRepository autorRepository;
    SearchResult sr = new SearchResult();
    String prompt;
    String promptraw;
    String[] promptseparado;
    String[] promptrawseparado;
    public BookSearchEngine(LivrosRepository arquivoRepository, AutorRepository autorRepository){
        this.arquivoRepository = arquivoRepository;
        this.autorRepository = autorRepository;
    }
    public Optional<SearchResult> acervoSearch(String prompt){
        this.prompt = prompt;
        this.promptraw = prompt.toUpperCase();
        this.promptseparado = prompt.split(" ");  
        this.promptrawseparado = promptraw.split(" "); 
        autorSearch();        
        bookSearch();
        if(sr.livro_registrado || sr.autor_registrado){
            return Optional.of(sr);
        }else{
            return Optional.empty();
        }        
    }    
    
    
    void autorSearch(){
        Iterable<Autor> todosautores = autorRepository.findAll();
        
        for(Autor v_autor:todosautores){            
            String[] autornomeseparado = v_autor.getNome().split(",");
            String[] sobrenomeseparado = autornomeseparado[0].split(" ");  
            String nome_usual = autornomeseparado[1] + " " + autornomeseparado[0];
            
            if(nome_usual.equals(prompt)){
                v_autor.setNome_bolded("||bold||" + v_autor.getNome() + "||bold||");
                sr.classificar(v_autor, 5);
                continue;
            }
            
            
            boolean primeiro_nome_contido = false;
            boolean sobrenome_contido= false;
            boolean partesobrenome_contido = false;
            byte loop = 0;
            for(String anome:autornomeseparado){ //anome significa autornome
                loop++;
                for(String nome:promptseparado){
                    if (nome.length() > 2 & anome.length() > 2 & nome.equals(anome)){
                        if(loop==1){sobrenome_contido=true; continue;}                        
                        if(loop==2){primeiro_nome_contido=true;}
                    }
                    
                    if(loop == 1 & sobrenomeseparado.length > 1){
                        byte vsbsloop = -1;
                        for(String vsbs:sobrenomeseparado){
                            vsbsloop++;
                            if(vsbs.length() > 1 & vsbs.equals(nome)){
                                sobrenomeseparado[vsbsloop] = "||bold||" + sobrenomeseparado[vsbsloop] + "||bold||";
                                partesobrenome_contido = true;
                            }
                        }
                    }
                }
            }
            String autornomepartesobrenomebolded = "";
            if(sobrenome_contido & primeiro_nome_contido){
                 v_autor.setNome_bolded("||bold||"+v_autor.getNome()+"||bold||");                
                 sr.classificar(v_autor, 4);
            }else if(sobrenome_contido){
                v_autor.setNome_bolded("||bold||" + autornomeseparado[0] + "||bold||," + autornomeseparado[1]);                
                sr.classificar(v_autor, 3);
            }else if(primeiro_nome_contido){
                System.out.println("primeironomecontido");
                v_autor.setNome_bolded(autornomeseparado[0] + ",||bold||" + autornomeseparado[1] + "||bold||" );
                sr.classificar(v_autor, 2);                   
            }else if(partesobrenome_contido){
                boolean debounce = true;
                for(String s:sobrenomeseparado){
                    if(!debounce){
                        autornomepartesobrenomebolded += " " + s;
                    }else{
                        autornomepartesobrenomebolded = s;
                        debounce = false;
                    }
                }
                autornomepartesobrenomebolded += "," + autornomeseparado[1];
                v_autor.setNome_bolded(autornomepartesobrenomebolded);
                sr.classificar(v_autor, 2);                   
            }
        }
    }
    //TODO:organizar livros por quantidadde de espaços
    void bookSearch(){
        Iterable<Book> allbooks = arquivoRepository.findAll();
        for(Book b:allbooks){
            int prioridade;
            int prioridadesinopse;
            prioridade = tituloIteration(b);
            prioridadesinopse = sinopseIteration(b);
            if(prioridade != 0 || prioridadesinopse != 0){sr.classificar(b, prioridade,prioridadesinopse);System.out.println(b.getSinopse());}
        }    
    }
    /**
     * 
     * @param b livro da atual iteração da bookSearch
     * @return prioridade dada ao livro dada a search de titulo
     */
    int tituloIteration(Book b){
            //System.out.println(b.getTitulo());
            String bnomefinal = "";
                             
            if(b.getTitulo().equals(prompt)){
                b.setAutor_nome(autorRepository.getNomeAutorById(b.getAutor()));                
                b.titulo_bolded = "||bold||" + b.getTitulo() + "||bold||";
                return 5;
            }
            String bnomeseparado[] = b.getTitulo().split(" ");
            byte palavrasiguais = 0;
            byte currentloop = 0;
            for(String bnome:bnomeseparado){
                for(String nome:promptseparado){
                    if (nome.length() > 2 & bnome.length() > 2 & nome.equals(bnome)){
                        bnomeseparado[currentloop] = "||bold||" +bnome + "||bold||"; // ||bold|| é interpretado pelo indexapimanager.js como <strong>
                        palavrasiguais++;
                        continue;
                    }
                }
                currentloop++;
            }
            
            boolean debounce = true;
            for(String s:bnomeseparado){
                if(!debounce){
                    bnomefinal += " " + s;
                }else{
                    bnomefinal = s;
                    debounce = false;
                }
            }
            b.titulo_bolded = bnomefinal;
            
            if(palavrasiguais == 1){
                System.out.println(b.titulo_bolded);
                b.setAutor_nome(autorRepository.getNomeAutorById(b.getAutor()));
                return 2;
            }else if(palavrasiguais >= 2){
                System.out.println(b.titulo_bolded);    
                b.setAutor_nome(autorRepository.getNomeAutorById(b.getAutor()));                
                return 3;
            }else{return 0;}
    }        
    
    
    /**
     * 
     * @param b livro da atual iteração da bookSearch
     * @return prioridade dada ao livro dada a search de sinopse
     */
    int sinopseIteration(Book b){
        String sinopseraw = b.getSinopse();
        String sinopse = sinopseraw.toUpperCase();
        String[] sinopseseparada = sinopse.split(" ");
        String[] sinopserawseparada = sinopseraw.split(" ");
        boolean ultimapalavra = false;
        int prioridadetoreturn = 0;
        if(sinopse.equals(promptraw)){
            return 6;
        }
        
        int currentpalavrasinopse = 0;
        int currentpalavraprompt = 0;
        for(String strs:sinopseseparada){            
            boolean equals = false;            
            while(currentpalavraprompt != promptrawseparado.length){
                if(promptrawseparado[currentpalavraprompt].equals(strs) & strs.length() > 2){
                    equals = true; 
                    break;
                }else if(ultimapalavra){
                    break;
                }
                currentpalavraprompt++;
            }
            if(equals){
                currentpalavraprompt++;
                sinopserawseparada[currentpalavrasinopse] = "||bold||" + sinopserawseparada[currentpalavrasinopse] + "||bold||";
                System.out.println(sinopserawseparada[currentpalavrasinopse]);
                if(ultimapalavra){
                    System.out.println("ultimapalavra true");
                    prioridadetoreturn += 2;
                }else{
                    prioridadetoreturn++;
                }
                ultimapalavra = true;
                equals = false;                
            }else{currentpalavraprompt = 0; ultimapalavra = false;}            
            currentpalavrasinopse++;
        }
        if(prioridadetoreturn != 0){
            int currentsinopseloopdebaixo = 1;
            sinopseraw = "";
            for(String s:sinopserawseparada){
                //System.out.println(s);
                sinopseraw += s;            
                if(sinopserawseparada.length != currentsinopseloopdebaixo){sinopseraw+=" ";}
                currentsinopseloopdebaixo++;
            }
            b.setSinopse(sinopseraw);
            //System.out.println(b.getSinopse());
            //System.out.println(prioridadetoreturn);
        }
        return prioridadetoreturn;
        
    }
    public List<Book> autorSearch(String autor){
        List<Book> listafinal = new ArrayList<Book>();
        for(Book b:arquivoRepository.findByAutor(autor)){
            b.setAutor_nome(autorRepository.getNomeAutorById(autor));
            listafinal.add(b);
        }
        return listafinal;
    }
    
}

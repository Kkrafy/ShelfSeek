/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.biblioteca.pojo;

import com.biblioteca.data.entities.Autor;
import com.biblioteca.data.entities.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Mateus Rocha(Kkrafy)
 */

//TODO:Fazer livros com autor que tem nome na pesquisa terem prioridade
//TODO mudar encoding do database
public class SearchResult {
    
    public boolean autor_registrado = false;
    public boolean livro_registrado = false;
    
    List<Book> livros1p = new ArrayList<Book>();
    List<Book> livros2p = new ArrayList<Book>();
    List<Book> livros3p = new ArrayList<Book>();
    List<Book> livros4p = new ArrayList<Book>();
    List<Book> livros5p = new ArrayList<Book>();
    List<Book> livrosespecial1 = new ArrayList<Book>(); 
    List<Book> livrosespecial2 = new ArrayList<Book>();
    List<Book> livrosespecial3 = new ArrayList<Book>();           
    List<Autor> autores1p = new ArrayList<Autor>();
    List<Autor> autores2p = new ArrayList<Autor>();
    List<Autor> autores3p = new ArrayList<Autor>();
    List<Autor> autores4p = new ArrayList<Autor>();
    List<Autor> autores5p = new ArrayList<Autor>();
    
    
    
    
    String jsonfinal = "{\"livros_e_autores\":[";
    
   public void addLivroToJSON(Book b){
        jsonfinal += "{\"livro_ou_autor\":\"livro\",\"isbn\":\""+b.getIsbn()+"\",\"nome\":\""+ b.titulo_bolded + "\", \"sinopse\":\"" + b.getSinopse() + "\",\"autor\":\"" + b.getAutor_nome()+ "\",\"autorid\":\""+b.getAutor()+"\"}";
   }    
   public void addAutorToJSON(Autor a){
        jsonfinal += "{\"livro_ou_autor\":\"autor\",\"nome\":\""+ a.getNome_bolded()+ "\",\"id\":\"" + a.getId() + "\"}";
    }  
    
   byte nao_vazios = 0;   
   void isEmpty(List lista){
       if(!lista.isEmpty()){
           nao_vazios++;
       }
   }
   //tolerança de um já que a lista que chamou o metodo ainda nao pode estar vazia 
   boolean isAllEmpty(){
       
       isEmpty(livros1p);
       isEmpty(livros2p);
       isEmpty(livros3p);
       isEmpty(livros4p);
       isEmpty(livros5p);
       isEmpty(livrosespecial1);
       isEmpty(livrosespecial2);
       isEmpty(livrosespecial3);       
       isEmpty(autores5p);
       isEmpty(autores4p);   
       isEmpty(autores3p);   
       isEmpty(autores2p);   
       
       if(nao_vazios == 1){
           nao_vazios = 0;
           return true;
       }
       nao_vazios = 0;   
       return false;
   }
   
   int currentloop = 0;
   void virgulaPlacer(List lista_posterior,List lista){
        boolean ultimoloop = false;
        currentloop++;      
        if(lista.size() == currentloop){
            ultimoloop = true;
        }
       
        if(isAllEmpty()){
            System.out.println("Allempty");
            if(!ultimoloop){jsonfinal += ",";} 
            else{jsonfinal += "]}";}
        }else{
            System.out.println("NotAllempty");            
            jsonfinal += ",";
            if(ultimoloop){currentloop = 0;}
         }
   }
   
   List<Autor> autores = new ArrayList<Autor>();
   Optional<Autor> findAutor(String id){
       for(Autor a:autores){
           if(a.getId().equals(id)){
               return Optional.of(a);
           }
       }
       return Optional.empty();
   }
   
   int getPrioridadeComposta(Book b, int prioridade){
       Optional<Autor> optautordob = findAutor(b.getAutor());
       if(!optautordob.isEmpty()){
           Autor autordob = optautordob.get();
           b.setAutor_nome(autordob.getNome_bolded());
           return prioridade + autordob.getPrioridade() - 1;
       }else{
           return prioridade;
       }
       
   }
   public void parseLists(){
       for(Book b:livrosespecial3){ addLivroToJSON(b);virgulaPlacer(livrosespecial2, livrosespecial3);}       
       livrosespecial3.clear();
       for(Book b:livrosespecial2){addLivroToJSON(b);virgulaPlacer(livrosespecial1, livrosespecial2);}      
       livrosespecial2.clear();       
       for(Book b:livrosespecial1){addLivroToJSON(b);virgulaPlacer(livros5p, livrosespecial1);}   
       livrosespecial1.clear();       
       for(Book b:livros5p){addLivroToJSON(b);virgulaPlacer(autores5p, livros5p);}
       livros5p.clear();
       for(Autor a:autores5p){addAutorToJSON(a);virgulaPlacer(livros4p, autores5p);}
       autores5p.clear();
       for(Book b:livros4p){addLivroToJSON(b);virgulaPlacer(autores4p, livros4p);}
       livros4p.clear();       
       for(Autor a:autores4p){addAutorToJSON(a);virgulaPlacer(livros3p, autores4p);}
       autores4p.clear();       
       for(Book b:livros3p){addLivroToJSON(b);virgulaPlacer(autores3p, livros3p);}
       livros3p.clear();       
       for(Autor a:autores3p){addAutorToJSON(a);virgulaPlacer(livros2p, autores3p);}   
       autores3p.clear();       
       for(Book b:livros2p){addLivroToJSON(b); virgulaPlacer(autores2p, livros2p);}
       livros2p.clear();       
       for(Autor a:autores2p){addAutorToJSON(a);virgulaPlacer(livros1p, autores2p);}
       autores2p.clear();
       for(Book b:livros1p){addLivroToJSON(b);virgulaPlacer(autores1p, livros1p);}
       livros1p.clear();
       
       int currentloop = 0;
       for(Autor a:autores1p){
           currentloop++;
           addAutorToJSON(a);
           if(autores1p.size() != currentloop){jsonfinal += ",";}
           else{jsonfinal += "]}";}
       }   
       
   }
    
    
    public String getJSON(){
        parseLists();
        System.out.println(jsonfinal);
        return jsonfinal ;
    }
    
    public void classificar(Book p,int prioridade){
        livro_registrado = true;
        int prioridadefinal = getPrioridadeComposta(p,prioridade);
        
        switch(prioridadefinal){
            case(1):
                System.out.println("livrop1");            
                livros1p.add(p);  
                break;
            case(2):
                System.out.println("livrop2");
                livros2p.add(p);   
                break;
            case(3):
                System.out.println("livrop3");
                livros3p.add(p);    
                break;
            case(4):
                System.out.println("livrop4");
                livros4p.add(p);    
                break;
            case(5):
                System.out.println("livrop5");
                livros5p.add(p);
                break;
            case(6):
                System.out.println("livroespecial1");
                livrosespecial1.add(p);  
                break;
            case(7):
                System.out.println("livroespecial2");
                livrosespecial2.add(p);       
                break;
            case(8):
                System.out.println("livroespecial3");
                livrosespecial3.add(p);       
                break;
        }
    }
    public void classificar(Autor p,int prioridade){
        autor_registrado = true;
        
        p.setPrioridade(prioridade);
        autores.add(p);
        
        if(prioridade == 1){
            System.out.println("autorp1");
            autores1p.add(p);
        }else if(prioridade == 2){
            System.out.println("autorp2");            
            autores2p.add(p);       
        }else if(prioridade == 3){
            System.out.println("autorp3");            
            autores3p.add(p);        
        }else if(prioridade == 4){
            System.out.println("autorp4");            
            autores4p.add(p);         
        }else if(prioridade == 5){
            System.out.println("autorp5");            
            autores5p.add(p);                     
        }
    }    
}


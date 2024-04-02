/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.shelfseek.model.businesslayer.searchengine;

import com.shelfseek.model.dataacesslayer.entities.Autor;
import com.shelfseek.model.dataacesslayer.entities.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Mateus Rocha(Kkrafy)
 */

public class SearchResult {
    List<ListPrioridade<Autor>> listas_autor = new ArrayList<>();
    List<ListPrioridade<Book>> listas_book = new ArrayList<>();
    List<ListPrioridade<Book>> listas_book_por_sinopse = new ArrayList<>();
    int autormaioratual = 0;
    float bookmaioratual = 0;
    int sinopsemaioratual = 0;
    public boolean autor_registrado = false;
    public boolean livro_registrado = false;         
    String livrosadicionadosdebug = "";
    String jsonfinal = "{\"livros_e_autores\":[";
    
    public String getJSON(){
        parseLists();
        System.out.print("Livros Achados:");
        System.out.println(livrosadicionadosdebug);
        System.out.println(jsonfinal);
        return jsonfinal ;
    }
    
    protected void classificar(Book p,int prioridade, int prioridadesinopse){
        float prioridadefinal = getPrioridadeComposta(p,prioridade);
        ListPrioridade<Book> list;
        ListPrioridade<Book> listsinopse;
        if(prioridadefinal != 0 & prioridadesinopse != 0){System.out.println(prioridadesinopse/9f); prioridadefinal += prioridadesinopse/9f;}
        if(prioridadefinal != 0){
            //System.out.println("pfinal != 0");
            //System.out.println(prioridadesinopse);
            prioridadesinopse = 0;
            list = getListBookPrioridade(prioridadefinal,listas_book);        
            list.add(p);
            livro_registrado = true;
        }else if(prioridadesinopse != 0){
            listsinopse = getListBookPrioridade((float)prioridadesinopse,listas_book_por_sinopse);
            listsinopse.add(p);
            livro_registrado = true;
        }       
        System.out.print("prioridade do " + p.getTitulo() + ":");
        System.out.println(prioridadefinal);
        if(prioridadefinal > bookmaioratual){bookmaioratual = prioridadefinal;}        
        if(prioridadesinopse > sinopsemaioratual & prioridadefinal == 0){sinopsemaioratual = prioridadesinopse;}
    }
    protected void classificar(Autor p,int prioridade){
        ListPrioridade<Autor> list = getListAutorPrioridade(prioridade);
        autor_registrado = true;        
        p.setPrioridade(prioridade);
        autores.add(p);
        list.add(p);
        if(prioridade > autormaioratual){autormaioratual = prioridade;}
    }
   
    void addLivroToJSON(Book b){
        jsonfinal += "{\"livro_ou_autor\":\"livro\",\"isbn\":\""+b.getIsbn()+"\",\"nome\":\""+ b.titulo_bolded + "\", \"sinopse\":\"" + b.getSinopse() + "\",\"autor\":\"" + b.getAutor_nome()+ "\",\"autorid\":\""+b.getAutor()+"\"}";
        livrosadicionadosdebug += " " + b.getTitulo();
    }    
   void addAutorToJSON(Autor a){
        jsonfinal += "{\"livro_ou_autor\":\"autor\",\"nome\":\""+ a.getNome_bolded()+ "\",\"id\":\"" + a.getId() + "\"}";
    }  
    
   byte nao_vazios = 0;   
   <T> void updateNaoVazios(List<ListPrioridade<T>> lista){
       for(ListPrioridade<T> l:lista){
           if(!l.isEmpty()){
               nao_vazios++;
           }
       }
   }
    Optional<ListPrioridade<Autor>> essaListAutorExiste(Integer prioridade){
           for(ListPrioridade<Autor> list:listas_autor){
               if(list.prioridade.equals(prioridade)){
                   return Optional.of(list);
               }
           }
           return Optional.empty();
    }
    Optional<ListPrioridade<Book>> essaListBookExiste(Float prioridade,List<ListPrioridade<Book>> listadelistas){
           for(ListPrioridade<Book> list:listadelistas){
               if(list.prioridade.equals(prioridade)){
                   return Optional.of(list);
               }
           }
           return Optional.empty();
   }

    ListPrioridade<Book> getListBookPrioridade(Float prioridade,List<ListPrioridade<Book>> listadelistas){
        Optional<ListPrioridade<Book>> opcional_list = essaListBookExiste(prioridade,listadelistas);
        if(opcional_list.isPresent()){
            return opcional_list.get();
        }else{
            ListPrioridade<Book> list = new ListPrioridade<Book>(prioridade,BookOuAutor.BOOK);
            listadelistas.add(list);
            return list;
        }
    }

    
    ListPrioridade<Autor> getListAutorPrioridade(Integer prioridade){
        Optional<ListPrioridade<Autor>> opcional_list = essaListAutorExiste(prioridade);
        if(opcional_list.isPresent()){
            return opcional_list.get();
        }else{
            ListPrioridade<Autor> list = new ListPrioridade<Autor>((float)prioridade,BookOuAutor.AUTOR);
            listas_autor.add(list);
            return list;
        }
    }
   //tolerança de um já que a lista que chamou o metodo ainda nao pode estar vazia 
   boolean isAllEmpty(){
       
       updateNaoVazios(listas_book);
       updateNaoVazios(listas_autor);
       updateNaoVazios(listas_book_por_sinopse);
       
       if(nao_vazios == 1){
           nao_vazios = 0;
           return true;
       }
       nao_vazios = 0;   
       return false;
   }
   
   int currentloop = 0;
   void virgulaPlacer(List lista){
        boolean ultimoloop = false;
        currentloop++;      
        if(lista.size() == currentloop){
            //System.out.print("ultimoloopvirgulaplacer");
            //System.out.println(lista.size());
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
           //System.out.println(b.getTitulo() + " é prioridade composta");
           Autor autordob = optautordob.get();
           b.setAutor_nome(autordob.getNome_bolded());
           return prioridade + autordob.getPrioridade();
       }else{
           return prioridade;
       }
       
   }
   void loopUmaPrioridadeBook(List<ListPrioridade<Book>> list,BookOuSinopse bookousinopse){
       ListPrioridade<Book> listp = list.get(0);
       for(Book b :listp){
           addLivroToJSON(b);
           virgulaPlacer(listp);
       }
       list.remove(0);       
       if(!list.isEmpty()){
           if(bookousinopse == BookOuSinopse.BOOK){bookmaioratual = list.get(0).prioridade.intValue();}
           else{sinopsemaioratual = list.get(0).prioridade.intValue();}
       }else{
           if(bookousinopse == BookOuSinopse.BOOK){bookmaioratual = 0;}
           else{sinopsemaioratual = 0;}           
       }
   }
   
   void loopUmaPrioridadeAutor(){
       ListPrioridade<Autor> list = listas_autor.get(0);
       for(Autor a:list){
           System.out.println(a.getNome());
           addAutorToJSON(a);
           virgulaPlacer(list);
       }
       listas_autor.remove(0);
       if(!listas_autor.isEmpty()){
           autormaioratual = listas_autor.get(0).prioridade.intValue();
       }else{
           autormaioratual = 0;
       }       
   }
   
   
   void sortLists(){
       ListPrioridadeComparatorDecrescente<Book> comparator_book = new ListPrioridadeComparatorDecrescente();
       ListPrioridadeComparatorDecrescente<Autor> comparator_autor = new ListPrioridadeComparatorDecrescente();
       listas_book.sort(comparator_book);
       listas_autor.sort(comparator_autor);
   }
   void parseLists(){
       sortLists();
       //System.out.println(autormaioratual);
       //System.out.println(bookmaioratual);       
       while(bookmaioratual != 0 || autormaioratual != 0 || sinopsemaioratual != 0){
           if(autormaioratual > bookmaioratual){
               loopUmaPrioridadeAutor();       
           }else if(bookmaioratual > autormaioratual){
               loopUmaPrioridadeBook(listas_book,BookOuSinopse.BOOK);                             
           }else if(bookmaioratual != 0 || autormaioratual != 0){
               loopUmaPrioridadeBook(listas_book,BookOuSinopse.BOOK);
               loopUmaPrioridadeAutor();
           }else{
               System.out.print("else triggered,sinopsemaioratual:");
               System.out.println(sinopsemaioratual);
               loopUmaPrioridadeBook(listas_book_por_sinopse, BookOuSinopse.SINOPSE);
           }
       }         
   }    
}


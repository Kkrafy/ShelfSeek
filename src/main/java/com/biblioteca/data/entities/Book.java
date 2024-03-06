
package com.biblioteca.data.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

/**
 *
 * @author Mateus Rocha(Kkrafy)
 */
@Entity
public class Book {
    @Id
    private Long isbn;
    
    String titulo;
    String sinopse;
    String autor;      
    
    @Transient
    public String titulo_bolded;
    
    @Transient
    public String autor_nome;    
    
    public String getAutor_nome() {
        return autor_nome;
    }

    public void setAutor_nome(String autor_nome) {
        this.autor_nome = autor_nome;
    }  
    

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setNome(String titulo) {
        this.titulo = titulo;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }
    public String getIsbn(){
        return isbn.toString() ;
    }
}

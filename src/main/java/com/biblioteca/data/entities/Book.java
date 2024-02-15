
package com.biblioteca.data.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

/**
 *
 * @author kkraft
 */
@Entity
public class Book {
    @Id
    private Long isbn;
    
    String titulo;
    String sinopse;
    
    //@Transient
    //public byte prioridade;

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }
    String autor;

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

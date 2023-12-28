
package com.biblioteca.data.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

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

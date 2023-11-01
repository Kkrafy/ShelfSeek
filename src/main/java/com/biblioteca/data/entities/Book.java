
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
    private int id;
    
    String nome;
    String sinopse;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }
}

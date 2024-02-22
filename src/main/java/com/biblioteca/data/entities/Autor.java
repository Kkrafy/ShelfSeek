/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.biblioteca.data.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

/**
 *
 * @author Mateus Rocha(Kkrafy)
 */
@Entity
public class Autor {
    @Id
    private int id;
    
    private String nome;    
    
    @Transient
    private String nome_bolded;

    public String getNome_bolded() {
        return nome_bolded;
    }

    public void setNome_bolded(String nome_bolded) {
        this.nome_bolded = nome_bolded;
    }

    public int getId() {
        return id;
    }
    
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}

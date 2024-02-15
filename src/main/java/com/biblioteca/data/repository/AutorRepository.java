/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.biblioteca.data.repository;

import com.biblioteca.data.entities.Autor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author kkraft
 */
@Repository
public interface AutorRepository extends CrudRepository<Autor,Integer> {
    //@Query(value="SELECT * FROM Autor",nativeQuery = true)
    //public Iterable<Autor> findTodosAutores();
    
    @Query(value="SELECT nome FROM Autor WHERE id = :#{#id}")
    public String getNomeAutorById(@Param("id") String id);    
}

/*
 */
package com.biblioteca.data.repository;

import com.biblioteca.data.entities.*;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author kkraft
 */
@Repository
public interface LivrosRepository extends CrudRepository<Book,Integer > {
    @Query(value="SELECT * FROM Book WHERE autor = :#{#autor}",nativeQuery=true)
    public Iterable<Book> findByAutor(@Param("autor") String autor);
   
}

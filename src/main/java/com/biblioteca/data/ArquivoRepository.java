/*
 */
package com.biblioteca.data;

import com.biblioteca.data.entities.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author kkraft
 */
@Repository
public interface ArquivoRepository extends CrudRepository<Book,Integer > {
    
}

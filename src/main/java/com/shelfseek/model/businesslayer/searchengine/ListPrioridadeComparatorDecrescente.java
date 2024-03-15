package com.shelfseek.model.businesslayer.searchengine;

import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Mateus Rocha(Kkrafy)
 */
class ListPrioridadeComparatorDecrescente <T> implements Comparator<ListPrioridade<T>> {

    @Override
    public int compare(ListPrioridade<T> o1,ListPrioridade<T> o2) {
        if(o2.prioridade > o1.prioridade){
            return 1;
        }
        return -1;
    }
    
}

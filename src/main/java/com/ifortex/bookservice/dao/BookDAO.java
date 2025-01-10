package com.ifortex.bookservice.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@Transactional
@Component
public class BookDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public Map<String, Long> getGenresAndCount()
    {
        //using NativeQuery bc field Set<String> genres doesn't have @ElementCollection
        //so that is the best way to work with this column
        List<Object[]> results = entityManager.createNativeQuery("SELECT unnest(genre) AS genr, COUNT(*) AS count " +
                "FROM books GROUP BY genr ORDER BY count DESC").
                getResultList();

        //convert result to LinkedHashMap (for saving sort)
        //return the count of the books by each genre, ordered from the genre with the most
        //books to the least
        return results.stream()
                .collect(Collectors.toMap(
                        row -> (String) row[0],
                        row -> ((Number) row[1]).longValue(),
                        (oldValue,newValue) -> oldValue,
                        LinkedHashMap::new
                ));
    }

    //for first decision
    /*
    public List<Book> getBooks() {
        return entityManager.createQuery("from Book", Book.class).getResultList();
    }
    */
}

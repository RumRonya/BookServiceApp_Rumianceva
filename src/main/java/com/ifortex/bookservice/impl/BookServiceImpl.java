package com.ifortex.bookservice.impl;

import com.ifortex.bookservice.dao.BookDAO;
import com.ifortex.bookservice.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;

@Component
@Service
public class BookServiceImpl implements BookService {

    private final BookDAO bookDAO;

    @Autowired
    public BookServiceImpl(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public Map<String, Long> getBooks() {
        return bookDAO.getGenresAndCount();

        //first decision where Java was used more than SQL
        /*
        List<Book> books = bookDAO.getBooks();
        Map<String, Long> booksMap = new LinkedHashMap<>();
        for (Book book : books) {
            for (String genre : book.getGenres()) {
                if (booksMap.containsKey(genre)) {
                    booksMap.put(genre, booksMap.get(genre) + 1);
                }
                else {
                    booksMap.put(genre, 1L);
                }
            }
        }

        List<Map.Entry<String, Long>> list = new ArrayList<>(booksMap.entrySet());
        list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        LinkedHashMap<String, Long> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Long> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
        */
    }
}

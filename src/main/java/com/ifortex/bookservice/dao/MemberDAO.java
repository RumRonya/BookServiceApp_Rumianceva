package com.ifortex.bookservice.dao;

import com.ifortex.bookservice.model.Book;
import com.ifortex.bookservice.model.Member;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional
@Component
public class MemberDAO {

    private final EntityManager entityManager;

    @Autowired
    public MemberDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    //return list of Members who register in 2023 year, but didn't read any books
    public List<Member> getMembersFrom2023WithoutBooks() {
        //date of year-begin
        LocalDateTime startDateTime = LocalDateTime.of(2023, 1, 1, 0, 0);
        //date of year-end
        LocalDateTime endDateTime = LocalDateTime.of(2023, 12, 31, 23, 59);

        //return members who doesn't read books and registered in 2023
        return entityManager.createQuery("from Member where borrowedBooks is empty and membershipDate between :startDateTime and :endDateTime", Member.class)
                .setParameter("startDateTime", startDateTime)
                .setParameter("endDateTime", endDateTime)
                .getResultList();
    }

    //return Member who read the oldest book in Romance genre and who was most recently
    //registered on the platform
    public Member getTheYoungestReaderTheOldestRomance(){

        //get list of books with genre 'Romance'
        //using NativeQuery bc field Set<String> genres doesn't have @ElementCollection
        //so that is the best way to work with this column
        List<Book> books = entityManager.createNativeQuery("SELECT * FROM books WHERE 'Romance' = ANY(genre)", Book.class).getResultList();

        //return Member who read the oldest book in Romance genre and who was most recently
        //registered on the platform using variable books which was got by NativeQuery
        return entityManager.createQuery("select distinct m from Member m join fetch m.borrowedBooks b where b in :books order by b.publicationDate asc, m.membershipDate desc",
                Member.class).
                setParameter("books", books).
                getResultStream().
                findFirst().
                orElse(null);
    }
}

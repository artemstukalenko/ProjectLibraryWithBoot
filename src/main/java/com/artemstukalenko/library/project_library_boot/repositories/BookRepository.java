package com.artemstukalenko.library.project_library_boot.repositories;

import com.artemstukalenko.library.project_library_boot.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<Book, Integer> {

    @Modifying
    @Query("update Book set taken = :taken where bookId = :bookId")
    public void setTakenValue(@Param("bookId") int bookId, @Param("taken") boolean taken);

}

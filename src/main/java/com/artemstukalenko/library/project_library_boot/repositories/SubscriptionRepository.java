package com.artemstukalenko.library.project_library_boot.repositories;


import com.artemstukalenko.library.project_library_boot.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {

//    public boolean registerSubscriptionInDB(Subscription subscriptionToRegister);
//
//    public List<Subscription> getAllSubscriptions();
//
//    public Subscription findSubscriptionById(int id);
//
//    public boolean deleteSubscriptionFromDB(int id);
//
    @Query("from Subscription where bookId = :bookId")
    public Subscription findSubscriptionByBookId(@Param("bookId") int bookId);
}

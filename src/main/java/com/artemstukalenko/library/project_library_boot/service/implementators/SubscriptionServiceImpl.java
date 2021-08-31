package com.artemstukalenko.library.project_library_boot.service.implementators;

import com.artemstukalenko.library.project_library_boot.entity.Subscription;
import com.artemstukalenko.library.project_library_boot.repositories.SubscriptionRepository;
import com.artemstukalenko.library.project_library_boot.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Override
    public boolean registerSubscriptionInDB(Subscription subscriptionToRegister) {
        subscriptionRepository.save(subscriptionToRegister);
        return true;
    }

    @Override
    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    @Override
    public Subscription findSubscriptionById(int id) {
        Optional<Subscription> foundSubscription = subscriptionRepository.findById(id);
        return Optional.ofNullable(foundSubscription.get()).orElse(new Subscription());
    }

    @Override
    public boolean deleteSubscriptionFromDB(int id) {
        subscriptionRepository.deleteById(id);
        return true;
    }

    @Override
    public Subscription findSubscriptionByBookId(int bookId) {
        return subscriptionRepository.findSubscriptionByBookId(bookId);
    }
}

package com.artemstukalenko.library.project_library_boot.service.implementators;

import com.artemstukalenko.library.project_library_boot.entity.CustomSubscriptionRequest;
import com.artemstukalenko.library.project_library_boot.repositories.CustomSubscriptionRequestRepository;
import com.artemstukalenko.library.project_library_boot.service.CustomSubscriptionRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomSubscriptionRequestServiceImpl
        implements CustomSubscriptionRequestService {

    @Autowired
    CustomSubscriptionRequestRepository customSubscriptionRequestRepository;

    @Override
    public boolean addCustomSubscriptionRequestToDB(CustomSubscriptionRequest request) {
        customSubscriptionRequestRepository.save(request);
        return true;
    }

    @Override
    public boolean deleteCustomSubscriptionRequestFromDB(int id) {
        customSubscriptionRequestRepository.deleteById(id);
        return true;
    }

    @Override
    public List<CustomSubscriptionRequest> getAllRequests() {
        return customSubscriptionRequestRepository.findAll();
    }

    @Override
    public CustomSubscriptionRequest findRequestById(int id) {
        Optional<CustomSubscriptionRequest> foundRequest = customSubscriptionRequestRepository.findById(id);
        return Optional.ofNullable(foundRequest.get()).orElse(new CustomSubscriptionRequest());
    }

}

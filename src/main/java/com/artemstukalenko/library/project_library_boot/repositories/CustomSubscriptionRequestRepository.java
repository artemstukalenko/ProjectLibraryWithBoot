package com.artemstukalenko.library.project_library_boot.repositories;

import com.artemstukalenko.library.project_library_boot.entity.CustomSubscriptionRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomSubscriptionRequestRepository extends JpaRepository<CustomSubscriptionRequest, Integer> {
}

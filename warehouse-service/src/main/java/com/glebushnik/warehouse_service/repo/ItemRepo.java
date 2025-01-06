package com.glebushnik.warehouse_service.repo;

import com.glebushnik.warehouse_service.domain.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ItemRepo extends JpaRepository<Item, UUID> {
}

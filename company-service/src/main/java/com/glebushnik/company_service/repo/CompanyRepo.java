package com.glebushnik.company_service.repo;

import com.glebushnik.company_service.domain.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CompanyRepo extends JpaRepository<Company, UUID> {
}

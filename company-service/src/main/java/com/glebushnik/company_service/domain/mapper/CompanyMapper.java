package com.glebushnik.company_service.domain.mapper;

import com.glebushnik.company_service.domain.DTO.CompanyClientDTO;
import com.glebushnik.company_service.domain.entity.Company;
import org.springframework.stereotype.Service;

@Service
public class CompanyMapper {
    public Company mapCompany(CompanyClientDTO newCompany, Company oldCompany) {
        return Company.builder()
                .id(oldCompany.getId())
                .companyName(newCompany.companyName() != null ? newCompany.companyName() : oldCompany.getCompanyName())
                .owner(newCompany.owner() != null ? newCompany.owner() : oldCompany.getOwner())
                .build();
    }
}

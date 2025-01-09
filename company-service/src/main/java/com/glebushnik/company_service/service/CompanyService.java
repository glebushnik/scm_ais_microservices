package com.glebushnik.company_service.service;

import com.glebushnik.company_service.domain.DTO.CompanyClientDTO;
import com.glebushnik.company_service.domain.entity.Company;
import com.glebushnik.company_service.exception.CompanyNotFoundByIdException;

import java.util.List;
import java.util.UUID;

public interface CompanyService {
    public Company createCompany(CompanyClientDTO clientDTO);
    public List<Company> getAllCompanies();
    public Company getCompanyById(UUID companyId) throws CompanyNotFoundByIdException;
    public Company updateCompanyById(CompanyClientDTO newCompany, UUID companyId) throws CompanyNotFoundByIdException;
    public void deleteCompanyById(UUID companyId) throws CompanyNotFoundByIdException;

}

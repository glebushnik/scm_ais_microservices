package com.glebushnik.company_service.service.impl;

import com.glebushnik.company_service.domain.DTO.CompanyClientDTO;
import com.glebushnik.company_service.domain.entity.Company;
import com.glebushnik.company_service.domain.mapper.CompanyMapper;
import com.glebushnik.company_service.exception.CompanyNotFoundByIdException;
import com.glebushnik.company_service.repo.CompanyRepo;
import com.glebushnik.company_service.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepo companyRepo;
    private final CompanyMapper companyMapper;
    @Override
    public Company createCompany(CompanyClientDTO clientDTO) {
        return companyRepo.save(
            Company.builder()
                    .companyName(clientDTO.companyName())
                    .owner(clientDTO.owner())
                    .build()
        );
    }
    @Override
    public List<Company> getAllCompanies() {
        return companyRepo.findAll();
    }

    @Override
    public Company getCompanyById(UUID companyId) throws CompanyNotFoundByIdException {
        return companyRepo.findById(companyId).orElseThrow(
                ()-> new CompanyNotFoundByIdException("Компания с id " + companyId +" не найдена.")
        );
    }

    @Override
    public Company updateCompanyById(CompanyClientDTO newCompany,UUID companyId) throws CompanyNotFoundByIdException {
        var oldCompany = companyRepo.findById(companyId).orElseThrow(
                ()-> new CompanyNotFoundByIdException("Компания с id " + companyId +" не найдена.")
        );

        return companyRepo.save(companyMapper.mapCompany(newCompany, oldCompany));
    }

    @Override
    public void deleteCompanyById(UUID companyId) throws CompanyNotFoundByIdException {
        var company = companyRepo.findById(companyId).orElseThrow(
                ()-> new CompanyNotFoundByIdException("Компания с id " + companyId +" не найдена.")
        );

        companyRepo.deleteById(companyId);
    }
}

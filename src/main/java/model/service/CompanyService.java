package model.service;

import model.dao.CompanyDao;
import model.dto.CompanyDto;
import model.exeptions.CompanyAlreadyExistException;
import model.service.converter.CompanyConverter;
import model.storage.CompanyStorage;

import java.util.Optional;


public class CompanyService {
    private CompanyStorage companyStorage;
    private CompanyConverter companyConverter;

public  CompanyService (CompanyStorage companyStorage, CompanyConverter companyConverter) {
    this.companyStorage = companyStorage;
    this.companyConverter = companyConverter;
}


/*    public CompanyDto save (CompanyDto companyDto) {
        CompanyDao companyDao = companyStorage.save(companyConverter.to(companyDto));
        return companyConverter.from(companyDao);
    }

 */

    public Optional<CompanyDto> findByName(String companyName) {
        Optional<CompanyDao> companyByName = companyStorage.findByName(companyName);
        return companyByName.map(companyDao -> companyConverter.from(companyDao));
    }

    public void saveWithValidationByName(CompanyDto companyDto) {
        CompanyDao companyDao = companyStorage.findByName(companyDto.getCompany_name())
                .orElseGet(() -> companyStorage.save(companyConverter.to(companyDto)));
        if(!companyDao.getRating().toString().equals(companyDto.getRating().toString())) {
            throw new CompanyAlreadyExistException(String.format("Company with name '%s' already exist with different " +
                    "rating '%s'" , companyDto.getCompany_name(), companyDao.getRating().toString()));
        }
    }
}

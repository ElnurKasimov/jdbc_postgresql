package model.service;

import model.dao.CompanyDao;
import model.dto.CompanyDto;
import model.service.converter.CompanyConverter;
import model.storage.CompanyStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class CompanyService {
    private CompanyStorage companyStorage;
    private CompanyConverter companyConverter;

public  CompanyService (CompanyStorage companyStorage, CompanyConverter companyConverter) {
    this.companyStorage = companyStorage;
    this.companyConverter = companyConverter;
}


public List<String> save (CompanyDto companyDto) {
    List<String> result = new ArrayList<>();
    Optional<CompanyDao> companyFromDb = companyStorage.findByName(companyDto.getCompany_name());
    if (companyFromDb.isPresent()) {
        result.add(validateByName(companyDto, companyConverter.from(companyFromDb.get())));
    } else {
        companyStorage.save(companyConverter.to(companyDto));
        result.add("\tCompany successfully added to the database");
    };
    return result;
}

    public String  validateByName(CompanyDto companyDto, CompanyDto companyFromDb) {
        if (!companyDto.getRating().toString().equals(companyFromDb.getRating().toString())) {
            return String.format("\tCompany with name '%s' already exist with different " +
                    "rating '%s'. Please enter correct data",
                    companyDto.getCompany_name(), companyFromDb.getRating().toString());
        } else return "\tCompany successfully added to the database";
    }

}

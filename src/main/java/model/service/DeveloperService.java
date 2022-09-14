package model.service;

import lombok.Data;
import model.dao.CompanyDao;
import model.dao.DeveloperDao;
import model.dto.CompanyDto;
import model.dto.DeveloperDto;
import model.service.converter.CompanyConverter;
import model.service.converter.DeveloperConverter;
import model.storage.CompanyStorage;
import model.storage.DeveloperStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class DeveloperService {
    private DeveloperStorage developerStorage;
    private DeveloperConverter developerConverter;
    private CompanyService companyService;
    private CompanyStorage companyStorage;
    private CompanyConverter companyConverter;


public DeveloperService (DeveloperStorage developerStorage, DeveloperConverter developerConverter) {
    this.developerStorage = developerStorage;
    this.developerConverter = developerConverter;
}


/*
    public List<String> getAllNames() {
        List<String> result = new ArrayList<>();
    return result;
    }

 */

    public List<String> save (DeveloperDto developerDto) {
        List<String> result = new ArrayList<>();
        Optional<DeveloperDao> developerFromDb =
                developerStorage.findByName(developerDto.getLastName(), developerDto.getFirstName());
        if(developerFromDb.isPresent()) {
            result.add(validateByName(developerDto, developerConverter.from(developerFromDb.get())));
        } else {
            developerStorage.save(developerConverter.to(developerDto));
            result.add("\tDeveloper " + developerDto.getLastName() + " " +
                    developerDto.getFirstName() + " successfully added to the database");
        };
        return result;
    }

    public String validateByName(DeveloperDto developerDto, DeveloperDto developerFromDb) {
        if ( (developerDto.getAge() == developerFromDb.getAge()) &&
             (developerDto.getCompany_id() == developerFromDb.getCompany_id() ) &&
             (developerDto.getSalary() == developerFromDb.getSalary()) ) {
            return "\tDeveloper " + developerDto.getLastName() + " " +
                    developerDto.getFirstName() + " successfully added to the database";
        } else return   String.format("\tDeveloper with name '%s %s ' already exist with different another data." +
                         " Please enter correct data", developerDto.getLastName(), developerDto.getFirstName());
    }

}


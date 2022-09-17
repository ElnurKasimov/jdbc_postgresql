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
import view.Output;

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

    public DeveloperDto save (DeveloperDto developerDto) {
        List<String> result = new ArrayList<>();
        Optional<DeveloperDao> developerFromDb =
                developerStorage.findByName(developerDto.getLastName(), developerDto.getFirstName());
        DeveloperDao savedDeveloperWithId = new DeveloperDao();
        if(developerFromDb.isPresent()) {
            result.add(validateByName(developerDto, developerConverter.from(developerFromDb.get())));
        } else {
            savedDeveloperWithId = developerStorage.save(developerConverter.to(developerDto));
            result.add("\tDeveloper " + developerDto.getLastName() + " " +
                    developerDto.getFirstName() + " successfully added to the database");
        };
        Output.getInstance().print(result);
        return developerConverter.from(savedDeveloperWithId);
    }

    public String validateByName(DeveloperDto developerDto, DeveloperDto developerFromDb) {
        if ( (developerDto.getAge() == developerFromDb.getAge()) &&
             (developerDto.getCompanyDto().getCompany_name().equals(developerFromDb.getCompanyDto().getCompany_name() ) ) &&
             (developerDto.getSalary() == developerFromDb.getSalary()) ) {
            return "\tDeveloper " + developerDto.getLastName() + " " +
                    developerDto.getFirstName() + " successfully added to the database";
        } else return   String.format("\tDeveloper with name '%s %s ' already exist with different another data." +
                         " Please enter correct data", developerDto.getLastName(), developerDto.getFirstName());
    }

}


package model.service;

import model.dao.CompanyDao;
import model.dto.CompanyDto;
import model.service.converter.CompanyConverter;
import model.storage.CompanyStorage;
import view.Output;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;


public class CompanyService {
    private CompanyStorage companyStorage;
    private CompanyConverter companyConverter;

public  CompanyService (CompanyStorage companyStorage, CompanyConverter companyConverter) {
    this.companyStorage = companyStorage;
    this.companyConverter = companyConverter;
}


public CompanyDto save (CompanyDto companyDto) {
    List<String> result = new ArrayList<>();
    Optional<CompanyDao> companyFromDb = companyStorage.findByName(companyDto.getCompany_name());
    if (companyFromDb.isPresent()) {
        result.add(validateByName(companyDto, companyConverter.from(companyFromDb.get())));
    } else {
        companyStorage.save(companyConverter.to(companyDto));
        result.add("\tCompany " + companyDto.getCompany_name() + " successfully added to the database");
    };
    Output.getInstance().print(result);
    return companyDto;
}

    public String  validateByName(CompanyDto companyDto, CompanyDto companyFromDb) {
        if (!companyDto.getRating().toString().equals(companyFromDb.getRating().toString())) {
            return String.format("\tCompany with name '%s' already exist with different " +
                    "rating '%s'. Please enter correct data",
                    companyDto.getCompany_name(), companyFromDb.getRating().toString());
        } else return "";
    }

    public Optional<CompanyDto> findById(long id) {
    Optional<CompanyDao> companyDaoFromDb = companyStorage.findById(id);
    return companyDaoFromDb.map(companyDao -> companyConverter.from(companyDao));
    }

    public Optional<CompanyDto> findByName(String name) {
        Optional<CompanyDao> companyDaoFromDb = companyStorage.findByName(name);
        return companyDaoFromDb.map(companyDao -> companyConverter.from(companyDao));
    }

    public CompanyDto createCompany() {
        System.out.print("Enter company name : ");
        Scanner sc = new Scanner(System.in);
        String newCompanyName = sc.nextLine();
        System.out.print("Enter company rating (high, middle, low) : ");
        String newCompanyRating = sc.nextLine();
        return new CompanyDto(newCompanyName, CompanyDto.Rating.valueOf(newCompanyRating));
    }

 /*
    public long getIdByName (String name) {
        long id;
        Optional<CompanyDto> checkedCompany = findByName(name);
        if(checkedCompany.isPresent()) {return checkedCompany.get().getCompany_id();}
        else {
            System.out.println(" According to the fact that there is no company with such name in the database," +
                    "please enter full information about this company.");
            CompanyDto newCompanyDto = createCompany();
            return companyConverter.from(companyStorage.save(companyConverter.to(newCompanyDto))).getCompany_id();
        }
    }
*/

    public CompanyDto checkByName (String name) {
        CompanyDto companyDto = findByName(name).orElseGet(() -> {
            System.out.println("Unfortunately, there is no company with such name in the database. " +
                    "Please create the company.");
           return createCompany();
        });
        return  save(companyDto);
    }

}

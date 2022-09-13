package model.service;

import lombok.Data;
import model.dao.CompanyDao;
import model.dto.CompanyDto;
import model.dto.DeveloperDto;
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


public DeveloperService (DeveloperStorage developerStorage, DeveloperConverter developerConverter) {
    this.developerStorage = developerStorage;
    this.developerConverter = developerConverter;
}



    public List<String> getAllNames() {
        List<String> result = new ArrayList<>();



    return result;
    }

    public List<String> save (DeveloperDto developerDto) {
        List<String> result = new ArrayList<>();
        Optional.ofNullable(developerDto.getDeveloper_id()).orElseGet(() -> {
            System.out.println(" According to the fact that there is no company with such name in the database," +
                    "please enter additional information.");
            companyService.createCompany();
            // а в базе сохранение есть этой компании?
            // после создания компании надо в поле Dto добавить id этой компании
            return null;
        });

        if(developerStorage.findByName(developerDto.getLastName(), developerDto.getFirstName()).isPresent()) {
            // проверка остальные поля совпадают или нет
            // если нет - result.dd (такой уже есть с другими данными)
            // если совпадают, то   result.dd (успешно добавлен)
        } else {
            // saveDto, с автоматичесвим сохранением в базе (developerStorage)
            // result.dd (успешно добавлен)
        };


        return result;
    }


}


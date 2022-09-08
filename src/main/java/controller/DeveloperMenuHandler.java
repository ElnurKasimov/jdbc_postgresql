package controller;

import lombok.Data;
import model.dao.DeveloperDao;
import model.dto.DeveloperDto;
import model.service.DeveloperService;
import model.service.converter.DeveloperConverter;
import model.storage.CompanyStorage;
import model.storage.DeveloperStorage;
import view.Output;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DeveloperMenuHandler {
    private DeveloperService developerService;
    private DeveloperStorage developerStorage;
    private DeveloperConverter developerConverter;

public DeveloperMenuHandler (DeveloperService developerService, DeveloperStorage developerStorage,
                             DeveloperConverter developerConverter) {
    this.developerService = developerService;
    this.developerStorage = developerStorage;
    this.developerConverter = developerConverter;
}


    public void getAllNames() {
        List<DeveloperDao> developerDaoList = developerStorage.findAll();
        List<String> result = new ArrayList<>();
        for (DeveloperDao developerDao : developerDaoList) {
            result.add(String.format("%d. %s %s, возраст %d лет, зарплата %d",
                    developerDao.getDeveloper_id(),
                    developerDao.getLastName(), developerDao.getFirstName(),
                    developerDao.getAge(), developerDao.getSalary()));
        }
        Output.getInstance().print(result);
    }

}

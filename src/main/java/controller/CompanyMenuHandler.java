package controller;

import model.dao.CompanyDao;
import model.dao.DeveloperDao;
import model.service.CompanyService;
import model.service.DeveloperService;
import model.service.converter.CompanyConverter;
import model.service.converter.DeveloperConverter;
import model.storage.CompanyStorage;
import model.storage.DeveloperStorage;
import view.Output;

import java.util.ArrayList;
import java.util.List;


public class CompanyMenuHandler {
    private CompanyService companyService;
    private CompanyStorage companyStorage;
    private CompanyConverter companyConverter;

public CompanyMenuHandler(CompanyService companyService, CompanyStorage companyStorage,CompanyConverter companyConverter) {
    this.companyService = companyService;
    this.companyStorage = companyStorage;
    this.companyConverter = companyConverter;
}


    public void getAllNames() {
        List<CompanyDao> companyDaoList = companyStorage.findAll();
        List<String> result = new ArrayList<>();
        for (CompanyDao companyDao : companyDaoList) {
            result.add(String.format("%d. %s, рейтинг -  %s",
                    companyDao.getCompany_id(),
                    companyDao.getCompany_name(),
                    companyDao.getRating()));
        }
        Output.getInstance().print(result);
    }

}

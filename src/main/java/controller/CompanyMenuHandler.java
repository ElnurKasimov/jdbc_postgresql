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
import java.util.Scanner;


public class CompanyMenuHandler {
    private CompanyService companyService;
    private CompanyStorage companyStorage;
    private CompanyConverter companyConverter;
    private MenuService menuService;
    private static final int EXIT_FROM_COMPANY_NENU = 4;

public CompanyMenuHandler(CompanyService companyService, CompanyStorage companyStorage,
                          CompanyConverter companyConverter, MenuService menuService) {
    this.companyService = companyService;
    this.companyStorage = companyStorage;
    this.companyConverter = companyConverter;
    this.menuService = menuService;
}


    public void launchCoreModule() {
        int choiceCompanies;
        do {
            menuService.get("Companies").printMenu();
            choiceCompanies = menuService.get("Companies").makeChoice();
            switch (choiceCompanies) {
                case 1:
                    getAllNames();
                    break;
                case 2:
                    //companyDaoService.addCompany();
                    break;
                case 3:
                    System.out.print("Внесите название компании, которую вы хотите удалить :");
                    Scanner sc33 = new Scanner(System.in);
                    String nameInput3 = sc33.nextLine();
                                /*ArrayList<String> companyProjects = new CompanyDaoService(Storage.getInstance().getConnection()).getCompanyProjects(nameInput3);
                                if (companyProjects.size() == 0) {
                                  companyDaoService.deleteCompany(nameInput3);
                                    break;}
                                System.out.println("\tЭта компания разрабатывает следующие проекты:");
                                for (String project : companyProjects) {
                                    System.out.println("\t\t- " + project + ", в котором задействованы следующие разработчики:");
                                    new ProjectDaoService(Storage.getInstance().getConnection()).getListDevelopers(project);
                                }
                                System.out.println("Для того, чтобы удалить эту компанию - внесите ссответствующие изменения в таблицы developers, projects");

                                 */
                    break;
            }
        } while (choiceCompanies != EXIT_FROM_COMPANY_NENU);
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

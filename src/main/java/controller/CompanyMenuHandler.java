package controller;

import model.dao.CompanyDao;
import model.dto.CompanyDto;
import model.service.CompanyService;
import model.storage.CompanyStorage;
import view.Output;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class CompanyMenuHandler {
    private CompanyService companyService;
    private CompanyStorage companyStorage;
    private MenuService menuService;
    private static final int EXIT_FROM_COMPANY_MENU = 4;

public CompanyMenuHandler(CompanyService companyService, CompanyStorage companyStorage,
                           MenuService menuService) {
    this.companyService = companyService;
    this.companyStorage = companyStorage;
    this.menuService = menuService;
}


    public void launch() {
        int choiceCompanies;
        do {
            menuService.get("Companies").printMenu();
            choiceCompanies = menuService.get("Companies").makeChoice();
            switch (choiceCompanies) {
                case 1:
                    getAllNames();
                    break;
                case 2:
                    addCompanyToDb();
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
        } while (choiceCompanies != EXIT_FROM_COMPANY_MENU);
    }

    private  void getAllNames() {
        List<CompanyDao> companyDaoList = companyStorage.findAll();
        List<String> result = new ArrayList<>();
        for (CompanyDao companyDao : companyDaoList) {
            result.add(String.format("\t%d. %s, rating -  %s",
                    companyDao.getCompany_id(),
                    companyDao.getCompany_name(),
                    companyDao.getRating()));
        }
        Output.getInstance().print(result);
    }

    private  void addCompanyToDb() {
        CompanyDto newCompanyDto = companyService.createCompany();
        companyService.save(newCompanyDto);
    }


}

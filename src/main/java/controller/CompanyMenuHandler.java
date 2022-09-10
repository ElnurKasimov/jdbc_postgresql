package controller;

import model.dao.CompanyDao;
import model.dto.CompanyDto;
import model.service.CompanyService;
import model.service.converter.CompanyConverter;
import model.storage.CompanyStorage;
import view.Output;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class CompanyMenuHandler {
    private CompanyService companyService;
    private CompanyStorage companyStorage;
    private CompanyConverter companyConverter;
    private MenuService menuService;
    private static final int EXIT_FROM_COMPANY_MENU = 4;

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
                    createCompany();
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
            result.add(String.format("%d. %s, рейтинг -  %s",
                    companyDao.getCompany_id(),
                    companyDao.getCompany_name(),
                    companyDao.getRating()));
        }
        Output.getInstance().print(result);
    }

    private  void createCompany() {
        System.out.print("Введите название компании : ");
        Scanner sc = new Scanner(System.in);
        String newCompanyName = sc.nextLine();
        System.out.print("Введите рейтинг компании (high, middle, low) : ");
        String newCompanyRating = sc.nextLine();
        CompanyDto newCompanyDto = new CompanyDto(newCompanyName, CompanyDto.Rating.valueOf(newCompanyRating));

        companyService.save(newCompanyDto);
/*
        addCompanySt.setLong(1, newCompanyId);
        addCompanySt.setString(2, newCompanyName);
        addCompanySt.setString(3, newCompanyRating);
        Company company = new Company();

        company.setCompany_id(newCompanyId);
        company.setCompany_name(newCompanyName);
        company.setRating(Company.Rating.valueOf(newCompanyRating));

        addCompanySt.executeUpdate();

        if (existsCompany(newCompanyId)) {System.out.println("Компания успешно добавлена");}
        else System.out.println("Что-то пошло не так и компания не была  добавлен в базу данных");

 */

        List<String> result = new ArrayList<>();
        Output.getInstance().print(result);
    }
}

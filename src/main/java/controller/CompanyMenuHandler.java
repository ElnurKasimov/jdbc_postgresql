package controller;


import model.dto.CompanyDto;
import model.dto.ProjectDto;
import model.service.CompanyService;
import model.service.DeveloperService;
import model.service.ProjectService;

import java.util.List;
import java.util.Scanner;


public class CompanyMenuHandler {
    private CompanyService companyService;
    private ProjectService projectService;
    private DeveloperService developerService;

    private MenuService menuService;
    private static final int EXIT_FROM_COMPANY_MENU = 4;

public CompanyMenuHandler(CompanyService companyService, MenuService menuService,
            ProjectService projectService, DeveloperService developerService) {
    this.companyService = companyService;
    this.menuService = menuService;
    this.projectService = projectService;
    this.developerService = developerService;
}

    public void launch() {
        int choiceCompanies;
        do {
            menuService.get("Companies").printMenu();
            choiceCompanies = menuService.get("Companies").makeChoice();
            switch (choiceCompanies) {
                case 1:
                    companyService.findAllCompanies();
                    break;
                case 2:
                    addCompanyToDb();
                    break;
                case 3:
                    deleteCompany();
                    break;
            }
        } while (choiceCompanies != EXIT_FROM_COMPANY_MENU);
    }

    private  void addCompanyToDb() {
        CompanyDto newCompanyDto = companyService.createCompany();
        companyService.save(newCompanyDto);
    }

    private void deleteCompany() {
        System.out.print("Enter name of the company to delete : ");
        Scanner sc = new Scanner(System.in);
        String name = null;
        while (true) {
            name = sc.nextLine();
            if (companyService.findByName(name).isEmpty()) {
                System.out.print("Unfortunately, there is no company with such name in the database.  Please enter correct company name :");
            } else {
                break;
            }
        }
        List<ProjectDto> projectDtoList = projectService.getCompanyProjects(name);
        if (!projectDtoList.isEmpty()) {
            System.out.println("\tThe company cannot be deleted because it develop such projects with respectively involved developers :");
            projectDtoList.forEach(projectDto -> {
                System.out.println("\t " + projectDto.getProject_name() + " : ");
                developerService.getDevelopersNamesByProjectName(projectDto.getProject_name()).
                        forEach(developerName -> System.out.println("\t\t" + developerName));
            });
            System.out.println("For deleting the company at first delete these developers and these projects in the appropriate sections of the database.");
        } else {
            companyService.deleteCompany(name);
        }
    }
}

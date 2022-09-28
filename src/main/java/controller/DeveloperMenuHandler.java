package controller;

import model.dto.CompanyDto;
import model.dto.DeveloperDto;
import model.dto.ProjectDto;
import model.dto.SkillDto;
import model.service.*;

import java.util.*;


public class DeveloperMenuHandler {
    private DeveloperService developerService;
    private MenuService menuService;
    private CompanyService companyService;
    private ProjectService projectService;
    private SkillService skillService;
    private RelationService relationService;
    private static final int EXIT_FROM_DEVELOPER_MENU = 8;

    public DeveloperMenuHandler(DeveloperService developerService, MenuService menuService,
                                CompanyService companyService, ProjectService projectService,
                                SkillService skillService, RelationService relationService) {
        this.developerService = developerService;
        this.menuService = menuService;
        this.companyService = companyService;
        this.projectService = projectService;
        this.skillService = skillService;
        this.relationService = relationService;
    }

    public void launch() {
        int choiceDevelopers;
        do {
            menuService.get("Developers").printMenu();
            choiceDevelopers = menuService.get("Developers").makeChoice();
            switch (choiceDevelopers) {
                case 1:
                    developerService.findAllDevelopers();
                    break;
                case 2:
                    getAdditionalInfoByName();
                    break;
                case 3:
                    developerService.getQuantityJavaDevelopers();
                    break;
                case 4:
                    developerService.getListNamesOfMiddleDevelopers();
                    break;
                case 5:
                    createDeveloper();
                    break;
                case 6:
                    developerService.updateDeveloper();
                    break;
                case 7:
                    System.out.println("Внесите данные по разработчику, которого вы хотите удалить");
                    Scanner sc17 = new Scanner(System.in);
                    System.out.print("Введите фамилию : ");
                    String lastNameInput7 = sc17.nextLine();
                    System.out.print("Введите имя : ");
                    String firstNameInput7 = sc17.nextLine();
                    //developerDaoService.deleteDeveloper(lastNameInput7, firstNameInput7);
                    break;
            }
        } while (choiceDevelopers != EXIT_FROM_DEVELOPER_MENU);
    }

    private void createDeveloper() {
        System.out.println("\tEnter, please, such data for developer.");
        Scanner sc = new Scanner(System.in);
        System.out.print("\tLast name : ");
        String lastName = sc.nextLine();
        System.out.print("\tFirst name : ");
        String firstName = sc.nextLine();
        System.out.print("\tAge (only digits): ");
        int age = Integer.parseInt(sc.nextLine());
        System.out.print("\tSalary (only digits): ");
        int salary = Integer.parseInt(sc.nextLine());
        System.out.print("\tCompany where he works: ");
        String companyName;
        while (true) {
            companyName = sc.nextLine();
            if (companyService.findByName(companyName).isEmpty()) {
                System.out.print("Unfortunately, there is no company with such name in the database. " +
                        "Do You want to create it (yes/no) or ? : ");
                String createOrNot = sc.nextLine();
                if (createOrNot.equalsIgnoreCase("yes")) break;
                System.out.print( "Enter correct company name : ");
            } else { break;}
        }
        CompanyDto checkedCompanyDto = companyService.checkByName(companyName);
        DeveloperDto newDeveloperDto = new DeveloperDto(lastName, firstName, age, checkedCompanyDto, salary);
        Set<ProjectDto> checkedProjectsDto = projectService.checkByCompanyName(companyName);
        Set<SkillDto> skillsDto = new HashSet<>();
        while (true) {
            System.out.print("\tLanguage the developer operated  : ");
            String language = sc.nextLine();
            System.out.print("\tLevel knowledge of the language (junior, middle, senior) : ");
            String level = sc.nextLine();
            SkillDto skillDto = skillService.findByLanguageAndLevel(language, level);
            skillsDto.add(skillDto);
            System.out.print("One more language? (yes/no) : ");
            String anotherLanguage = sc.nextLine();
            if (anotherLanguage.equalsIgnoreCase("no")) break;
        }
        newDeveloperDto = developerService.save(newDeveloperDto);
        relationService.saveProjectDeveloperRelation(checkedProjectsDto, newDeveloperDto);
        relationService.saveDeveloperSkillRelation(newDeveloperDto, skillsDto);
    }

    void getAdditionalInfoByName() {
        while (true) {
            System.out.print("\tEnter last name : ");
            Scanner sc = new Scanner(System.in);
            String lastName = sc.nextLine();
            System.out.print("\tEnter first name : ");
            String firstName = sc.nextLine();
            if (developerService.isExist(lastName, firstName)) {
                developerService.getInfoByName(lastName, firstName);
                break;
            }
            System.out.println("There is no such developer in the database. Please enter correct data");
        }
    }

}

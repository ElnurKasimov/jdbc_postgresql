package controller;

import model.dao.DeveloperDao;
import model.dto.CompanyDto;
import model.dto.DeveloperDto;
import model.dto.ProjectDto;
import model.dto.SkillDto;
import model.service.*;
import model.storage.DeveloperStorage;
import view.Output;

import java.sql.ResultSet;
import java.util.*;


public class DeveloperMenuHandler {
    private DeveloperService developerService;
    private DeveloperStorage developerStorage;
    private MenuService menuService;
    private CompanyService companyService;
    private ProjectService projectService;
    private SkillService skillService;
    private static final int EXIT_FROM_DEVELOPER_MENU = 8;

public DeveloperMenuHandler (DeveloperService developerService, DeveloperStorage developerStorage,
                             MenuService menuService, CompanyService companyService,
                             ProjectService projectService, SkillService skillService) {
    this.developerService = developerService;
    this.developerStorage = developerStorage;
    this.menuService = menuService;
    this.companyService = companyService;
    this.projectService = projectService;
    this.skillService = skillService;
}


    public void launch() {
        int choiceDevelopers;
        do {
            menuService.get("Developers").printMenu();
            choiceDevelopers = menuService.get("Developers").makeChoice();
            switch (choiceDevelopers) {
                case 1:
                    getAllNames();
                    break;
                case 2:
                    System.out.print("\tВведите фамилию : ");
                    Scanner sc12 = new Scanner(System.in);
                    String lastNameInput2 = sc12.nextLine();
                    System.out.print("\tВведите имя : ");
                    String firstNameInput2 = sc12.nextLine();
                    //developerDaoService.getInfoByName(lastNameInput2, firstNameInput2);
                    break;
                case 3:
                    //developerDaoService.getQuantityJavaDevelopers();
                    break;
                case 4:
                    //developerDaoService.getListMiddleDevelopers();
                    break;
                case 5:
                    createDeveloper();
                    break;
                case 6:
                    System.out.println("Для внесения изменения хоть в одно поле данных необходимо обновить все поля");
                    System.out.println("Данные по какому разработчику вы планируете изменить?");
                    Scanner sc16 = new Scanner(System.in);
                    System.out.print("Введите фамилию : ");
                    String lastNameInput6 = sc16.nextLine();
                    System.out.print("Введите имя : ");
                    String firstNameInput6 = sc16.nextLine();
                    long idToDelete;
                    //  try {
                    // idToDelete = developerDaoService.getIdByName(lastNameInput6, firstNameInput6);
                    //}  catch (SQLException e) {
                    //     System.out.println("В базе данных такого разработчика не существует. Вводите корректные данные.");
                    //    break;
                    //}
                    //developerDaoService.deleteDeveloper(idToDelete);
                    //int update = developerDaoService.addDeveloper(lastNameInput6, firstNameInput6);
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

    public void getAllNames() {
        List<DeveloperDao> developerDaoList = developerStorage.findAll();
        List<String> result = new ArrayList<>();
        for (DeveloperDao developerDao : developerDaoList) {
            result.add(String.format("\t%d. %s %s, age %d, salary %d",
                    developerDao.getDeveloper_id(),
                    developerDao.getLastName(), developerDao.getFirstName(),
                    developerDao.getAge(), developerDao.getSalary()));
        }
        Output.getInstance().print(result);
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
        String companyName = sc.nextLine();

        CompanyDto checkedCompanyDto = companyService.checkByName(companyName);
        DeveloperDto newDeveloperDto = new DeveloperDto(lastName, firstName, age, checkedCompanyDto, salary);

        ProjectDto checkedProjectDto = projectService.checkByCompanyName (companyName); // project with id already



/*
        Set<SkillDto> skills = new HashSet<>();
        while(true) {
            System.out.print("\tLanguage the developer operated : ");
            String language = sc.nextLine();
            if (language.equals("")) language = sc.nextLine();
            System.out.print("\tLevel knowledge of the language (junior, middle, senior) : ");
            String level = sc.nextLine();

            //todo
            /*{
             1.  check is this pair exist in DB
                 if  not - add this pair  to  table Skill and return id the pair
                 if yes - return id the pair


            long skillId = skillService.getIdSkillByLanguageAndLevel(language, level);


            skills.add(skillDto);
            System.out.print("One more language? (yes/no) : ");
            String anotherLanguage = sc.nextLine();
            if(anotherLanguage.equalsIgnoreCase("no")) break;
        }
*/



        newDeveloperDto = developerService.save(newDeveloperDto);
       // newDeveloperDto.setSkills(skills);
        newDeveloperDto.setProjectDto(checkedProjectDto);



        projectService.saveProjectDeveloperRelation(checkedProjectDto,newDeveloperDto);



    }



}

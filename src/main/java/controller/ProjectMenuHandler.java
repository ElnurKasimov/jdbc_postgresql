package controller;

import model.dao.ProjectDao;
import model.dto.DeveloperDto;
import model.service.ProjectService;
import model.storage.ProjectStorage;
import view.Output;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ProjectMenuHandler {
    private ProjectService projectService;
    private ProjectStorage projectStorage;
    private MenuService menuService;
    private static final int EXIT_FROM_PROJECT_MENU = 9;

public ProjectMenuHandler(ProjectService projectService, ProjectStorage projectStorage,
                          MenuService menuService) {
    this.projectService = projectService;
    this.projectStorage = projectStorage;
    this.menuService = menuService;
}

    public void launch() {
        int choiceProjects;
        do {
            menuService.get("Projects").printMenu();
            choiceProjects = menuService.get("Projects").makeChoice();
            switch (choiceProjects) {
                case 1:
                    getAllNames();
                    break;
                case 2:
                    System.out.print("Введите название проекта : ");
                    Scanner sc22 = new Scanner(System.in);
                    String projectNameInput2 = sc22.nextLine();
                    //projectDaoService.getInfoByName(projectNameInput2);
                    break;
                case 3:
                    System.out.print("Введите название проекта : ");
                    Scanner sc23 = new Scanner(System.in);
                    String projectNameInput3 = sc23.nextLine();
                    System.out.println("\tВ проекте " + projectNameInput3 + " задействованы следующие разработчики: ");
                    // projectDaoService.getListDevelopers(projectNameInput3);
                    break;
                case 4:
                    System.out.print("Введите название проекта : ");
                    Scanner sc24 = new Scanner(System.in);
                    String projectNameInput4 = sc24.nextLine();
                    //  projectDaoService.getBudgetByProjectName(projectNameInput4);
                    break;
                case 5:
                    //projectDaoService.getProjectsListInSpecialFormat();
                    break;
                case 6:
                    System.out.print("Введите название проекта : ");
                    Scanner sc26 = new Scanner(System.in);
                    String projectNameInput6 = sc26.nextLine();
                    // int add = projectDaoService.addProject(projectNameInput6);
                    break;
                case 7:
                    System.out.println("Для внесения изменения хоть в одно поле данных необходимо обновить все поля");
                    System.out.print("Введите название проекта, данные по которому вы планируете изменить: ");
                    Scanner sc27 = new Scanner(System.in);
                    String nameInput7 = sc27.nextLine();
                    long idToDelete;
                               /* try {
                                    idToDelete = projectDaoService.getIdByName(nameInput7);
                                }  catch (SQLException e) {
                                    System.out.println("В базе данных такого проекта не существует. Вводите корректные данные.");
                                    break;
                                }
                                 projectDaoService.deleteProject(idToDelete);
                                int update = projectDaoService.addProject(nameInput7);
                                */
                    break;
                case 8:
                    System.out.print("Внесите название проекта, которого вы хотите удалить :");
                    Scanner sc28 = new Scanner(System.in);
                    String nameInput8 = sc28.nextLine();
                    // projectDaoService.deleteProject(nameInput8);
                    break;
            }
        } while (choiceProjects != EXIT_FROM_PROJECT_MENU);
    }

    public void getAllNames() {
        List<ProjectDao> projectDaoList = projectStorage.findAll();
        List<String> result = new ArrayList<>();
        for (ProjectDao projectDao : projectDaoList) {
            result.add(String.format("\t%d. %s, budget - %d, launched  %s",
                    projectDao.getProject_id(),
                    projectDao.getProject_name(),
                    projectDao.getCost(),
                    projectDao.getStart_date().toString()));
        }
        Output.getInstance().print(result);
    }

    private void createProject() {
        System.out.println("\tEnter, please, such data for project.");
        Scanner sc = new Scanner(System.in);
        System.out.print("\tProject name : ");
        System.out.print("\tThe company which develops the project: ");
        String companyName = sc.nextLine();
        System.out.print("\tThe customer who ordered the development of the project: ");
        String customerName = sc.nextLine();
        System.out.print("\tBudget of the project (only digits): ");
        int salary = Integer.parseInt(sc.nextLine());
        System.out.print("\tStart date of the project (in format yyyy-mm-dd): ");
        String startDate = sc.nextLine();
/*
        long companyId = companyService.getIdByName(companyName);
        DeveloperDto newDeveloperDto = new DeveloperDto(lastName, firstName, age, companyId, salary);
        List<String> result = developerService.save(newDeveloperDto);
        Output.getInstance().print(result);

 */
    }
}

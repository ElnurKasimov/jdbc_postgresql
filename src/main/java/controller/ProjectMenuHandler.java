package controller;

import model.service.ProjectService;
import view.Output;

import java.util.Scanner;


public class ProjectMenuHandler {
    private ProjectService projectService;
    private MenuService menuService;
    private static final int EXIT_FROM_PROJECT_MENU = 9;

public ProjectMenuHandler(ProjectService projectService, MenuService menuService) {
    this.projectService = projectService;
    this.menuService = menuService;
}

    public void launch() {
        int choiceProjects;
        do {
            menuService.get("Projects").printMenu();
            choiceProjects = menuService.get("Projects").makeChoice();
            switch (choiceProjects) {
                case 1:
                    Output.getInstance().print(projectService.getAllProjects());
                    break;
                case 2:
                    getAdditionalInfoByName();
                    break;
                case 3:
                    getDevelopersList();
                    break;
                case 4:
                    getProjectExpenses();
                    break;
                case 5:
                    projectService.getProjectsListInSpecialFormat();
                    break;
                case 6:
                    projectService.save(projectService.createProject());
                    break;
                case 7:
                    projectService.updateProject();
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

    private void getAdditionalInfoByName() {
        while (true) {
            System.out.print("\tEnter project name : ");
            Scanner sc = new Scanner(System.in);
            String projectName = sc.nextLine();
            if (projectService.isExist(projectName)) {
                projectService.getInfoByName(projectName);
                break;
            }
            System.out.println("There is no such project in the database. Please enter correct data");
        }
    }

    private void getDevelopersList () {
        while (true) {
            System.out.print("\tEnter project name : ");
            Scanner sc = new Scanner(System.in);
            String projectName = sc.nextLine();
            if (projectService.isExist(projectName)) {
                projectService.getDevelopersNamesByProjectName(projectName);
                break;
            }
            System.out.println("There is no such project in the database. Please enter correct data");
        }
    }

    private void getProjectExpenses() {
        while (true) {
            System.out.print("\tEnter project name : ");
            Scanner sc = new Scanner(System.in);
            String projectName = sc.nextLine();
            if (projectService.isExist(projectName)) {
                projectService.getProjectExpences(projectName);
                break;
            }
            System.out.println("There is no such project in the database. Please enter correct data");
        }
    }

}

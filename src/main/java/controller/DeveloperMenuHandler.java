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
import java.util.Scanner;


public class DeveloperMenuHandler {
    private DeveloperService developerService;
    private DeveloperStorage developerStorage;
    private DeveloperConverter developerConverter;
    private MenuService menuService;
    private static final int EXIT_FROM_DEVELOPER_NENU = 8;

public DeveloperMenuHandler (DeveloperService developerService, DeveloperStorage developerStorage,
                             DeveloperConverter developerConverter, MenuService menuService) {
    this.developerService = developerService;
    this.developerStorage = developerStorage;
    this.developerConverter = developerConverter;
    this.menuService = menuService;
}


    public void launchCoreModule() {
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
                    System.out.println("\tВведите, пожалуйста следующие данные по разработчику");
                    Scanner sc15 = new Scanner(System.in);
                    System.out.print("\tВведите фамилию : ");
                    String lastNameInput5 = sc15.nextLine();
                    System.out.print("\tВведите имя : ");
                    String firstNameInput5 = sc15.nextLine();
                    //int add = developerDaoService.addDeveloper(lastNameInput5, firstNameInput5);

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
        } while (choiceDevelopers != EXIT_FROM_DEVELOPER_NENU);
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

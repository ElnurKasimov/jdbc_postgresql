
import controller.*;
import model.config.DatabaseManagerConnector;
import model.config.Migration;
import model.config.PropertiesConfig;
import model.dao.DeveloperDao;
import model.service.CompanyService;
import model.service.CustomerService;
import model.service.DeveloperService;
import model.service.ProjectService;
import model.service.converter.CompanyConverter;
import model.service.converter.CustomerConverter;
import model.service.converter.DeveloperConverter;
import model.service.converter.ProjectConverter;
import model.storage.CompanyStorage;
import model.storage.CustomerStorage;
import model.storage.DeveloperStorage;
import model.storage.ProjectStorage;
import view.Output;

import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class App {
    private static DatabaseManagerConnector manager;
    private static MenuService menuService;
    private static DeveloperStorage developerStorage;
    private static DeveloperService developerService;
    private static DeveloperConverter developerConverter;
    private static CompanyStorage companyStorage;
    private static CompanyService companyService;
    private static CompanyConverter companyConverter;
    private static CustomerStorage customerStorage;
    private static CustomerService customerService;
    private static CustomerConverter customerConverter;
    private static ProjectStorage projectStorage;
    private static ProjectService projectService;
    private static ProjectConverter projectConverter;
    private static final int EXIT_FROM_MAIN_NENU = 5;

    public static void main(String[] args) {
        initDbAndMigrations();
        initAllServiceStorageConverterClasses();
        menuService.create();
        int choice;
        do {
            menuService.get("Main").printMenu();
            choice = menuService.get("Main").makeChoice();
            switch (choice) {
                case 1:
                    new DeveloperMenuHandler(developerService, developerStorage,
                            developerConverter, menuService).launchCoreModule();
                    break;
                case 2:
                    new ProjectMenuHandler(projectService, projectStorage,
                            projectConverter, menuService).launchCoreModule();
                    break;
                case 3:
                    new CompanyMenuHandler(companyService, companyStorage,
                            companyConverter, menuService).launchCoreModule();
                    break;
                case 4:
                    new CustomerMenuHandler(customerService, customerStorage,
                            customerConverter, menuService).launchCoreModule();
                    break;
            }
        } while (choice != EXIT_FROM_MAIN_NENU);
    }


    private static void initDbAndMigrations() {
        String dbPassword = System.getenv("dbPassword");
        String dbUsername = System.getenv("dbusername");
        PropertiesConfig propertiesConfig = new PropertiesConfig();
        Properties properties = propertiesConfig.loadProperties("application.properties");
        DatabaseManagerConnector manager = new DatabaseManagerConnector(properties, dbUsername, dbPassword);
        new Migration(manager).initDb();
    }

    private static void initAllServiceStorageConverterClasses() {
        MenuService menuService = new MenuService();
        try {
            developerStorage = new DeveloperStorage(manager);
            developerService = new DeveloperService();
            developerConverter = new DeveloperConverter();
            companyStorage = new CompanyStorage(manager);
            companyService = new CompanyService();
            companyConverter = new CompanyConverter();
            customerStorage = new CustomerStorage(manager);
            customerService = new CustomerService();
            customerConverter = new CustomerConverter();
            projectStorage = new ProjectStorage(manager);
            projectService = new ProjectService();
            projectConverter = new ProjectConverter();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
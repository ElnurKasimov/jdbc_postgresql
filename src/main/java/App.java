
import controller.*;
import model.config.DatabaseManagerConnector;
import model.config.Migration;
import model.config.PropertiesConfig;
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

import java.sql.SQLException;
import java.util.Properties;

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
    private static final int EXIT_FROM_MAIN_MENU = 5;

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
                    new DeveloperMenuHandler(developerService, developerStorage, menuService).launch();
                    break;
                case 2:
                    new ProjectMenuHandler(projectService, projectStorage, menuService).launch();
                    break;
                case 3:
                    new CompanyMenuHandler(companyService, companyStorage, menuService).launch();
                    break;
                case 4:
                    new CustomerMenuHandler(customerService, customerStorage, menuService).launch();
                    break;
            }
        } while (choice != EXIT_FROM_MAIN_MENU);
    }


    private static void initDbAndMigrations() {
        String dbPassword = System.getenv("dbPassword");
        String dbUsername = System.getenv("dbusername");
        PropertiesConfig propertiesConfig = new PropertiesConfig();
        Properties properties = propertiesConfig.loadProperties("application.properties");
        manager = new DatabaseManagerConnector(properties, dbUsername, dbPassword);
        new Migration(manager).initDb();
    }

    private static void initAllServiceStorageConverterClasses() {
        menuService = new MenuService();
        try {
            developerStorage = new DeveloperStorage(manager);
            developerConverter = new DeveloperConverter();
            developerService = new DeveloperService(developerStorage, developerConverter);
            companyStorage = new CompanyStorage(manager);
            companyConverter = new CompanyConverter();
            companyService = new CompanyService(companyStorage, companyConverter);
            customerStorage = new CustomerStorage(manager);
            customerConverter = new CustomerConverter();
            customerService = new CustomerService(customerStorage, customerConverter);
            projectStorage = new ProjectStorage(manager);
            projectConverter = new ProjectConverter();
            projectService = new ProjectService(projectStorage, projectConverter);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
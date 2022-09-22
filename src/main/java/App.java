
import controller.*;
import model.config.DatabaseManagerConnector;
import model.config.Migration;
import model.config.PropertiesConfig;
import model.service.*;
import model.service.converter.*;
import model.storage.*;

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
    private static SkillService skillService;
    private  static SkillStorage skillStorage;
    private static SkillConverter skillConverter;
    private static RelationStorage relationStorage;
    private static RelationService relationService;
    private static final int EXIT_FROM_MAIN_MENU = 5;

    public static void main(String[] args) {
        initDatabaseAndMigrations();
        initAllServiceStorageConverterClasses();
        menuService.create();
        int choice;
        do {
            menuService.get("Main").printMenu();
            choice = menuService.get("Main").makeChoice();
            switch (choice) {
                case 1:
                    new DeveloperMenuHandler(developerService, menuService,
                            companyService, projectService, skillService,  relationService).launch();
                    break;
                case 2:
                    new ProjectMenuHandler(projectService, menuService).launch();
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


    private static void initDatabaseAndMigrations() {
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
            skillStorage = new SkillStorage(manager);
            skillConverter = new SkillConverter();
            skillService = new SkillService(skillStorage, skillConverter);
            companyStorage = new CompanyStorage(manager);
            companyConverter = new CompanyConverter();
            companyService = new CompanyService(companyStorage, companyConverter);
            customerStorage = new CustomerStorage(manager);
            customerConverter = new CustomerConverter();
            customerService = new CustomerService(customerStorage, customerConverter);
            projectStorage = new ProjectStorage(manager, companyStorage, customerStorage);
            projectConverter = new ProjectConverter();
            projectService = new ProjectService(projectStorage, projectConverter,
                                           companyService, customerService);
            developerStorage = new DeveloperStorage(manager, companyStorage, skillStorage, projectStorage);
            developerConverter = new DeveloperConverter();
            developerService = new DeveloperService(developerStorage, developerConverter);
            relationStorage = new RelationStorage(manager);
            relationService = new RelationService(projectConverter, developerConverter, skillConverter, relationStorage);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
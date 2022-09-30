
import controller.*;
import model.config.DatabaseManagerConnector;
import model.config.Migration;
import model.config.PropertiesConfig;
import model.service.*;
import model.storage.*;

import java.sql.SQLException;
import java.util.Properties;

public class App {
    private static DatabaseManagerConnector manager;
    private static MenuService menuService;
    private static DeveloperStorage developerStorage;
    private static DeveloperService developerService;
    private static CompanyStorage companyStorage;
    private static CompanyService companyService;
    private static CustomerStorage customerStorage;
    private static CustomerService customerService;
    private static ProjectStorage projectStorage;
    private static ProjectService projectService;
    private static SkillService skillService;
    private  static SkillStorage skillStorage;
    private static RelationStorage relationStorage;
    private static RelationService relationService;
    private static final int EXIT_FROM_MAIN_MENU = 5;

    public static void main(String[] args) {
        initDatabaseAndMigrations();
        initAllServiceAndStorageClasses();
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
                    new CompanyMenuHandler(companyService, menuService, projectService, developerService).launch();
                    break;
                case 4:
                    new CustomerMenuHandler(customerService, projectService,
                            menuService,developerService).launch();
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

    private static void initAllServiceAndStorageClasses() {
        menuService = new MenuService();
        try {
            skillStorage = new SkillStorage(manager);
            skillService = new SkillService(skillStorage);
            relationStorage = new RelationStorage(manager);
            relationService = new RelationService(relationStorage);
            companyStorage = new CompanyStorage(manager);
            companyService = new CompanyService(companyStorage);
            customerStorage = new CustomerStorage(manager);
            customerService = new CustomerService(customerStorage);
            developerStorage = new DeveloperStorage(manager, companyStorage, skillStorage);
            projectStorage = new ProjectStorage(manager, companyStorage, customerStorage);
            projectService = new ProjectService(projectStorage, developerStorage,companyService,
                    customerService, relationService);
            developerService = new DeveloperService(developerStorage, projectService, projectStorage,
                    skillStorage, companyStorage, relationService, skillService);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
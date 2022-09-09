package controller;

import model.dao.CompanyDao;
import model.dao.CustomerDao;
import model.service.CompanyService;
import model.service.CustomerService;
import model.service.converter.CompanyConverter;
import model.service.converter.CustomerConverter;
import model.storage.CompanyStorage;
import model.storage.CustomerStorage;
import view.Output;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class CustomerMenuHandler {
    private CustomerService customerService;
    private CustomerStorage customerStorage;
    private CustomerConverter customerConverter;
    private MenuService menuService;
    private static final int EXIT_FROM_CUSTOMER_NENU = 4;

public CustomerMenuHandler(CustomerService customerService, CustomerStorage customerStorage,
                           CustomerConverter customerConverter, MenuService menuService) {
    this.customerService = customerService;
    this.customerStorage = customerStorage;
    this.customerConverter = customerConverter;
    this.menuService = menuService;
}

    public void launchCoreModule() {
        int choiceCustomers;
        do {
            menuService.get("Customers").printMenu();
            choiceCustomers = menuService.get("Customers").makeChoice();
            switch (choiceCustomers) {
                case 1:
                    getAllNames();
                    break;
                case 2:
                    // customerDaoService.addCustomer();
                    break;
                case 3:
                    System.out.print("Внесите название заказчика, которого вы хотите удалить :");
                    Scanner sc43 = new Scanner(System.in);
                    String nameInput3 = sc43.nextLine();
                               /* ArrayList<String> customerProjects = customerDaoService.getProjectsNames(nameInput3);
                                if (customerProjects.size() == 0) {
                                    customerDaoService.deleteCustomer(nameInput3);
                                    break;}
                                System.out.println("\tЭтот заказчик заказал следующие проекты:");
                                for (String project : customerDaoService.getProjectsNames(nameInput3)) {
                                    System.out.println("\t\t- " + project );
                                }
                                System.out.println("Для того, чтобы удалить этого заказчика - внесите ссответствующие изменения в таблицу projects");

                                */
                    break;
            }
        } while (choiceCustomers != EXIT_FROM_CUSTOMER_NENU);
    }
    public void getAllNames() {
        List<CustomerDao> customerDaoList = customerStorage.findAll();
        List<String> result = new ArrayList<>();
        for (CustomerDao customerDao : customerDaoList) {
            result.add(String.format("%d. %s, репутация -  %s",
                    customerDao.getCustomer_id(),
                    customerDao.getCustomer_name(),
                    customerDao.getReputation()));
        }
        Output.getInstance().print(result);
    }

}

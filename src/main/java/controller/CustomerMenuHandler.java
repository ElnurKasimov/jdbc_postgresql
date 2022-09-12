package controller;

import model.dao.CustomerDao;
import model.dto.CompanyDto;
import model.dto.CustomerDto;
import model.service.CustomerService;
import model.storage.CustomerStorage;
import view.Output;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class CustomerMenuHandler {
    private CustomerService customerService;
    private CustomerStorage customerStorage;
    private MenuService menuService;
    private static final int EXIT_FROM_CUSTOMER_MENU = 4;

public CustomerMenuHandler(CustomerService customerService, CustomerStorage customerStorage,
                            MenuService menuService) {
    this.customerService = customerService;
    this.customerStorage = customerStorage;
    this.menuService = menuService;
}

    public void launch() {
        int choiceCustomers;
        do {
            menuService.get("Customers").printMenu();
            choiceCustomers = menuService.get("Customers").makeChoice();
            switch (choiceCustomers) {
                case 1:
                    getAllNames();
                    break;
                case 2:
                    createCustomer();
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
        } while (choiceCustomers != EXIT_FROM_CUSTOMER_MENU);
    }
    public void getAllNames() {
        List<CustomerDao> customerDaoList = customerStorage.findAll();
        List<String> result = new ArrayList<>();
        for (CustomerDao customerDao : customerDaoList) {
            result.add(String.format("\t%d. %s, reputation -  %s",
                    customerDao.getCustomer_id(),
                    customerDao.getCustomer_name(),
                    customerDao.getReputation()));
        }
        Output.getInstance().print(result);
    }

    private  void createCustomer() {
        System.out.print("Enter customer name : ");
        Scanner sc = new Scanner(System.in);
        String newCustomerName = sc.nextLine();
        System.out.print("Enter customer reputation (respectable, trustworthy, insolvent) : ");
        String newCustomerReputation = sc.nextLine();
        CustomerDto newCustomerDto = new CustomerDto(newCustomerName, CustomerDto.Reputation.valueOf(newCustomerReputation));
        List<String> result = customerService.save(newCustomerDto);
        Output.getInstance().print(result);
    }
}

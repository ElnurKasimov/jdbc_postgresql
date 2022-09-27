package controller;

import model.dao.CustomerDao;
import model.dto.CompanyDto;
import model.dto.CustomerDto;
import model.dto.ProjectDto;
import model.service.CompanyService;
import model.service.CustomerService;
import model.service.DeveloperService;
import model.service.ProjectService;
import model.storage.CustomerStorage;
import view.Output;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class CustomerMenuHandler {
    private CustomerService customerService;
    private ProjectService projectService;
    private MenuService menuService;
    private DeveloperService developerService;
    private CompanyService companyService;
    private static final int EXIT_FROM_CUSTOMER_MENU = 4;

public CustomerMenuHandler(CustomerService customerService, ProjectService projectService,
                            MenuService menuService, DeveloperService developerService, CompanyService companyService) {
    this.customerService = customerService;
    this.projectService = projectService;
    this.menuService = menuService;
    this.developerService = developerService;
    this.companyService = companyService;
}

    public void launch() {
        int choiceCustomers;
        do {
            menuService.get("Customers").printMenu();
            choiceCustomers = menuService.get("Customers").makeChoice();
            switch (choiceCustomers) {
                case 1:
                    customerService.findAllCustomers();
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

    private void deleteCustomer() {
        System.out.print("Enter name of the customer to delete : ");
        Scanner sc = new Scanner(System.in);
        String name = null;
        while (true) {
            name = sc.nextLine();
            if (customerService.findByName(name).isEmpty()) {
                System.out.print("Unfortunately, there is no customer with such name in the database.  Please enter correct company name :");
            } else {
                break;
            }
        }
        List<ProjectDto> projectDtoList = projectService.getCustomerProjects(name);
        if (!projectDtoList.isEmpty()) {
            System.out.println("\tThe customer cannot be deleted because it ordered such projects with respectively involved developers :");
            projectDtoList.forEach(projectDto -> {
                System.out.println("\t " + projectDto.getProject_name() + " : ");
                developerService.getDevelopersNamesByProjectName(projectDto.getProject_name()).
                        forEach(developerName -> System.out.println("\t\t" + developerName));
            });
            System.out.println("For deleting the customer at first delete these developers and these projects in the appropriate sections of the database.");
        } else {
            companyService.deleteCompany(name);
        }
    }
}

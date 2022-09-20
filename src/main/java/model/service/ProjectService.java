package model.service;

import model.dao.ProjectDao;
import model.dto.CompanyDto;
import model.dto.CustomerDto;
import model.dto.ProjectDto;
import model.service.converter.ProjectConverter;
import model.storage.ProjectStorage;
import view.Output;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ProjectService {
    private ProjectStorage projectStorage;
    private ProjectConverter projectConverter;
    private CompanyService companyService;
    private CustomerService customerService;


public ProjectService (ProjectStorage projectStorage, ProjectConverter projectConverter,
                       CompanyService companyService, CustomerService customerService) {
    this.projectStorage = projectStorage;
    this.projectConverter = projectConverter;
    this.companyService = companyService;
    this.customerService = customerService;
}


    public List<String> getAllProjects() {
        List<Optional<ProjectDao>> projectDaoList = projectStorage.findAll();
        List<String> result = new ArrayList<>();
        for (Optional<ProjectDao> projectDao : projectDaoList) {
            projectDao.ifPresent(dao -> result.add(String.format("\t%d. %s, budget - %d, launched  %s",
                    dao.getProject_id(),
                    dao.getProject_name(),
                    dao.getCost(),
                    dao.getStart_date().toString())));
        }
        return result;
    }

    public List<ProjectDto> getCompanyProjects(String companyName) {
        List<ProjectDto> projects = new ArrayList<>();
        List<ProjectDao> projectDaoList = projectStorage.getCompanyProjects(companyName);
        for (ProjectDao projectDao : projectDaoList) {
            projects.add(projectConverter.from(projectDao));
        }
        return projects;
    };

    public long getIdByName (String name){
        return  projectStorage.getIdByName(name).orElseGet(() -> {
            System.out.print( "There is no project with such name. Please enter correct data");
            return (long)0;
        });
    }

    public ProjectDto checkByCompanyName(String companyName) {
        System.out.println("\tThis company develops such projects : ");
        List<ProjectDto> projectDtoList = getCompanyProjects(companyName);
        for (ProjectDto projectDto : projectDtoList) {
            System.out.print(projectDto.getProject_name() + ",\n");
        }
        if (projectDtoList.isEmpty()) {
            System.out.println("\nThere is no project in the company. Please create the one.");
            ProjectDto newProjectDto;
            do {
                newProjectDto = createProject();
                newProjectDto = save(newProjectDto);
            } while (newProjectDto.getProject_id() == 0 );
            System.out.println("Company " + companyName + " develops project " + newProjectDto.getProject_name());
        }
        ProjectDto selectedProject;
        String projectName;
        while(true) {
            System.out.print("\tPlease enter project name the developers participate in : ");
            Scanner sc = new Scanner(System.in);
            projectName = sc.nextLine();
            Optional<ProjectDao> projectDaoFromDb = projectStorage.findByName(projectName);
            if(projectDaoFromDb.isPresent()) {
                selectedProject =  projectConverter.from(projectDaoFromDb.get());
                break;
            } else {System.out.println("\tThere is no such project. Please enter correct data");}
        }
        return  selectedProject;
    }

    public ProjectDto createProject() {
        Scanner sc = new Scanner(System.in);
        System.out.print("\tEnter name of the project : ");
        String projectName = sc.nextLine();
        CompanyDto companyDto = null;
        CustomerDto customerDto = null;
        while (true) {
            System.out.print("\tEnter company name which develops the project : ");
            String company = sc.nextLine();
            if (companyService.findByName(company).isPresent()) {
               companyDto = companyService.findByName(company).get();
                break;
            } else {
                System.out.println("There is no company with such name. Please enter correct one.");
            }
        }
        while (true) {
            System.out.print("\tEnter customer name which ordered the development of the project: : ");
            String customer = sc.nextLine();
            if (customerService.findByName(customer).isPresent()) {
                customerDto = customerService.findByName(customer).get();
                break;
            } else {
                System.out.println("There is no customer with such name. Please enter correct one.");
            }
        }
        System.out.print("\tBudget of the project (only digits): ");
        int cost = Integer.parseInt(sc.nextLine());
        System.out.print("\tStart date of the project (in format yyyy-mm-dd): ");
        String startDate = sc.nextLine();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        LocalDate startLocalDate = LocalDate.parse(startDate, dtf);
        java.sql.Date startSqlDate = java.sql.Date.valueOf(startLocalDate);
        return new ProjectDto(projectName, companyDto, customerDto, cost, startSqlDate);
    }

    public ProjectDto save (ProjectDto projectDto) {
        List<String> stringList = new ArrayList<>();
        Optional<ProjectDao> projectFromDb =
                projectStorage.findByName(projectDto.getProject_name());
        ProjectDto result  = new ProjectDto();
        if (projectFromDb.isPresent()) {
            if (validateByName(projectDto, projectConverter.from(projectFromDb.get()))) {
                result = projectConverter.from(projectFromDb.get()); // with id
            } else {
               stringList.add(String.format("\tProject with name '%s ' already exist with different another data." +
                    " Please enter correct data", projectDto.getProject_name()));
               result = projectDto; // without id
            }
            } else {
            stringList.add("\tProject " + projectDto.getProject_name() + " successfully added to the database");
            result  = projectConverter.from(projectStorage.save(projectConverter.to(projectDto))); // with id
        }
        Output.getInstance().print(stringList);
        return result;
    }


    public boolean validateByName(ProjectDto projectDto, ProjectDto projectFromDb) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateFromProjectDto = dateFormat.format(projectDto.getStart_date());
        String dateFromProjectFromDb = dateFormat.format(projectFromDb.getStart_date());
        return  (projectDto.getCompanyDto().getCompany_name().equals(projectFromDb.getCompanyDto().getCompany_name())) &&
              (projectDto.getCustomerDto().getCustomer_name().equals(projectFromDb.getCustomerDto().getCustomer_name())) &&
               (projectDto.getCost() == projectFromDb.getCost() ) &&
               (dateFromProjectDto.equals(dateFromProjectFromDb)) ;
    }
}

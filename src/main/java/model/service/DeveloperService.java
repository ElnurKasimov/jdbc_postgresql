package model.service;

import model.dao.CompanyDao;
import model.dao.DeveloperDao;
import model.dao.ProjectDao;
import model.dto.CompanyDto;
import model.dto.DeveloperDto;
import model.dto.ProjectDto;
import model.dto.SkillDto;
import model.service.converter.CompanyConverter;
import model.service.converter.DeveloperConverter;
import model.storage.CompanyStorage;
import model.storage.DeveloperStorage;
import model.storage.ProjectStorage;
import model.storage.SkillStorage;
import view.Output;

import java.util.*;

public class DeveloperService {
    private DeveloperStorage developerStorage;
    private ProjectStorage projectStorage;
    private SkillStorage skillStorage;
    private CompanyStorage companyStorage;

public DeveloperService (DeveloperStorage developerStorage, ProjectStorage projectStorage,
                SkillStorage skillstorage, CompanyStorage companyStorage) {
    this.developerStorage = developerStorage;
    this.projectStorage = projectStorage;
    this.skillStorage = skillstorage;
    this.companyStorage = companyStorage;
}

    public DeveloperDto save (DeveloperDto developerDto) {
        List<String> result = new ArrayList<>();
        Optional<DeveloperDao> developerFromDb =
                developerStorage.findByName(developerDto.getLastName(), developerDto.getFirstName());
        DeveloperDao savedDeveloperWithId = new DeveloperDao();
        if(developerFromDb.isPresent()) {
            result.add(validateByName(developerDto, DeveloperConverter.from(developerFromDb.get())));
        } else {
            savedDeveloperWithId = developerStorage.save(DeveloperConverter.to(developerDto));
            result.add("\tDeveloper " + developerDto.getLastName() + " " +
                    developerDto.getFirstName() + " successfully added to the database");
        };
        Output.getInstance().print(result);
        return DeveloperConverter.from(savedDeveloperWithId);
    }

    public String validateByName(DeveloperDto developerDto, DeveloperDto developerFromDb) {
      if( (developerDto.getAge() == developerFromDb.getAge()) &&
          (developerDto.getCompanyDto().getCompany_name().equals(developerFromDb.getCompanyDto().getCompany_name() ) )
          && (developerDto.getSalary() == developerFromDb.getSalary()) ) {
            return "\tDeveloper " + developerDto.getLastName() + " " +
                    developerDto.getFirstName() + " successfully added to the database";
        } else return   String.format("\tDeveloper with name '%s %s ' already exist with different another data." +
                         " Please enter correct data", developerDto.getLastName(), developerDto.getFirstName());
    }

    public void findAllDevelopers() {
        List<String> result = new ArrayList<>();
        for (Optional<DeveloperDao> developerDao : developerStorage.findAll()) {
            developerDao.ifPresent(dao -> result.add(String.format("\t%d. %s %s, works in company %s",
                    dao.getDeveloper_id(),
                    dao.getLastName(),
                    dao.getFirstName(),
                    dao.getCompanyDao().getCompany_name())));
        }
        Output.getInstance().print(result);
    }

    public void getInfoByName(String lastName, String firstName) {
        List<String> result = new ArrayList<>();
        DeveloperDto developerDto = DeveloperConverter.from(developerStorage.findByName(lastName, firstName).get());
        result.add(String.format("\t\tDeveloper  %s %s  :", developerDto.getLastName(), developerDto.getFirstName()));
        result.add("\t\t\tAge : " + developerDto.getAge() + ",");
        result.add(String.format("\t\t\tWorks in company %s, with salary %d",
                             developerDto.getCompanyDto().getCompany_name(), developerDto.getSalary()));
        StringBuilder projectsName = new StringBuilder();
        projectsName.append("\t\t\tTakes participation in such projects :");
        List<String> projectsList = projectStorage.getProjectsNameByDeveloperId(developerDto.getDeveloper_id());
        for ( String project : projectsList) {
            projectsName.append(" " + project + ",");
        }
        projectsName.deleteCharAt(projectsName.length()-1);
        result.add(projectsName.toString());
        StringBuilder skillsName = new StringBuilder();
        skillsName.append("\t\t\tHas skill set :");
        List<String> skillsList =  skillStorage.getSkillSetByDeveloperId(developerDto.getDeveloper_id());
        for ( String skill : skillsList) {
            skillsName.append(" " + skill + ",");
        }
        skillsName.deleteCharAt(skillsName.length()-1);
        result.add(skillsName.toString());
        Output.getInstance().print(result);
    };

    public boolean isExist(String lastName, String firstName) {
        return developerStorage.isExist(lastName, firstName);
    }

    public void getQuantityJavaDevelopers() {
        List<String> result = new ArrayList<>();
        result.add(String.format("\t\tIn all projects participate %d Java-developers",
                developerStorage.getQuantityJavaDevelopers()));
        Output.getInstance().print(result);
    }

    public void getListNamesOfMiddleDevelopers() {
        List<String> result = new ArrayList<>();
        result.add("\tThere are such middle-developer in the database : ");
        developerStorage.getNamesListOfMiddleDevelopers().forEach(name -> result.add("\t\t" + name + ","));
        Output.getInstance().print(result);
    };

    public List<String> getDevelopersNamesByProjectName(String projectName) {
        return developerStorage.getDevelopersNamesByProjectName(projectName);
    }

    public void updateDeveloper() {
        System.out.println("\tEnter, please,  data for developer You want to update.");
        DeveloperDto developerDtoToUpdate;
        String lastName;
        String firstName;
        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.print("\tLast name : ");
            lastName = sc.nextLine();
            System.out.print("\tFirst name : ");
            firstName = sc.nextLine();
            Optional<DeveloperDao> currentDeveloperDao = developerStorage.findByName(lastName, firstName);
            if(currentDeveloperDao.isPresent()) {
                developerDtoToUpdate = DeveloperConverter.from(currentDeveloperDao.get());
                break;
            }
            System.out.println("There is no such developer in the database. Please enter correct data");
        }
        System.out.print("\tEnter new age (only digits) or just click 'Enter' if this field will not be changed : ");
        String newAgeString = sc.nextLine();
        int newAge;
        if(newAgeString.equals("")) {
            newAge = developerDtoToUpdate.getAge();
        } else {
            newAge = Integer.parseInt(newAgeString);
        }
        System.out.print("\tEnter new salary(only digits) or just click 'Enter' if this field will not be changed : ");
        String newSalaryString = sc.nextLine();
        int newSalary;
        if(newSalaryString.equals("")) {
            newSalary = developerDtoToUpdate.getSalary();
        } else {
            newSalary = Integer.parseInt(newSalaryString);
        }

        CompanyDto newCompany;
        System.out.print("\tEnter name of  new company where he works or just click 'Enter' if this field will not be changed: ");
        String companyName;
        
        while (true) {
            companyName = sc.nextLine();
            Optional<CompanyDao> companyDaoFromDB = companyStorage.findByName(companyName);
            if (companyDaoFromDB.isPresent()) {
               newCompany = CompanyConverter.from(companyDaoFromDB.get());
               break;
            }
            System.out.print("Unfortunately, there is no company with such name in the database.  Enter correct data or add such company to database.");
        }
        developerDtoToUpdate = new DeveloperDto(lastName, firstName, newAge, newCompany, newSalary);
        developerStorage.update(DeveloperConverter.to(developerDtoToUpdate));





        System.out.print("\tEnter name of  new project the developer participate  or just click 'Enter' if this field will not be changed: ");
        String newProjectName;
        long newProjectId;
        while (true) {
            newProjectName = sc.nextLine();
            Optional<ProjectDao> projectDaoFromDB = projectStorage.findByName(newProjectName);
            if (projectDaoFromDB.isPresent()) {
                newProjectId = projectDaoFromDB.get().getProject_id();
                break;
            }
            System.out.print("Unfortunately, there is no project with such name in the database.  Enter correct data or add such project to database.");
        }


       long developerDtoToUpdateId =  developerStorage.getIdByName(lastName, firstName);



        //todo
        long projectIdToDelete = projectStorage.
        relationStorage.deleteProjectDeveloperRelation(developerDtoToUpdateId, projectIdToDelete);
        relationStorage.saveProjectDeveloperRelation(developerDtoToUpdateId, newProjectId);


        Set<SkillDto> skillsDto = new HashSet<>();
        while (true) {
            System.out.print("\tLanguage the developer operated  : ");
            String language = sc.nextLine();
            System.out.print("\tLevel knowledge of the language (junior, middle, senior) : ");
            String level = sc.nextLine();
            SkillDto skillDto = skillService.findByLanguageAndLevel(language, level);
            skillsDto.add(skillDto);
            System.out.print("One more language? (yes/no) : ");
            String anotherLanguage = sc.nextLine();
            if (anotherLanguage.equalsIgnoreCase("no")) break;
        }


        newDeveloperDto = developerService.save(newDeveloperDto);

        relationService.saveDeveloperSkillRelation(newDeveloperDto, skillsDto);
    }

}


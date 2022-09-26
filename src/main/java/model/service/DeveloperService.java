package model.service;

import model.dao.DeveloperDao;
import model.dto.DeveloperDto;
import model.service.converter.DeveloperConverter;
import model.storage.DeveloperStorage;
import model.storage.ProjectStorage;
import model.storage.SkillStorage;
import view.Output;

import java.util.*;

public class DeveloperService {
    private DeveloperStorage developerStorage;
    private ProjectStorage projectStorage;
    private SkillStorage skillStorage;

public DeveloperService (DeveloperStorage developerStorage, ProjectStorage projectStorage,
                SkillStorage skillstorage) {
    this.developerStorage = developerStorage;
    this.projectStorage = projectStorage;
    this.skillStorage = skillstorage;
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

    public Set<DeveloperDto> getQuantityJavaDevelopers() {
        Set<DeveloperDto> developerDtoSet = new HashSet<>();
        return developerDtoSet;
    }
}


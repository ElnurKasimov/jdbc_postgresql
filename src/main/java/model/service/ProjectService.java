package model.service;

import model.dao.ProjectDao;
import model.dto.DeveloperDto;
import model.dto.ProjectDto;
import model.service.converter.DeveloperConverter;
import model.service.converter.ProjectConverter;
import model.service.converter.ProjectConverterIdName;
import model.storage.CompanyStorage;
import model.storage.ProjectStorage;

import java.util.ArrayList;
import java.util.List;

public class ProjectService {
    private ProjectStorage projectStorage;
    private ProjectConverter projectConverter;
    private ProjectConverterIdName projectConverterIdName;
    private DeveloperConverter developerConverter;


public ProjectService (ProjectStorage projectStorage, ProjectConverter projectConverter,
                       DeveloperConverter developerConverter) {
    this.projectStorage = projectStorage;
    this.projectConverter = projectConverter;
    this.developerConverter = developerConverter;
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
        long id = projectStorage.getIdByName(name).orElseGet(() -> {
            System.out.print( "There is no project with such name. Please enter correct data");
            return (long)0;
        });
        return id;
    }

    public void saveProjectDeveloperRelation(ProjectDto projectDto, DeveloperDto developerDto) {
        projectStorage.saveProjectDeveloperRelation(
                projectConverter.to(projectDto), developerConverter.to(developerDto));

    };

}

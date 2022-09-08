package controller;

import model.dao.CompanyDao;
import model.dao.ProjectDao;
import model.service.CompanyService;
import model.service.ProjectService;
import model.service.converter.CompanyConverter;
import model.service.converter.ProjectConverter;
import model.storage.CompanyStorage;
import model.storage.ProjectStorage;
import view.Output;

import java.util.ArrayList;
import java.util.List;


public class ProjectMenuHandler {
    private ProjectService projectService;
    private ProjectStorage projectStorage;
    private ProjectConverter projectConverter;

public ProjectMenuHandler(ProjectService projectService, ProjectStorage projectStorage,ProjectConverter projectConverter) {
    this.projectService = projectService;
    this.projectStorage = projectStorage;
    this.projectConverter = projectConverter;
}


    public void getAllNames() {
        List<ProjectDao> projectDaoList = projectStorage.findAll();
        List<String> result = new ArrayList<>();
        for (ProjectDao projectDao : projectDaoList) {
            result.add(String.format("%d. %s, бюджет - %d, запущен %s",
                    projectDao.getProject_id(),
                    projectDao.getProject_name(),
                    projectDao.getCost(),
                    projectDao.getStart_date().toString()));
        }
        Output.getInstance().print(result);
    }

}

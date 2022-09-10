package model.service;

import model.service.converter.ProjectConverter;
import model.storage.ProjectStorage;

public class ProjectService {
    private ProjectStorage projectStorage;
    private ProjectConverter projectConverter;

public ProjectService (ProjectStorage projectStorage, ProjectConverter projectConverter) {
    this.projectStorage = projectStorage;
    this.projectConverter = projectConverter;
}




}

package model.service;

import model.dao.DeveloperDao;
import model.dto.DeveloperDto;
import model.dto.ProjectDto;
import model.dto.SkillDto;
import model.service.converter.CompanyConverter;
import model.service.converter.DeveloperConverter;
import model.service.converter.ProjectConverter;
import model.service.converter.SkillConverter;
import model.storage.RelationStorage;

public class RelationService {
    private ProjectConverter projectConverter;
    private DeveloperConverter developerConverter;
    private SkillConverter skillConverter;
    private RelationStorage relationStorage;

public RelationService(ProjectConverter projectConverter, DeveloperConverter developerConverter,
                       SkillConverter skillConverter, RelationStorage relationStorage) {
    this.projectConverter = projectConverter;
    this.developerConverter = developerConverter;
    this.skillConverter = skillConverter;
    this.relationStorage = relationStorage;
}

public void saveProjectDeveloperRelation (ProjectDto projectDto, DeveloperDto developerDto) {
    relationStorage.saveProjectDeveloperRelation(
        projectConverter.to(projectDto), developerConverter.to(developerDto));
    }

    public void saveDeveloperSkillRelation (DeveloperDto developerDto, SkillDto skillDto) {
        relationStorage.saveDeveloperSkillRelation(
                developerConverter.to(developerDto), skillConverter.to(skillDto));
    }

}

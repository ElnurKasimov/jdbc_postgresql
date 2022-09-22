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

import java.util.Set;
import java.util.stream.Collectors;


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

    public void saveProjectDeveloperRelation(Set<ProjectDto> projectsDto, DeveloperDto developerDto) {
        relationStorage.saveProjectDeveloperRelation(
                projectsDto.stream().map(projectConverter::to).collect(Collectors.toSet()),
                developerConverter.to(developerDto)
        );
    };

    public void saveDeveloperSkillRelation (DeveloperDto developerDto, Set<SkillDto> skillsDto) {
        relationStorage.saveDeveloperSkillRelation(
                developerConverter.to(developerDto),
                skillsDto.stream().map(skillConverter::to).collect(Collectors.toSet())
        );
    }

}

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
    private RelationStorage relationStorage;

public RelationService(RelationStorage relationStorage) {
    this.relationStorage = relationStorage;
}

    public void saveProjectDeveloperRelation(Set<ProjectDto> projectsDto, DeveloperDto developerDto) {
        relationStorage.saveProjectDeveloperRelation(
                projectsDto.stream().map(ProjectConverter::to).collect(Collectors.toSet()),
                DeveloperConverter.to(developerDto)
        );
    };

    public void saveDeveloperSkillRelation (DeveloperDto developerDto, Set<SkillDto> skillsDto) {
        relationStorage.saveDeveloperSkillRelation(
                DeveloperConverter.to(developerDto),
                skillsDto.stream().map(SkillConverter::to).collect(Collectors.toSet())
        );
    }

}

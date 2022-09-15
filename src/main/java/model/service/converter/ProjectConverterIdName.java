package model.service.converter;

import model.dao.ProjectDao;
import model.dto.ProjectDto;

public class ProjectConverterIdName implements Converter<ProjectDto, ProjectDao>{

    @Override
    public ProjectDto from(ProjectDao entity) {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setProject_id(entity.getProject_id());
        projectDto.setProject_name(entity.getProject_name());
        return projectDto;
    }

    @Override
    public ProjectDao to(ProjectDto entity) {
        ProjectDao projectDao = new ProjectDao();
        projectDao.setProject_id(entity.getProject_id());
        projectDao.setProject_name(entity.getProject_name());
        return projectDao;
    }
}


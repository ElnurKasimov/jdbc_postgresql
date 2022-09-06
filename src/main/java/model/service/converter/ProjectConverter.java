package model.service.converter;

import model.dao.ProjectDao;
import model.dto.ProjectDto;

public class ProjectConverter implements Converter<ProjectDto, ProjectDao>{

    @Override
    public ProjectDto from(ProjectDao entity) {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setProject_id(entity.getProject_id());
        projectDto.setProject_name(entity.getProject_name());
        projectDto.setCompany_id(entity.getCompany_id());
        projectDto.setCustomer_id(entity.getCustomer_id());
        projectDto.setCost(entity.getCost());
        projectDto.setStart_date(entity.getStart_date());
        return projectDto;
    }

    @Override
    public ProjectDao to(ProjectDto entity) {
        ProjectDao projectDao = new ProjectDao();
        projectDao.setProject_id(entity.getProject_id());
        projectDao.setProject_name(entity.getProject_name());
        projectDao.setCompany_id(entity.getCompany_id());
        projectDao.setCustomer_id(entity.getCustomer_id());
        projectDao.setCost(entity.getCost());
        projectDao.setStart_date(entity.getStart_date());
        return projectDao;
    }
}


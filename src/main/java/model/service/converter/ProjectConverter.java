package model.service.converter;

import model.dao.ProjectDao;
import model.dto.ProjectDto;
import java.util.stream.Collectors;

public class ProjectConverter{
/*
    CompanyConverter companyConverter = new CompanyConverter();
    CustomerConverter customerConverter = new CustomerConverter();
    DeveloperConverter developerConverter = new DeveloperConverter();

 */

//@Override
    public static ProjectDto from(ProjectDao entity) {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setProject_id(entity.getProject_id());
        projectDto.setProject_name(entity.getProject_name());
        projectDto.setCompanyDto(CompanyConverter.from(entity.getCompanyDao()));
        projectDto.setCustomerDto(CustomerConverter.from(entity.getCustomerDao()));
        projectDto.setCost(entity.getCost());
        projectDto.setStart_date(entity.getStart_date());
        projectDto.setDevelopersDto(entity.getDevelopersDao().stream().map(developerDao -> DeveloperConverter.from(developerDao)).collect(Collectors.toSet()));
        return projectDto;
    }

    //@Override
    public static ProjectDao to(ProjectDto entity) {
        ProjectDao projectDao = new ProjectDao();
        projectDao.setProject_id(entity.getProject_id());
        projectDao.setProject_name(entity.getProject_name());
        projectDao.setCompanyDao(CompanyConverter.to(entity.getCompanyDto()));
        projectDao.setCustomerDao(CustomerConverter.to(entity.getCustomerDto()));
        projectDao.setCost(entity.getCost());
        projectDao.setStart_date(entity.getStart_date());
        projectDao.setDevelopersDao(entity.getDevelopersDto().stream().map(developerDto -> DeveloperConverter.to(developerDto)).collect(Collectors.toSet()));
        return projectDao;
    }
}


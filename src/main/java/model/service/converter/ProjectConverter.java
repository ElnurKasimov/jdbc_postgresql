package model.service.converter;

import model.dao.ProjectDao;
import model.dto.ProjectDto;
import java.util.stream.Collectors;

public class ProjectConverter implements Converter<ProjectDto, ProjectDao>{

    CompanyConverter companyConverter = new CompanyConverter();
    CustomerConverter customerConverter = new CustomerConverter();
    DeveloperConverter developerConverter = new DeveloperConverter();

@Override
    public ProjectDto from(ProjectDao entity) {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setProject_id(entity.getProject_id());
        projectDto.setProject_name(entity.getProject_name());
        projectDto.setCompanyDto(companyConverter.from(entity.getCompanyDao()));
        projectDto.setCustomerDto(customerConverter.from(entity.getCustomerDao()));
        projectDto.setCost(entity.getCost());
        projectDto.setStart_date(entity.getStart_date());
        projectDto.setDevelopersDto(entity.getDevelopersDao().stream().map(developerConverter::from).collect(Collectors.toSet()));
        return projectDto;
    }

    @Override
    public ProjectDao to(ProjectDto entity) {
        ProjectDao projectDao = new ProjectDao();
        projectDao.setProject_id(entity.getProject_id());
        projectDao.setProject_name(entity.getProject_name());
        projectDao.setCompanyDao(companyConverter.to(entity.getCompanyDto()));
        projectDao.setCustomerDao(customerConverter.to(entity.getCustomerDto()));
        projectDao.setCost(entity.getCost());
        projectDao.setStart_date(entity.getStart_date());
        projectDao.setDevelopersDao(entity.getDevelopersDto().stream().map(developerConverter::to).collect(Collectors.toSet()));
        return projectDao;
    }
}


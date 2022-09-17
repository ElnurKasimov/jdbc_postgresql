package model.service.converter;

import model.dao.ProjectDao;
import model.dto.ProjectDto;

public class ProjectConverter implements Converter<ProjectDto, ProjectDao>{
    private CompanyConverter companyConverter;
    private CustomerConverter customerConverter;

public ProjectConverter(CompanyConverter companyConverter, CustomerConverter customerConverter) {
    this.companyConverter = companyConverter;
    this.customerConverter = customerConverter;
}

    public ProjectConverter() {
    }


@Override
    public ProjectDto from(ProjectDao entity) {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setProject_id(entity.getProject_id());
        projectDto.setProject_name(entity.getProject_name());
        projectDto.setCompanyDto(companyConverter.from(entity.getCompanyDao()));
        projectDto.setCustomerDto(customerConverter.from(entity.getCustomerDao()));
        projectDto.setCost(entity.getCost());
        projectDto.setStart_date(entity.getStart_date());
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
        return projectDao;
    }
}


package model.service.converter;

import model.dao.DeveloperDao;
import model.dto.DeveloperDto;

import java.util.stream.Collectors;

public class DeveloperConverter implements Converter<DeveloperDto, DeveloperDao>{
    private  CompanyConverter companyConverter;
    private  ProjectConverter projectConverter;
    private SkillConverter skillConverter;

public DeveloperConverter(CompanyConverter companyConverter, ProjectConverter projectConverter, SkillConverter skillConverter) {
    this.companyConverter = companyConverter;
    this.projectConverter = projectConverter;
    this.skillConverter = skillConverter;
}
    @Override
    public DeveloperDto from(DeveloperDao entity) {
        DeveloperDto developerDto = new DeveloperDto();
        developerDto.setDeveloper_id(entity.getDeveloper_id());
        developerDto.setLastName(entity.getLastName());
        developerDto.setFirstName(entity.getFirstName());
        developerDto.setAge(entity.getAge());
        developerDto.setCompanyDto(companyConverter.from(entity.getCompanyDao()));
        developerDto.setSalary(entity.getSalary());
        developerDto.setSkills(entity.getSkills().stream().map(skillConverter::from).collect(Collectors.toSet()));
        developerDto.setProjectDto(projectConverter.from(entity.getProjectDao()));
        return developerDto;
    }

    @Override
    public DeveloperDao to(DeveloperDto entity) {
        DeveloperDao developerDao = new DeveloperDao();
        developerDao.setDeveloper_id(entity.getDeveloper_id());
        developerDao.setLastName(entity.getLastName());
        developerDao.setFirstName(entity.getFirstName());
        developerDao.setAge(entity.getAge());
        developerDao.setCompanyDao(companyConverter.to(entity.getCompanyDto()));
        developerDao.setSalary(entity.getSalary());
        developerDao.setSkills(entity.getSkills().stream().map(skillConverter::to).collect(Collectors.toSet()));
        developerDao.setProjectDao(projectConverter.to(entity.getProjectDto()));
        return developerDao;
    }
}


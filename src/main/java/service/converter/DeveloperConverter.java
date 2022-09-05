package service.converter;

import model.dao.DeveloperDao;
import model.dto.DeveloperDto;

public class DeveloperConverter implements Converter<DeveloperDto, DeveloperDao>{

    @Override
    public DeveloperDto from(DeveloperDao entity) {
        DeveloperDto developerDto = new DeveloperDto();
        developerDto.setDeveloper_id(entity.getDeveloper_id());
        developerDto.setLastName(entity.getLastName());
        developerDto.setFirstName(entity.getFirstName());
        developerDto.setAge(entity.getAge());
        developerDto.setCompany_id(entity.getCompany_id());
        developerDto.setSalary(entity.getSalary());
        return developerDto;
    }

    @Override
    public DeveloperDao to(DeveloperDto entity) {
        DeveloperDao developerDao = new DeveloperDao();
        developerDao.setDeveloper_id(entity.getDeveloper_id());
        developerDao.setLastName(entity.getLastName());
        developerDao.setFirstName(entity.getFirstName());
        developerDao.setAge(entity.getAge());
        developerDao.setCompany_id(entity.getCompany_id());
        developerDao.setSalary(entity.getSalary());
        return developerDao;
    }
}


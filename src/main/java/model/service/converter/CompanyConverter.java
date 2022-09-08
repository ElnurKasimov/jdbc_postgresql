package model.service.converter;

import model.dao.CompanyDao;
import model.dto.CompanyDto;

public class CompanyConverter implements Converter<CompanyDto, CompanyDao>{

    @Override
    public CompanyDto from(CompanyDao entity) {
        CompanyDto companyDto = new CompanyDto();
        companyDto.setCompany_id(entity.getCompany_id());
        companyDto.setCompany_name(entity.getCompany_name());
        companyDto.setRating(CompanyDto.Rating.valueOf(entity.getRating().toString()));
        return companyDto;
    }

    @Override
    public CompanyDao to(CompanyDto entity) {
        CompanyDao companyDao = new CompanyDao();
        companyDao.setCompany_id(entity.getCompany_id());
        companyDao.setCompany_name(entity.getCompany_name());
        companyDao.setRating(CompanyDao.Rating.valueOf(entity.getRating().toString()));
        return companyDao;
    }
}


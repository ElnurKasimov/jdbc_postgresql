package model.service.converter;

import model.dao.CompanyDao;
import model.dao.SkillDao;
import model.dto.CompanyDto;
import model.dto.SkillDto;

public class SkillConverter implements Converter<SkillDto, SkillDao>{

    @Override
    public SkillDto from(SkillDao entity) {
        SkillDto  skillDto = new SkillDto();
        skillDto.setSkill_id(entity.getSkill_id());
        skillDto.setLanguage(entity.getLanguage());
        skillDto.setLevel(entity.getLevel());
        return skillDto;
    }

    @Override
    public SkillDao to(SkillDto entity) {
        SkillDao  skillDao = new SkillDao();
        skillDao.setSkill_id(entity.getSkill_id());
        skillDao.setLanguage(entity.getLanguage());
        skillDao.setLevel(entity.getLevel());
        return skillDao;
    }
}


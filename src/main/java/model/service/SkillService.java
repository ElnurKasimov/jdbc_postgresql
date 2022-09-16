package model.service;

import model.dao.CompanyDao;
import model.dao.ProjectDao;
import model.dto.CompanyDto;
import model.dto.DeveloperDto;
import model.dto.ProjectDto;
import model.dto.SkillDto;
import model.service.converter.DeveloperConverter;
import model.service.converter.ProjectConverter;
import model.service.converter.ProjectConverterIdName;
import model.service.converter.SkillConverter;
import model.storage.ProjectStorage;
import model.storage.SkillStorage;
import view.Output;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SkillService {
    private SkillStorage skillStorage;
    private SkillConverter skillConverter;


public SkillService(SkillStorage skillStorage) {
    this.skillStorage = skillStorage;
}

    public long getIdSkillByLanguageAndLevel(String language, String level) {
        Optional<Long> id = skillStorage.getIdSkillByLanguageAndLevel(language, level);
        SkillDto skillDto = new SkillDto(language, level);
        return id.orElseGet(() -> save(skillDto).getSkill_id());
    };

    public SkillDto save (SkillDto skillDto) {
        List<String> result = new ArrayList<>();
        skillDto = skillConverter.from(skillStorage.save(skillConverter.to(skillDto)));
        result.add(String.format("\tSkill %s - %s successfully added to the database",
                skillDto.getLanguage(), skillDto.getLevel()));
        Output.getInstance().print(result);
        return skillDto;
    }

    public SkillDto findById(long)
}

package model.service;

import model.dao.ProjectDao;
import model.dto.DeveloperDto;
import model.dto.ProjectDto;
import model.service.converter.DeveloperConverter;
import model.service.converter.ProjectConverter;
import model.service.converter.ProjectConverterIdName;
import model.service.converter.SkillConverter;
import model.storage.ProjectStorage;
import model.storage.SkillStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SkillService {
    private SkillStorage skillStorage;


public SkillService(SkillStorage skillStorage) {
    this.skillStorage = skillStorage;
}

    public long getIdSkillByLanguageAndLevel(String language, String level) {
        Optional<Long> id = skillStorage.getIdSkillByLanguageAndLevel(language, level);



        return id.get();
    };

}

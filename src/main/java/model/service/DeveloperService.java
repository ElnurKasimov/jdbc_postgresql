package model.service;

import lombok.Data;
import model.service.converter.DeveloperConverter;
import model.storage.DeveloperStorage;

import java.util.ArrayList;
import java.util.List;


public class DeveloperService {
    private DeveloperStorage developerStorage;
    private DeveloperConverter developerConverter;


public DeveloperService (DeveloperStorage developerStorage, DeveloperConverter developerConverter) {
    this.developerStorage = developerStorage;
    this.developerConverter = developerConverter;
}



    public List<String> getAllNames() {
        List<String> result = new ArrayList<>();



    return result;
    }

}


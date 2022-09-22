package model.dao;

import lombok.Data;
import model.dto.SkillDto;

import java.util.Set;

@Data
public class DeveloperDao {
    private long developer_id;
    private String lastName;
    private String firstName;
    private int age;
    private CompanyDao companyDao;
    private int salary;
    private Set<SkillDao> skills;
    private Set<ProjectDao> projectsDao;

    public DeveloperDao (String lastName, String firstName, int age, CompanyDao companyDao, int salary,
                         Set<SkillDao> skills, Set<ProjectDao> projectsDao) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.age = age;
        this.companyDao = companyDao;
        this.salary=salary;
        this.skills = skills;
        this.projectsDao = projectsDao;
    }

     public DeveloperDao () {
     }

}


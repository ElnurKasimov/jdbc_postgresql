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
    private long company_id;
    private int salary;
    private Set<SkillDao> skills;

    public DeveloperDao (String lastName, String firstName, int age, long company_id, int salary, Set<SkillDao>  skills) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.age = age;
        this.company_id=company_id;
        this.salary=salary;
        this.skills = skills;
    }

     public DeveloperDao () {
     }

}


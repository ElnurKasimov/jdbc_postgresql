package model.dto;

import lombok.Data;

import java.util.Set;

@Data
public class DeveloperDto {
    private long developer_id;
    private String lastName;
    private String firstName;
    private int age;
    private long company_id;
    private  int salary;
    private Set<SkillDto> skills;
    private ProjectDto projectDto;

    public DeveloperDto(String lastName, String firstName, int age, long company_id, int salary, Set<SkillDto> skills, ProjectDto projectDto) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.age = age;
        this.company_id = company_id;
        this.salary = salary;
        this.skills = skills;
        this.projectDto = projectDto;
    }

    public DeveloperDto(String lastName, String firstName, int age, long company_id, int salary, Set<SkillDto> skills) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.age = age;
        this.company_id = company_id;
        this.salary = salary;
        this.skills = skills;
    }

    public DeveloperDto(String lastName, String firstName, int age, long company_id, int salary) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.age = age;
        this.company_id = company_id;
        this.salary = salary;
    }

     public DeveloperDto() {
     }

}


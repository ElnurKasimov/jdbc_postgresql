package model.dto;

import lombok.Data;

import java.util.Set;

@Data
public class DeveloperDto {
    private long developer_id;
    private String lastName;
    private String firstName;
    private int age;
    private CompanyDto companyDto;
    private  int salary;
    private Set<SkillDto> skills;
    private Set<ProjectDto> projectsDto;

    public DeveloperDto(String lastName, String firstName, int age, CompanyDto companyDto, int salary, Set<SkillDto> skills,
                        Set<ProjectDto> projectsDto) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.age = age;
        this.companyDto = companyDto;
        this.salary = salary;
        this.skills = skills;
        this.projectsDto = projectsDto;
    }

    public DeveloperDto(String lastName, String firstName, int age, CompanyDto companyDto, int salary, Set<SkillDto> skills) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.age = age;
        this.companyDto = companyDto;
        this.salary = salary;
        this.skills = skills;
    }

    public DeveloperDto(String lastName, String firstName, int age, CompanyDto companyDto, int salary) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.age = age;
        this.companyDto = companyDto;
        this.salary = salary;
    }

     public DeveloperDto() {
     }

}


package model.dto;

import lombok.Data;

@Data
public class DeveloperDto {
    private long developer_id;
    private String lastName;
    private String firstName;
    private int age;
    private long company_id;
    private  int salary;

    public DeveloperDto(String lastName, String firstName, int age, long company_id, int salary) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.age = age;
        this.company_id=company_id;
        this.salary=salary;
    }

     public DeveloperDto() {
     }

}


package model.dao;

import lombok.Data;
import model.dto.DeveloperDto;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Set;

@Data
public class ProjectDao {
    private long project_id;
    private String project_name;
    private CompanyDao companyDao;
    private CustomerDao customerDao;
    private  int cost;
    private Date start_date;

    public ProjectDao (String project_name,CompanyDao companyDao, CustomerDao customerDao, int cost,
                       Date start_date) {
        this.project_name = project_name;
        this.companyDao = companyDao;
        this.customerDao = customerDao;
        this.cost = cost;
        this.start_date = start_date;
    }

    public ProjectDao () {
    }

}


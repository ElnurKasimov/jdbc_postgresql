package model.dao;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ProjectDao {
    private long project_id;
    private String project_name;
    private long company_id;
    private long customer_id;
    public int cost;
    private LocalDate start_date;

    public ProjectDao (String project_name,long company_id, long customer_id, int cost, LocalDate start_date) {
        this.project_name=project_name;
        this.company_id=company_id;
        this.customer_id=customer_id;
        this.cost=cost;
        this.start_date=start_date;
    }

    public ProjectDao () {
    }

}


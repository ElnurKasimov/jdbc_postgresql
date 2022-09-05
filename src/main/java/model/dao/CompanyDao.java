package model.dao;

import lombok.Data;
import model.dto.CompanyDto;

@Data
public class CompanyDao {
    private long company_id;
    private String company_name;
    private CompanyDto.Rating rating;

    public enum Rating {
        high,
        middle,
        low
    }

    public CompanyDao (String company_name, CompanyDto.Rating rating) {
        this.company_name=company_name;
        this.rating=rating;
    }

    public CompanyDao () {
    }

}


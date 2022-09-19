package model.dao;

import lombok.Data;

import java.util.Set;

@Data
public class ProjectDeveloperDao {
    private  long id;
    private long project_id;
    private long developer_id;

     public ProjectDeveloperDao() {
     }

}


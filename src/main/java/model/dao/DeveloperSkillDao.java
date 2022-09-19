package model.dao;

import lombok.Data;

@Data
public class DeveloperSkillDao {
    private  long id;
    private long developer_id;
    private long skill_id;

     public DeveloperSkillDao() {
     }

}


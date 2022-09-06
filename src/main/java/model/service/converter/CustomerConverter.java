package model.service.converter;

import model.dao.CustomerDao;
import model.dto.CustomerDto;

public class CustomerConverter implements Converter<CustomerDto, CustomerDao>{

    @Override
    public CustomerDto from(CustomerDao entity) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCustomer_id(entity.getCustomer_id());
        customerDto.setCustomer_name(entity.getCustomer_name());
        customerDto.setReputation(entity.getReputation());
        return customerDto;
    }

    @Override
    public CustomerDao to(CustomerDto entity) {
        CustomerDao customerDao = new CustomerDao();
        customerDao.setCustomer_id(entity.getCustomer_id());
        customerDao.setCustomer_name(entity.getCustomer_name());
        customerDao.setReputation(entity.getReputation());
        return customerDao;
    }
}


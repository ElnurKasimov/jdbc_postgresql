package model.service;

import model.dao.CustomerDao;
import model.dto.CustomerDto;
import model.service.converter.CustomerConverter;
import model.storage.CustomerStorage;
import view.Output;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerService {
    private CustomerStorage customerStorage;



public  CustomerService (CustomerStorage customerStorage) {
    this.customerStorage = customerStorage;
}


    public List<String> save (CustomerDto customerDto) {
        List<String> result = new ArrayList<>();
        Optional<CustomerDao> customerFromDb = customerStorage.findByName(customerDto.getCustomer_name());
        if (customerFromDb.isPresent()) {
            result.add(validateByName(customerDto, CustomerConverter.from(customerFromDb.get())));
        } else {
            customerStorage.save(CustomerConverter.to(customerDto));
            result.add("\tCustomer successfully added to the database");
        };
        return result;
    }

    public String  validateByName(CustomerDto customerDto, CustomerDto customerFromDb) {
        if (!customerDto.getReputation().toString().equals(customerFromDb.getReputation().toString())) {
            return String.format("\tCustomer with name '%s' already exist with different " +
                            "reputation '%s'. Please enter correct data",
                    customerDto.getCustomer_name(), customerFromDb.getReputation().toString());
        } else return "\tCustomer successfully added to the database";
    }

    public Optional<CustomerDto> findByName(String name) {
        Optional<CustomerDao> customerDaoFromDb = customerStorage.findByName(name);
        return customerDaoFromDb.map(customerDao -> CustomerConverter.from(customerDao));
    }

    public void findAllCustomers() {
        List<String> result = new ArrayList<>();
        for (Optional<CustomerDao> customerDao : customerStorage.findAll()) {
            customerDao.ifPresent(dao -> result.add(String.format("\t%d. %s, rating -  %s",
                    dao.getCustomer_id(),
                    dao.getCustomer_name(),
                    dao.getReputation())));
        }
        Output.getInstance().print(result);
    }

}

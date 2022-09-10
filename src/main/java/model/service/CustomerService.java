package model.service;

import lombok.Data;
import model.service.converter.CustomerConverter;
import model.storage.CustomerStorage;


public class CustomerService {
    private CustomerStorage customerStorage;
    private CustomerConverter customerConverter;


public  CustomerService (CustomerStorage customerStorage, CustomerConverter customerConverter) {
    this.customerStorage = customerStorage;
    this.customerConverter = customerConverter;
}










}

package com.codewithyash.accounts.service;

import com.codewithyash.accounts.dto.CustomerDto;

public interface IAccountService {

    /**
     * This methods take the Dto objects and make the account for the Customer
     * @param customerDto customerDto
     */
    void createAccount(CustomerDto customerDto);


    /**
     * This method return the Customer Account based on mobile Number
     * @param mobileNumber Customer Mobile Number
     */
    CustomerDto fetchAccount(String mobileNumber);

    /**
     * @param customerDto Customer object
     * @return return updated status
     */
    boolean updateAccount(CustomerDto customerDto);

    /**
     * @param mobileNumber customer to be deleted
     */
    boolean deleteAccount(String mobileNumber);


}

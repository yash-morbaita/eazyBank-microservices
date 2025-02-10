package com.codewithyash.message.dto;


/**Record make Record final and generate getter and setters. We wont get the getter and setter with get but with name.
 * @param accountNumber
 * @param email
 * @param mobileNumber
 */
public record AccountsMsgDto(Long accountNumber,String name, String email, String mobileNumber) {
}

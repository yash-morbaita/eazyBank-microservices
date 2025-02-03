package com.codewithyash.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "Customer and Account Information",
        description = "Schema which holds the customer, account, cards, loans information"
)
public class CustomerDetailsDto {

    @NotEmpty(message = "Name cannot be Empty")
    @Size(min = 5, max = 50, message = "Name must be within 5 to 30 letters")
    @Schema(
            description = "Name of the Customer",
            example = "Yash Morbita"
    )
    private String name;

    @NotEmpty(message = "Email cannot be Empty")
    @Email(message = "Email address is not valid")
    @Schema(
            description = "Email of the Customer"
    )
    private String email;

    @Pattern(regexp = "($|[0-9]{10})", message = "Mobile number must be 10 digits")
    @NotEmpty(message = "Name cannot be Empty")
    @Schema(
            description = "Mobile Number of the Customer"
    )
    private String mobileNumber;

    @Schema(
            description = "Accounts Details of the Customer"
    )
    private AccountsDto accountsDto;
    @Schema(
            description = "Loans Details of the Customer"
    )
    private LoansDto loansDto;

    @Schema(
            description = "Cards Details of the Customer"
    )
    private CardsDto cardsDto;
}

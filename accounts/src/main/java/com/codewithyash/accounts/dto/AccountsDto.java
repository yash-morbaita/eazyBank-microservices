package com.codewithyash.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(
        name = "Account Information",
        description = "Schema which holds the account information"
)
public class AccountsDto {
    @NotEmpty(message = "Account number cannot be Empty")
    @Pattern(regexp = "($|[0-9]{10})", message = "Mobile number must be 10 digits")
    @Schema(
            description = "Account Number"
    )
    private Long accountNumber;

    @NotEmpty(message = "Account Type cannot be Empty")
    @Schema(
            description = "Either Saving or Current",
            example = "Saving"
    )
    private String accountType;

    @Schema(
            description = "Address of the Branch"
    )
    @NotEmpty(message = "Branch Address cannot be Empty")
    private String branchAddress;
}

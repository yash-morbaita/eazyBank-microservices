package com.codewithyash.accounts.controller;

import com.codewithyash.accounts.constants.AccountsConstants;
import com.codewithyash.accounts.dto.AccountsContactInfoPropertyDto;
import com.codewithyash.accounts.dto.CustomerDto;
import com.codewithyash.accounts.dto.ErrorResponseDto;
import com.codewithyash.accounts.dto.ResponseDto;
import com.codewithyash.accounts.service.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1", produces = {MediaType.APPLICATION_JSON_VALUE}/*,
        consumes = {MediaType.APPLICATION_JSON_VALUE}*/)
@Validated
@Tag(
        name = "CRUD REST APIS for Accounts in Easy Bank",
        description = "CRUD REST APIs in Eazy Bank to CREATE,UPDATE,FETCH and DELETE account DETAILS"
)
public class AccountsController {

    private final IAccountService accountService;


    @Autowired
    private AccountsContactInfoPropertyDto accountsContactInfoPropertyDto;

    public AccountsController(IAccountService accountService) {
        this.accountService = accountService;
    }

    /** decalring variable using @Value. this gets the value from tghe properties.(in backend, properties is loaded in applciation
     * context, from that application context, value is passed using @Value. **/
//    @Value("${build.version}")
//    private String buildVersion;

    /** loading properties using Environment Variables**/
    @Autowired
    private Environment environment;

    @Operation(
            summary = "Create Account Rest API",
            description = " REST API to create a new Customer & Account inside EazyBank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Client Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PostMapping("/accounts")
    public ResponseEntity<ResponseDto> createAccounts(@RequestBody @Valid CustomerDto customerDto) {

        accountService.createAccount(customerDto);
        return ResponseEntity
            .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201,AccountsConstants.MESSAGE_201));
//        return new ResponseEntity<>(new ResponseDto(AccountsConstants.STATUS_201,AccountsConstants.MESSAGE_201),HttpStatus.CREATED);
    }
    @Operation(
            summary = "Fetch Account Rest API",
            description = " REST API to fetch a new Customer & Account inside EazyBank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Client Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchCustomer(@RequestParam
                                                         @Pattern(regexp = "($|[0-9]{10})", message = "Mobile number must be 10 digits")
                                                         String mobileNumber) {
        CustomerDto customer = accountService.fetchAccount(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customer);
    }

    @Operation(
            summary = "Update Account Rest API",
            description = " REST API to update a Customer & Account inside EazyBank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "HTTP Status Client Side Error"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Client Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateCustomer(@RequestBody @Valid CustomerDto customerDto) {
        boolean isUpdated = accountService.updateAccount(customerDto);
        if(isUpdated) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(AccountsConstants.STATUS_200,AccountsConstants.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDto(AccountsConstants.STATUS_417,AccountsConstants.MESSAGE_417_UPDATE));
        }
    }

    @Operation(
            summary = "Delete Account Rest API",
            description = " REST API to delete Customer & Account inside EazyBank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "HTTP Status Client Side Error"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Client Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccount(@RequestParam
                                                         @Pattern(regexp = "($|[0-9]{10})", message = "Mobile number must be 10 digits")
                                                         String mobileNumber) {
        boolean isDeleted = accountService.deleteAccount(mobileNumber);
        if(isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
                    new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_DELETE));
        }
    }

//    @Operation(
//            summary = "Build Information for MS",
//            description = " REST API with information about the build info"
//    )
//    @ApiResponses({
//            @ApiResponse(
//                    responseCode = "200",
//                    description = "HTTP Status OK"
//            ),
//            @ApiResponse(
//                    responseCode = "500",
//                    description = "HTTP Status Client Error",
//                    content = @Content(
//                            schema = @Schema(implementation = ErrorResponseDto.class)
//                    )
//            )
//    })
//
//    @GetMapping("/build-info")
//    public ResponseEntity<String> getBuildInfo() {
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body("Version: " + buildVersion);
//    }

    @Operation(
            summary = "Build Information to get Java version for MS",
            description = " REST API with information about the java version"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Client Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Java Version: " + environment.getProperty("JAVA_HOME"));
    }

    @Operation(
            summary = "Contact information of Loans MS",
            description = " REST API to get the contact information of loans MS"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Client Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("contact-info")
    public ResponseEntity<AccountsContactInfoPropertyDto> getContactInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountsContactInfoPropertyDto);
    }


}

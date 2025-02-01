package com.codewithyash.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data @AllArgsConstructor
@Schema(
        name = "Failed Response",
        description = "Schema which holds the failed response information"
)
public class ErrorResponseDto {
    @Schema(
            description = "API path of the request"
    )
    private String apiPath;
    @Schema(
            description = "Failed ErrorCode sent"
    )
    private HttpStatus errorCode;
    @Schema(
            description = "Failed ErrorMessage sent"
    )
    private String errorMessage;
    @Schema(
            description = "Failed Request Date and Time"
    )
    private LocalDateTime errorTime;
}

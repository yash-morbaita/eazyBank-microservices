package com.codewithyash.cards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data @AllArgsConstructor
@Schema(
        name = "ErrorResponse",
        description = "Schema to hold error response information"
)
public class ErrorResponseDto {
    @Schema(
            description = "API path invoked by client"
    )
    private String api;
    @Schema(
            description = "Error code representing the error happened"
    )
    private String errorStatus;
    @Schema(
            description = "Error message representing the error happened"
    )
    private String errorMessage;
    @Schema(
            description = "Time representing when the error happened"
    )
    private LocalDateTime errorTime;
}

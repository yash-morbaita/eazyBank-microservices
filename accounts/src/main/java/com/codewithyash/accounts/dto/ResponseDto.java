package com.codewithyash.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
@Schema(
        name = "Successful Response",
        description = "Schema which holds the success response information"
)
public class ResponseDto {

    @Schema(
            description = "Response Status of the Request"
    )
    private String status;
    @Schema(
            description = "Response Message of the Request"
    )
    private String statusMessage;
}

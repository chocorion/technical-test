package fr.rob.technicaltest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

/**
 * Represent the user sent to the client.
 */
@Data
@AllArgsConstructor
public class UserResponse {
    @Schema(description = "Id of the user", example = "1")
    private Long id;

    @Schema(description = "Name of the user", example = "James")
    private String name;

    @Schema(description = "Date of birth, yyyy-mm-dd", example = "2021-09-22")
    private LocalDate birth;

    @Schema(description = "Country, iso3 format", example = "FRA")
    private String country;

    @Schema(description = "Phone number", nullable = true, example = "0666666666")
    private String phoneNumber;

    @Schema(description = "Gender, f for female and m for male", nullable = true, example = "m")
    private String gender;
}

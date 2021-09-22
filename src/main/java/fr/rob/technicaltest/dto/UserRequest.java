package fr.rob.technicaltest.dto;

import fr.rob.technicaltest.validation.AdultBirthDate;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;


/**
 * Represent a user sent by client, contains the validation process.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    @Schema(description = "User name", example = "David")
    @NotBlank(message = "Name may not be empty")
    private String name;

    @Schema(description = "Date of birth, yyyy-mm-dd", example = "1998-04-22")
    @AdultBirthDate
    private LocalDate birth;

    @Schema(description = "Country, iso3 format. Only FRA is accepted", example = "FRA")
    @NotNull(message = "Country must be present")
    @Pattern(regexp = "FRA", message = "Only French people are accepted")
    private String country;

    @Schema(description = "French phone number. Optional", nullable = true, example = "0655665566")
    @Pattern(regexp = "^(0|\\+33)[0-9]([-. ]?[0-9]{2}){4}$", message = "Phone number is not a valid french phone number")
    private String phoneNumber;

    @Schema(description = "Gender, f for female, m for male. Optional", nullable = true, example = "f")
    @Pattern(regexp = "F|f|M|m", message = "Invalid gender, must be 'f' or 'm'")
    private String gender;
}

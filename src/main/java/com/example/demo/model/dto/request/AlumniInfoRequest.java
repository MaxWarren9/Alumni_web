package com.example.demo.model.dto.request;

import com.example.demo.enums.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AlumniInfoRequest {
    @NotEmpty
    String email;
    String password;
    String firstName;
    String lastName;
    @NotNull
    LocalDate dateOfBirth;
    Integer graduationYear;

    Gender gender;
}

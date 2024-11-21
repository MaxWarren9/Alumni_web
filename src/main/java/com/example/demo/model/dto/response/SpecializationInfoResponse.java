package com.example.demo.model.dto.response;

import com.example.demo.model.dto.request.AlumniInfoRequest;
import com.example.demo.model.dto.request.SpecializationInfoRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SpecializationInfoResponse extends SpecializationInfoRequest {
    long id;
}

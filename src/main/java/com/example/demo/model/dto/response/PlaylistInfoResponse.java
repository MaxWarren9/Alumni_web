package com.example.demo.model.dto.response;

import com.example.demo.model.dto.request.PlaylistInfoRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlaylistInfoResponse extends PlaylistInfoRequest {
    long id;
}

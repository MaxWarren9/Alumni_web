package com.example.demo.model.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.time.Duration;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrackInfoRequest {
    @NotNull
    String trackName;
    @NotNull
    String trackArtist;

    String trackAlbum;

    String trackGenre;

    Duration trackDuration;

}

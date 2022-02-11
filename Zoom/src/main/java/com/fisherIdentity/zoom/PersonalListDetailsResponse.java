package com.fisherIdentity.zoom;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonalListDetailsResponse implements Serializable {
    @JsonProperty("users")
    List<PersonalDetailsResponse> personalDetailsResponses;
}

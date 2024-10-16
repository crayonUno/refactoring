package com.crayon.refactoring;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Performance {
    public String playID;
    public int audience;

    @JsonCreator
    public Performance(@JsonProperty("playID") String playID, @JsonProperty("audience") int audience) {
        this.playID = playID;
        this.audience = audience;
    }
}
package com.w9jds.marketbot.data.models.esi;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.joda.time.DateTime;

public class EsiMarketHistory {

    @JsonProperty
    double average;

    @JsonProperty
    DateTime date;

    @JsonProperty
    double highest;

    @JsonProperty
    double lowest;

    @JsonProperty("order_count")
    int count;

    @JsonProperty
    int volume;

}

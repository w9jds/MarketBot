package com.w9jds.marketbot.data.models.esi;


import com.fasterxml.jackson.annotation.JsonProperty;

public class EsiMarketOrder {

//    Enum:	"station", "region", "solarsystem", "1", "2", "3", "4", "5", "10", "20", "30", "40",

    @JsonProperty
    int duration;

    @JsonProperty("is_buy_order")
    boolean isBuyOrder;

    @JsonProperty
    String issued;

    @JsonProperty("location_id")
    int locationId;

    @JsonProperty("min_volume")
    int minVolume;

    @JsonProperty("order_id")
    int orderId;

    @JsonProperty
    double price;

    @JsonProperty
    String range;

    @JsonProperty("type_id")
    int typeId;

    @JsonProperty("volume_remain")
    int remaining;

    @JsonProperty("volume_total")
    int total;


}

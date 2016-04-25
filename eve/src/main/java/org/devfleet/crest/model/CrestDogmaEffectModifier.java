package org.devfleet.crest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

class CrestDogmaEffectModifier {

    @JsonProperty("domain")
    private String domain;

    @JsonProperty("func")
    private String function;

    @JsonProperty("operator")
    private int operator;

    @JsonProperty("modifyingAttributeID")
    private CrestItem modifyingAttribute;

    @JsonProperty("modifiedAttributeID")
    private CrestItem modifiedAttribute;

    public String getDomain() {
        return domain;
    }

    public String getFunction() {
        return function;
    }

    public int getOperator() {
        return operator;
    }

    public CrestItem getModifyingAttribute() {
        return modifyingAttribute;
    }

    public CrestItem getModifiedAttribute() {
        return modifiedAttribute;
    }
}

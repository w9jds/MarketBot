package core.eve.crest;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class CrestDogmaEffect extends CrestItem {

    @JsonProperty("isAssistance")
    private Boolean assistance;

    @JsonProperty("isOffensive")
    private Boolean offensive;

    @JsonProperty("isWarpSafe")
    private Boolean warpSafe;

    @JsonProperty("isDefault")
    private Boolean defaultEffect;

    @JsonProperty
    private Boolean electronicChance;

    @JsonProperty
    private Boolean rangeChance;

    @JsonProperty
    private Boolean disallowAutoRepeat;

    @JsonProperty
    private Integer effectCategory;

    @JsonProperty
    private Integer postExpression;

    @JsonProperty
    private Integer preExpression;

    @JsonProperty
    private String displayName;

    public Boolean getAssistance() {
        return assistance;
    }

    public Boolean getOffensive() {
        return offensive;
    }

    public Boolean getWarpSafe() {
        return warpSafe;
    }

    public Boolean getElectronicChance() {
        return electronicChance;
    }

    public Boolean getRangeChance() {
        return rangeChance;
    }

    public Boolean getDisallowAutoRepeat() {
        return disallowAutoRepeat;
    }

    public Integer getEffectCategory() {
        return effectCategory;
    }

    public Integer getPostExpression() {
        return postExpression;
    }

    public Integer getPreExpression() {
        return preExpression;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Boolean getDefaultEffect() {
        return defaultEffect;
    }

    public void setDefaultEffect(boolean defaultEffect) {
        this.defaultEffect = defaultEffect;
    }
}

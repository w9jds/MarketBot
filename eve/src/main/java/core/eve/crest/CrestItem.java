package core.eve.crest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CrestItem extends CrestEntity {

    @JsonProperty
    private String href;

    @JsonProperty
    private long id;

    @JsonProperty
    private String name;

    @JsonProperty
    private String description;

    @JsonProperty
    private boolean published = true;

    public final String getRef() {
        return href;
    }

    public final long getId() {
        return id;
    }

    protected final void setId(final long id) {
        this.id = id;
    }

    public final String getName() {
        return name;
    }

    public final String getDescription() {
        return description;
    }

    public final boolean getPublished() {
        return published;
    }
}

package org.devfleet.crest.model;

import java.util.Collections;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class CrestDictionary<T extends CrestEntity> extends CrestEntity {

    @JsonProperty
    private long totalCount;

    @JsonProperty
    private int pageCount;

    @JsonProperty("next")
    @JsonDeserialize(using = RefDeserializer.class)
    private String pageNext;

    @JsonProperty("previous")
    @JsonDeserialize(using = RefDeserializer.class)
    private String pagePrevious;

    @JsonProperty
    private List<T> items;

    public final long getTotalCount() {
        return totalCount;
    }

    public final int getPageCount() {
        return pageCount;
    }

    public final List<T> getItems() {
        return Collections.unmodifiableList(items);
    }

    public String getPageNext() {
        return pageNext;
    }

    public String getPagePrevious() {
        return pagePrevious;
    }
}

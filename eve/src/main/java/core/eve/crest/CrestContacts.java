package core.eve.crest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

public final class CrestContacts extends CrestEntity {

    private int pageCount;

    private List<CrestContact> items;

    @JsonProperty
    @JsonDeserialize(using = RefDeserializer.class)
    private String next;

    private int totalCount;

    public int getPageCount() {
        return pageCount;
    }

    public List<CrestContact> getItems() {
        return items;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public String getNext() {
        return next;
    }

}

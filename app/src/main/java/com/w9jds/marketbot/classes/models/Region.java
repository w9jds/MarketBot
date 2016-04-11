package com.w9jds.marketbot.classes.models;


/**
 * Created by Jeremy Shore on 2/16/16.
 */
public final class Region extends MarketItemBase {

    private String href;

    public String getHref() {
        return href;
    }

    public class Builder {

        private long id;
        private String name;
        private String href;

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setHref(String href) {
            this.href = href;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Region build() {
            Region region = new Region();
            region.id = this.id;
            region.name = this.name;
            region.href = this.href;

            return region;
        }
    }

}

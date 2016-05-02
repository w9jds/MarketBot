package com.w9jds.marketbot.classes.models;

public final class Region extends MarketItemBase {

    private String href;

    public String getHref() {
        return href;
    }

    public static class Builder {

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

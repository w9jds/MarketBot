package com.w9jds.marketbot.classes.models;


/**
 * Created by Jeremy on 2/17/2016.
 */
public final class Type extends MarketItemBase {

    private String href;
    private String icon;
    private String marketGroup;
    private long groupId;

    public String getHref() {
        return href;
    }

    public String getIcon() {
        return icon;
    }

    public long getGroupId() {
        return groupId;
    }

    public String getMarketGroup() {
        return marketGroup;
    }

    public static class Builder {

        private long id;
        private String name;
        private String href;
        private String icon;
        private String marketGroup;
        private long groupId;

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setHref(String href) {
            this.href = href;
            return this;
        }

        public Builder setIcon(String icon) {
            this.icon = icon;
            return this;
        }

        public Builder setMarketGroup(String marketGroup) {
            this.marketGroup = marketGroup;
            return this;
        }

        public Builder setGroupId(long groupId) {
            this.groupId = groupId;
            return this;
        }

        public Type build() {
            Type type = new Type();
            type.id = this.id;
            type.name = this.name;
            type.href = this.href;
            type.icon = this.icon;
            type.marketGroup = this.marketGroup;
            type.groupId = this.groupId;

            return type;
        }


    }

}

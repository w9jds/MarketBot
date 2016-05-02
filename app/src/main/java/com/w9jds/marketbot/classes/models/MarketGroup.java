package com.w9jds.marketbot.classes.models;

public final class MarketGroup extends MarketItemBase  {

    private String parentGroup;
    private long parentId;
    private String href;
    private String description;
    private String types;

    public MarketGroup() {

    }

    public String getDescription() {
        return description;
    }

    public String getHref() {
        return href;
    }

    public String getTypesLocation() {
        return types;
    }

    public String getParentGroup() {
        return parentGroup;
    }

    public long getParentId() {
        return this.parentId;
    }

    public Long getParentGroupId() {
        String[] query = this.parentGroup.split("/");

        for (int i = query.length; i > 0; i--) {
            if (!query[i-1].equals("")) {
                return Long.parseLong(query[i-1]);
            }
        }

        return 0L;
    }

    public boolean hasParent() {
        return this.parentGroup != null && !this.parentGroup.equals("");
    }

    public static class Builder {

        private long id;
        private String name;
        private String parentGroup;
        private long parentId;
        private String href;
        private String description;
        private String types;

        public Builder setParentGroup(String parentGroup) {
            this.parentGroup = parentGroup;
            return this;
        }

        public Builder setHref(String href) {
            this.href = href;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setTypes(String types) {
            this.types = types;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setParentId(long id) {
            this.parentId = id;
            return this;
        }

        public MarketGroup build() {
            MarketGroup group = new MarketGroup();
            group.id = id;
            group.name = name;

            if (parentGroup != null) {
                group.parentGroup = this.parentGroup;
                group.parentId = this.parentId;
            }

            group.href = this.href;
            group.description = this.description;
            group.types = this.types;

            return group;
        }
    }

}

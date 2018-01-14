package com.w9jds.marketbot.classes.models

import com.w9jds.marketbot.data.models.esi.Group
import com.w9jds.marketbot.data.models.esi.MarketBase
import com.w9jds.marketbot.data.storage.GroupEntry

data class MarketGroup(var group: GroupEntry): MarketBase(), Group {


    override val description: String
        get() = group.description


//
//    public MarketGroup() {
//
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public String getHref() {
//        return href;
//    }
//
//    public String getTypesLocation() {
//        return types;
//    }
//
//    public String getParentGroup() {
//        return parentGroupRef;
//    }
//
//    public long getParentId() {
//        return this.parentId;
//    }
//
//    public boolean hasParent() {
//        return this.parentGroupRef != null && !this.parentGroupRef.equals("");
//    }
//
//    public static class Builder {
//
//        private long id;
//        private String name;
//        private String parentGroup;
//        private long parentId;
//        private String href;
//        private String description;
//        private String types;
//
//        public Builder setParentGroup(String parentGroup) {
//            this.parentGroup = parentGroup;
//            return this;
//        }
//
//        public Builder setHref(String href) {
//            this.href = href;
//            return this;
//        }
//
//        public Builder setDescription(String description) {
//            this.description = description;
//            return this;
//        }
//
//        public Builder setTypes(String types) {
//            this.types = types;
//            return this;
//        }
//
//        public Builder setName(String name) {
//            this.name = name;
//            return this;
//        }
//
//        public Builder setId(long id) {
//            this.id = id;
//            return this;
//        }
//
//        public Builder setParentId(long id) {
//            this.parentId = id;
//            return this;
//        }
//
//        public MarketGroup build() {
//            MarketGroup group = new MarketGroup();
//            group.id = id;
//            group.name = name;
//
//            if (parentGroup != null) {
//                group.parentGroupRef = this.parentGroup;
//                group.parentId = this.parentId;
//            }
//
//            group.href = this.href;
//            group.description = this.description;
//            group.types = this.types;
//
//            return group;
//        }
//    }
//


}
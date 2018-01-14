package com.w9jds.marketbot.classes.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.w9jds.marketbot.data.models.esi.Group

data class MarketGroup(
        @JsonProperty("market_group_id") override val id: Int,
        @JsonProperty("parent_group_id") override val parentGroupId: Int?,
        @JsonProperty override val name: String?,
        @JsonProperty override val description: String,
        @JsonProperty override val types: List<Int>
): Group


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

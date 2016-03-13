package com.w9jds.eveapi.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jeremy Shore on 3/13/16.
 */
public final class ServerInfo {

    @SerializedName("serverVersion")
    String serverVersion;

    public String getServerVersion() {
        return serverVersion;
    }

}

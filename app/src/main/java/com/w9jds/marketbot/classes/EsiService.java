package com.w9jds.marketbot.classes;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.w9jds.evemeetbuddies.data.models.esi.EsiCharacter;
import io.w9jds.evemeetbuddies.data.models.esi.EsiContact;
import io.w9jds.evemeetbuddies.data.models.esi.EsiCorporation;
import io.w9jds.evemeetbuddies.data.models.esi.EsiReference;
import io.w9jds.evemeetbuddies.data.models.esi.EsiSearchResults;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface EsiService {

    @GET("/latest/characters/{characterId}/contacts/")
    Observable<Response<List<EsiContact>>> getContacts(
            @Path("characterId") final long characterId,
            @Query("token") final String authToken
    );

    @POST("/latest/characters/{characterId}/contacts/")
    Call<List<Long>> postContacts(
            @Path("characterId") final long characterId,
            @Body final ArrayList<Long> contactIds,
            @Query("standing") final float standing,
            @Query("watched") final boolean watched,
            @Query("token") final String authToken
    );

    @PUT("/latest/characters/{characterId}/contacts/")
    Call<EsiContact> updateContacts(
            @Path("characterId") final long characterId,
            @Body final String contactIds,
            @Query("standing") final float standing,
            @Query("watched") final boolean watched,
            @Query("token") final String authToken
    );

    @HTTP(method = "delete", path = "/latest/characters/{characterId}/contacts/", hasBody = true)
    Call<Void> deleteContacts(
            @Path("characterId") final long characterId,
            @Body final ArrayList<Long> contactIds,
            @Query("token") final String authToken
    );

    @GET("/latest/characters/{characterId}/contacts/labels/")
    Observable<Response<EsiReference>> getContactLabels(
            @Path("characterId") final long characterId,
            @Query("token") final String authToken
    );

    @POST("/latest/ui/openwindow/contract/")
    Call<Void> openContact(
            @Query("contract_id") final long contactId,
            @Query("token") final String authToken
    );

    @GET("/latest/characters/{characterId}/")
    Observable<Response<EsiCharacter>> getCharacter(
            @Path("characterId") final long characterId
    );

    @GET("/latest/corporations/{corporationId}/")
    Call<EsiCorporation> getCorporation(
            @Path("corporationId") final long corpId
    );

    @GET("/latest/search/")
    Observable<Response<EsiSearchResults>> search(
            @Query("search") final String search,
            @Query("categories") final String category,
            @Query("language") final String language,
            @Query("strict") final boolean isStrict
    );

    @GET("/latest/characters/names")
    Observable<Response<ArrayList<EsiReference>>> characterNames(
            @Query("character_ids") final String ids
    );

}

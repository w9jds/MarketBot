//package com.w9jds.marketbot.services.receivers;
//
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//
//import com.w9jds.eveapi.Models.Type;
//import com.w9jds.marketbot.activities.ItemActivity;
//import com.w9jds.marketbot.data.storage.DataContracts;
//
///**
// * Created by Jeremy Shore on 3/23/16.
// */
//public class OpenItemReceiver extends BroadcastReceiver {
//
//    public static final String INTENT_NAME = "com.w9jds.marketbot.intent.action.OPEN_TYPE";
//
//    @Override
//    public void onReceive(Context context, Intent intent) {
//
//        final Intent typeIntent = new Intent();
//        intent.setClass(context, ItemActivity.class);
//
//
//
//        if (typeId != -1) {
//            Type type = DataContracts.MarketTypeEntry.getType(context, typeId);
//
//            if (type != null && regionId != -1) {
//                typeIntent.putExtra("currentType", type);
//                typeIntent.putExtra("selectedRegion", regionId);
//
//                context.startActivity(typeIntent);
//            }
//        }
//    }
//}

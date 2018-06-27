# MarketBot
This is an open source Android application that allows the user to search the market, along with buy and sell orders. 

It is quick and responsive, so you don't have to worry about that slow SDE bloating the APK. 

I used Android Material design standards and made sure it uses as many transitions as possible to make it a smooth and enjoyable experience. 

Make sure to tap on the market orders, because they open to give you more info, like range and location.



# Intent Integration

Courtesy of EvaNova, here is the class needed to open MarketBot from any app that you would like:

    import android.content.Context;
    import android.content.Intent;
    import android.content.pm.PackageManager;
    import android.content.pm.ResolveInfo;
    ​
    import java.util.List;
    ​
    public class MarketBot {
        private MarketBot() {}
    ​
        public static void launch(
                final Context context,
                final long itemID,
                final long regionID) {
            final Intent intent = new Intent("com.w9jds.marketbot.intent.action.OPEN_TYPE");
            intent.putExtra("typeId", itemID);
            intent.putExtra("regionId", regionID);
            context.startActivity(intent);
        }
    ​
        public static boolean isAvailable(final Context context) {
            final Intent intent = new Intent("com.w9jds.marketbot.intent.action.OPEN_TYPE");
            return isIntentAvailable(context, intent);
        }
    ​
        private static boolean isIntentAvailable(Context ctx, Intent intent) {
            final PackageManager mgr = ctx.getPackageManager();
            List<ResolveInfo> list = mgr.queryIntentActivities(intent, 0);
            return list.size() > 0;
        }
    }
package com.w9jds.marketbot.services;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;

import android.os.AsyncTask;
import android.os.Build;

import com.w9jds.marketbot.classes.CrestService;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public final class BotJobService extends JobService {

    private static final String TAG = "MailService";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        new CheckMarketOrders(this)
                .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {


        return false;
    }

    private class CheckMarketOrders extends AsyncTask<JobParameters, Void, JobParameters[]> {

        private Context context;
        private JobParameters params;
        private CrestService publicCrestApi;

        public CheckMarketOrders(Context context) {
            this.context = context;

//            publicCrestApi = new Crest.Builder()
//                    .setPublicTranquilityEndpoint()
//                    .build(context);
        }

        @Override
        protected JobParameters[] doInBackground(JobParameters... params) {

            this.params = params[0];


//            mCurrentCharacter = new Character();
//            PersistableBundle bundle = mParams.getExtras();
//
//            mCurrentCharacter.setCharacterId(bundle.getLong(Constants.CHARACTER_ID));
//            mCurrentCharacter.setCharacterName(bundle.getString(Constants.CHARACTER_NAME));
//            mCurrentCharacter.setKeyId(bundle.getString(Constants.KEYID));
//            mCurrentCharacter.setvCode(bundle.getString(Constants.VCODE));
//
//            mApiService = new Api.Builder()
//                    .setTranquilityEndpoint()
//                    .build();
//
//            mApiService.getMailHeaders(mCurrentCharacter, this);

            // Do updating and stopping logical here.
            return params;
        }

//        @Override
//        public void success(Response<ArrayList<MailHeader>> mails) {
//            scheduleNextUpdate(mails.getCurrentTime(), mails.getCachedUntil());
//            ArrayList<Long> ids = new ArrayList<>();
//
//            for (MailHeader header : mails.getResult()) {
//                if (header.getSenderId() != mCurrentCharacter.getCharacterId()) {
//                    long newId = MailHeaderEntry.addNewMailHeader(mContext, header);
//                    ids.add(header.getMessageId());
//
//                    if (newId > 0) {
//                        mMails.add(header);
//                    }
//                }
//            }
//
//            if (ids.size() > 0) {
//                mApiService.getMailBodies(mCurrentCharacter, ids, mMailBodyCallback);
//            }
//
//            cleanOutMailbox(mails.getResult());
//        }

//        private void cleanOutMailbox(ArrayList<MailHeader> mails) {
//            HashMap<Long, MailHeader> headers = new HashMap<>();
//            ArrayList<MailHeader> onDevice = MailHeaderEntry.getInbox(mContext, mCurrentCharacter);
//            ArrayList<Long> removeItems = new ArrayList<>();
//
//            for (MailHeader header : mails) {
//                headers.put(header.getMessageId(), header);
//            }
//
//            for (MailHeader header : onDevice) {
//                if (!headers.containsKey(header.getMessageId())) {
//                    removeItems.add(header.getMessageId());
//                    Log.d(TAG, "removing message " + header.getMessageId());
//                }
//            }
//
//            MailHeaderEntry.removeMails(mContext, removeItems);
//        }

//        private void buildSummary(ArrayList<MailHeader> headers) {
//            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
//            inboxStyle.setBigContentTitle(headers.size() + " new messages");
//            inboxStyle.setSummaryText(mCurrentCharacter.getCharacterName());
//
//            Intent mailIntent = new Intent(mContext, MainActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putString(Constants.CURRENT_ACCOUNT, mCurrentCharacter.getAccountName());
//            bundle.putLong(Constants.CHARACTER_ID, mCurrentCharacter.getCharacterId());
//            bundle.putString(SyncStateContract.Constants.CURRENT_FRAGMENT, "Mail");
//            mailIntent.putExtra(Constants.NOTIFICATION_BUNDLE, bundle);
//
//            PendingIntent intent = PendingIntent.getActivity(mContext, (int)mCurrentCharacter.getCharacterId(), mailIntent,
//                    PendingIntent.FLAG_UPDATE_CURRENT);
//
//            for (MailHeader header : headers) {
//                int senderLength = header.getSenderName().length();
//                int titleLength = header.getTitle().length();
//
//                Spannable spannable = new SpannableString(header.getSenderName() + " " + header.getTitle());
//                spannable.setSpan(new StyleSpan(Typeface.BOLD), 0,
//                        senderLength, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                spannable.setSpan(new StyleSpan(Typeface.NORMAL), senderLength, senderLength + titleLength,
//                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                inboxStyle.addLine(spannable);
//
//                buildNotification(header, false);
//            }
//
//            NotificationManager manager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
//
//            Notification summaryNotification = new NotificationCompat.Builder(mContext)
//                    .setContentTitle(headers.size() + " new messages")
//                    .setContentText(mCurrentCharacter.getCharacterName())
//                    .setAutoCancel(true)
//                    .setSmallIcon(R.drawable.ic_mail_white_24dp)
//                    .setTicker(String.valueOf(headers.size()))
//                    .setNumber(headers.size())
//                    .setColor(Color.parseColor("#2980b9"))
//                    .setStyle(inboxStyle)
//                    .setContentIntent(intent)
//                    .setGroup(String.valueOf(mCurrentCharacter.getCharacterId()))
//                    .setGroupSummary(true)
//                    .setDefaults(Notification.DEFAULT_ALL)
//                    .build();
//
//            manager.notify((int) mCurrentCharacter.getCharacterId(), summaryNotification);
//        }

//        private void buildNotification(final MailHeader header, final boolean soundOn) {
//            Intent mailIntent = new Intent(mContext, MessageActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putLong(Constants.CHARACTER_ID, mCurrentCharacter.getCharacterId());
//            bundle.putLong(Constants.CURRENT_MESSAGE, header.getMessageId());
//            mailIntent.putExtra(Constants.NOTIFICATION_BUNDLE, bundle);
//
//            final PendingIntent intent = PendingIntent.getActivity(mContext, 0, mailIntent,
//                    PendingIntent.FLAG_UPDATE_CURRENT);
//
//            DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
//            float imageSize = 64 * (metrics.density / 3f);
//
//            Glide.with(mContext)
//                .load(String.format(Constants.PORTRAIT_FORMAT, ImagePath.CHARACTER,
//                        header.getSenderId(), "200", ImageType.JPG))
//                .asBitmap()
//                .into(new SimpleTarget<Bitmap>((int) imageSize, (int) imageSize) {
//                    @Override
//                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//
//                        NotificationManager manager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
//                        NotificationCompat.Builder mailBuilder = new NotificationCompat.Builder(mContext)
//                            .setSmallIcon(R.drawable.ic_mail_white_24dp)
//                            .setLargeIcon(resource)
//                            .setColor(Color.parseColor("#2980b9"))
//                            .setAutoCancel(true)
//                            .setContentTitle(header.getSenderName())
//                            .setContentText(header.getTitle())
//                            .setContentIntent(intent)
//                            .setGroup(String.valueOf(mCurrentCharacter.getCharacterId()));
//
//                        if (header.getMessageBody() != null && !header.getMessageBody().equals("")) {
//                            String messageBody = Html.fromHtml(header.getMessageBody()).toString();
//                            messageBody = messageBody.replaceAll("\\n\\n","\n")
//                                    .replaceAll("\\s\\s+", " ");
//
//                            mailBuilder.setStyle(new NotificationCompat.BigTextStyle()
//                                .setBigContentTitle(header.getTitle())
//                                .bigText(messageBody)
//                                .setSummaryText(mCurrentCharacter.getCharacterName()));
//                        }
//
//                        if (soundOn) {
//                            mailBuilder.setDefaults(Notification.DEFAULT_ALL);
//                        }
//
//                        manager.notify((int) header.getMessageId(), mailBuilder.build());
//                    }
//                });
//        }

//        Callback<ArrayList<MailBody>> mMailBodyCallback = new Callback<ArrayList<MailBody>>() {
//            @Override
//            public void success(ArrayList<MailBody> mailBodies) {
//                MailBodyEntry.addMailBodies(mContext, mailBodies);
//                HashMap<Long, String> bodies = new HashMap<>();
//
//                for (MailBody body : mailBodies) {
//                    bodies.put(body.getMessageId(), body.getMessageBody());
//                }
//
//                for (MailHeader header : mMails) {
//                    header.setMessageBody(bodies.get(header.getMessageId()));
//                }
//
//                if (mMails.size() > 1) {
//                    buildSummary(mMails);
//                }
//                else if (mMails.size() == 1) {
//                    buildNotification(mMails.get(0), true);
//                }
//
//                jobFinished(mParams, false);
//            }
//
//            @Override
//            public void failure(String error) {
//                jobFinished(mParams, true);
//            }
//        };

//        @Override
//        public void failure(String error) {
//            jobFinished(mParams, true);
//        }

//        private void scheduleNextUpdate(Date current, Date cached) {
//            long diff = cached.getTime() - current.getTime();
//
//            PersistableBundle info = new PersistableBundle();
//            info.putLong(Constants.CHARACTER_ID, mCurrentCharacter.getCharacterId());
//            info.putString(Constants.CHARACTER_NAME, mCurrentCharacter.getCharacterName());
//            info.putString(Constants.KEYID, mCurrentCharacter.getKeyId());
//            info.putString(Constants.VCODE, mCurrentCharacter.getvCode());
//
//            JobScheduler scheduler = (JobScheduler) mContext.getSystemService(Context.JOB_SCHEDULER_SERVICE);
//            ComponentName serviceName = new ComponentName(mContext, MailJobService.class);
//            JobInfo jobInfo = new JobInfo.Builder((int)mCurrentCharacter.getCharacterId() + 1, serviceName)
//                .setExtras(info)
//                .setPersisted(true)
//                .setRequiresCharging(false)
//                .setRequiresDeviceIdle(false)
//                .setMinimumLatency(diff + 60000)
//                .build();
//
//            scheduler.schedule(jobInfo);
//        }

        @Override
        protected void onPostExecute(JobParameters[] result) {
//            for (JobParameters params : result) {
//                if (!hasJobBeenStopped(params)) {
//                    jobFinished(params, false);
//                }
//            }
        }
    }
}

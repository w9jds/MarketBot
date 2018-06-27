//package com.w9jds.marketbot.data.service
//
//import androidx.work.Worker
//import com.google.firebase.database.DataSnapshot
//import com.w9jds.marketbot.classes.MarketBot
//import com.w9jds.marketbot.classes.models.market.MarketGroup
//import java.util.*
//
//class GroupsWorker: Worker()  {
//
//    private val database =  MarketBot.base.database()
//    private val firebase = MarketBot.base.firebase()
//
//    override fun doWork(): WorkerResult {
//
////        firebase.getReference("groups").getSnapshot()
////                .observeOn(Schedulers.io())
////                .subscribeOn(Schedulers.computation())
////                .subscribeBy(
////                    onNext = {
////                        processGroups(it?.children?.distinct() ?: emptyList())
////                        WorkerResult.SUCCESS
////                    },
////                    onError = {
////                        Log.e("MarketSync", it.message, it)
////                        WorkerResult.FAILURE
////                    }
////                )
//
//        TODO("not implemented")
//    }
//
//    private fun processGroups(groups: List<DataSnapshot>): Boolean {
//        val items: HashMap<String, MarketGroup> = hashMapOf()
//
//        for (group in groups) {
//            val parsed = group.getValue(MarketGroup::class.java)
//            if (parsed != null) {
//                items[group.key] = parsed
//            }
//        }
//
//        database.marketGroupDao().insertAllGroups(items.values.asIterable())
//        return true
//    }
//
//}
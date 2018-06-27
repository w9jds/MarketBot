//package com.w9jds.marketbot.data.service
//
//import androidx.work.Worker
//import com.google.firebase.database.DataSnapshot
//import com.w9jds.marketbot.classes.MarketBot
//import com.w9jds.marketbot.classes.models.market.MarketType
//import java.util.*
//
//class TypesWorker: Worker() {
//
//    private val database =  MarketBot.base.database()
//    private val firebase = MarketBot.base.firebase()
//
//    override fun doWork(): WorkerResult {
//
////        firebase.getReference("types").getSnapshot()
////                .observeOn(Schedulers.io())
////                .subscribeOn(Schedulers.computation())
////                .subscribeBy(
////                        onNext = {
////                            processTypes(it?.children?.distinct() ?: emptyList())
////                        },
////                        onError = {
////                            Log.e("MarketSync", it.message, it)
////                            WorkerResult.SUCCESS
////                        }
////                )
//
//        TODO("not implemented")
//    }
//
//    private fun processTypes(types: List<DataSnapshot>): Boolean {
//        val items: HashMap<String, MarketType> = hashMapOf()
//
//        for (type in types) {
//            val parsed = type.getValue(MarketType::class.java)
//            if (parsed != null) {
//                items[type.key] = parsed
//            }
//        }
//
//        database.marketTypeDao().insertAllTypes(items.values.asIterable())
//        return true
//    }
//
//}
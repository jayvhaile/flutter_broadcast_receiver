package com.jvhaile.broadcast_receiver

import android.content.Intent
import android.os.Bundle
import android.provider.Telephony
import android.telephony.SmsMessage

fun Intent.toMap(): Map<String, Any?> {
    return mapOf(
            "action" to this.action,
            "data" to this.data?.toString(),
            "extra" to this.extras?.toMap()
    )
}

fun Bundle.toMap(): Map<String, Any?> {
    val keySet = this.keySet()
    return mapOf(*keySet.map { it to this.get(it) }.filter {
        val value = it.second
        value is Int || value is Double || value is Float || value is String || value is Map<*, *>
    }.toTypedArray())
}

fun SmsMessage.toMap(): Map<String, Any?> {
    return mapOf(
            "address" to this.displayOriginatingAddress,
            "body" to this.messageBody,
            "date" to this.timestampMillis)
}

fun List<SmsMessage>.mergedMap(): Map<String, Any?> {
    return this.map { it.toMap() }.reduce { acc, map ->
        mapOf(
                "address" to acc["address"],
                "body" to "${acc["body"]}${map["body"]}",
                "date" to acc["date"])
    }
}
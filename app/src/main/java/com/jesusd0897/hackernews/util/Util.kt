package com.jesusd0897.hackernews.util

import org.ocpsoft.prettytime.PrettyTime
import java.util.*

const val TAG_DEBUG = "tag/dev"

object DateHelper {
    fun formatTime(time: Long): String {
        val t = PrettyTime()
        return t.format(Date(1000 * time))
    }
}
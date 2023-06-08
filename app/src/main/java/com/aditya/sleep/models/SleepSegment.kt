/* Copyright 2023 Aditya Mishra

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.aditya.sleep.models

import com.aditya.sleep.SleepReceiver
import com.google.android.gms.location.SleepSegmentEvent
import java.util.ServiceLoader

data class SleepSegment(
    val startTime: String, val endTime: String, val duration: String, val status: SleepStatus
) {
    companion object {
        fun toSleepSegment(sleepSegmentEvent: SleepSegmentEvent): SleepSegment {
            return SleepSegment(
                SleepReceiver.epochToDateTime(sleepSegmentEvent.startTimeMillis),
                SleepReceiver.epochToDateTime(sleepSegmentEvent.endTimeMillis),
                SleepReceiver.convertMsToHMS(sleepSegmentEvent.segmentDurationMillis),
                sleepSegmentEvent.status.toSleepStatus()
            )
        }

        fun Int.toSleepStatus(): SleepStatus = when (this) {
            0 -> SleepStatus.STATUS_SUCCESSFUL
            1 -> SleepStatus.STATUS_MISSING_DATA
            2 -> SleepStatus.STATUS_NOT_DETECTED
            else -> SleepStatus.UNKNOWN
        }
    }
}
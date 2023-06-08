/*Copyright 2023 Aditya Mishra

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
import com.google.android.gms.location.SleepClassifyEvent

data class SleepClassify(
    val time: String, val confidence: Int, val motion: Int, val light: Int,
) {
    companion object {
        fun toSleepClassifyEvent(sleepClassifyEvent: SleepClassifyEvent): SleepClassify {
            return SleepClassify(
                SleepReceiver.epochToDateTime(sleepClassifyEvent.timestampMillis),
                sleepClassifyEvent.confidence,
                sleepClassifyEvent.motion,
                sleepClassifyEvent.light
            )
        }
    }
}
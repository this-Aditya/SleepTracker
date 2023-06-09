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

package com.aditya.sleep

import android.app.Activity
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Message
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class Permissioner {
    private val TAG = "Permissioner"
    fun requestPermissions(
        context: Activity,
        permissions: Array<String>,
        launcher: ActivityResultLauncher<Array<String>>,
        message: String
    ) {
        val permissionsToRequest = mutableListOf<String>()
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                Log.d(TAG, "All permissions not granted yet!!")
                permissionsToRequest.add(permission)
            }
        }

        if (permissionsToRequest.isEmpty()) {
            Log.i(TAG, "All permissions granted")
            // All permissions are already granted
        } else {
            val shouldShowRationale = permissionsToRequest.any {
                ActivityCompat.shouldShowRequestPermissionRationale(context, it)
            }

            if (shouldShowRationale) {
                Log.i(TAG, "Showing Rationale ")
                Toast.makeText(context, "Permission not granted.", Toast.LENGTH_SHORT).show()
                showAlertDialogue(context, permissionsToRequest.toTypedArray(), launcher, message)
            } else {
                Log.i(TAG, "Requesting permissions first time.")
                launcher.launch(permissionsToRequest.toTypedArray())
            }
        }
    }

    private fun showAlertDialogue(
        context: Activity,
        permissions: Array<String>,
        launcher: ActivityResultLauncher<Array<String>>,
        message: String
    ) {
        AlertDialog.Builder(context).apply {
            setTitle("Need permissions to function")
            setMessage(message)
            setPositiveButton("Yes") { _, _ ->
                launcher.launch(permissions) // Trigger the permission request
            }
            setNegativeButton("No") { _, _ ->
                Log.i(TAG, "Permission denied.")
            }
        }.create().show()
    }

}
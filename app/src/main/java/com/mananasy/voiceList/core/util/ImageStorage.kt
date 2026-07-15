package com.mananasy.voiceList.core.util

import android.content.Context
import android.net.Uri
import java.io.File
import java.util.UUID

object ImageStorage {

    private fun imagesDir(context: Context): File {
        val dir = File(context.getExternalFilesDir(null), "images")
        if (!dir.exists()) dir.mkdirs()
        return dir
    }

    /** copy image to the app's dedicated folder and return the absolute path */
    fun copyToAppStorage(context: Context, sourceUri: Uri): String? {
        return try {
            val fileName = "${UUID.randomUUID()}.jpg"
            val destFile = File(imagesDir(context), fileName)

            context.contentResolver.openInputStream(sourceUri)?.use { input ->
                destFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            destFile.absolutePath
        } catch (e: Exception) {
            null
        }
    }

    /** cleaning */
    fun deleteImage(path: String?) {
        if (path.isNullOrBlank()) return
        try {
            val file = File(path)
            if (file.exists()) file.delete()
        } catch (e: Exception) {
        }
    }
}
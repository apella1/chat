package me.apella.chat.util

import lombok.extern.slf4j.Slf4j
import java.io.File
import java.io.IOException
import java.nio.file.Files

@Slf4j
class FileUtils {
    fun readFileFromLocation(fileUrl: String): ByteArray? {
        if (fileUrl.isBlank()) {
            return byteArrayOf(0)
        }

        return try {
            val filePath = File(fileUrl).toPath()
            Files.readAllBytes(filePath)
        } catch (e: IOException) {
            return byteArrayOf(0)
        }
    }
}
package me.apella.chat.util

import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import java.io.File
import java.io.IOException
import java.nio.file.Files

@Slf4j
class FileUtils {
    private val logger = LoggerFactory.getLogger(FileUtils::class.java)
    private val baseDirectory = File("uploads").absolutePath

    fun readFileFromLocation(fileUrl: String): ByteArray? {
        if (fileUrl.isBlank()) {
            logger.error("File url is blank!")
            return null
        }

        return try {
            val filePath = File(fileUrl).toPath()
            if (!filePath.startsWith(baseDirectory)) {
                logger.error("Attempted to access file outside allowed directory: $fileUrl")
                return null
            }
            Files.readAllBytes(filePath)
        } catch (e: IOException) {
            logger.error("Failed to read file from path: $fileUrl", e)
            null
        }
    }
}
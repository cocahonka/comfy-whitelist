package com.cocahonka.comfywhitelist.storage.yaml

import com.cocahonka.comfywhitelist.storage.Storage
import com.cocahonka.comfywhitelist.storage.StorageTestBase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.nio.file.Path

class YamlStorageTest : StorageTestBase() {
    private lateinit var storage: Storage
    private lateinit var tempFile: File

    @BeforeEach
    fun setUp(@TempDir tempDir: Path) {
        tempFile = tempDir.resolve("whitelist.yml").toFile()
        storage = YamlStorage(tempFile)
    }

    override fun createStorage(): Storage {
        return storage
    }
}
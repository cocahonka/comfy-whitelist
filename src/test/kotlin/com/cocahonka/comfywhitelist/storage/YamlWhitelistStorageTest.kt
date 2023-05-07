package com.cocahonka.comfywhitelist.storage

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.nio.file.Path

class YamlWhitelistStorageTest : WhitelistStorageTestBase() {
    private lateinit var storage: WhitelistStorage
    private lateinit var tempFile: File

    @BeforeEach
    fun setUp(@TempDir tempDir: Path) {
        tempFile = tempDir.resolve("whitelist.yml").toFile()
        storage = YamlWhitelistStorage(tempFile)
    }

    override fun createStorage(): WhitelistStorage {
        return storage
    }
}
package com.cocahonka.comfywhitelist.storage

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path

class YamlWhitelistStorageTest : WhitelistStorageTestBase() {
    private lateinit var storage: WhitelistStorage

    @BeforeEach
    fun setUp(@TempDir tempDir: Path) {
        storage = YamlWhitelistStorage(tempDir.toFile())
    }

    override fun createStorage(): WhitelistStorage {
        return storage
    }
}
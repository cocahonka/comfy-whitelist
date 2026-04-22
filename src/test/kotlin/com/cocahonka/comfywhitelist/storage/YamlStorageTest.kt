package com.cocahonka.comfywhitelist.storage

import org.mockbukkit.mockbukkit.MockBukkit
import org.mockbukkit.mockbukkit.ServerMock
import com.cocahonka.comfywhitelist.api.Storage
import org.bukkit.plugin.Plugin
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach

class YamlStorageTest : StorageTestBase() {
    private lateinit var storage: Storage
    private lateinit var server: ServerMock
    private lateinit var plugin: Plugin

    @BeforeEach
    fun setUp() {
        server = MockBukkit.mock()
        plugin = MockBukkit.createMockPlugin()
        storage = YamlStorage(plugin.dataFolder)
    }

    @AfterEach
    fun tearDown() {
        MockBukkit.unmock()
    }

    override fun createStorage(): Storage {
        return storage
    }
}
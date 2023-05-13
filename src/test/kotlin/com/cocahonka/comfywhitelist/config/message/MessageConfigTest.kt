package com.cocahonka.comfywhitelist.config.message

import be.seeseemelk.mockbukkit.MockBukkit
import be.seeseemelk.mockbukkit.ServerMock
import com.cocahonka.comfywhitelist.config.base.Locale
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File

class MessageConfigTest {

    private lateinit var messageConfig: MessageConfig
    private lateinit var server: ServerMock
    private lateinit var plugin: Plugin
    private lateinit var locale: Locale

    @BeforeEach
    fun setUp() {
        server = MockBukkit.mock()
        plugin = MockBukkit.createMockPlugin()
        locale = Locale.EN
        messageConfig = MessageConfig(plugin, locale)
    }

    @AfterEach
    fun tearDown() {
        MockBukkit.unmock()
    }

    @Test
    fun `loadConfig sets default messages when config file does not exist`() {
        messageConfig.loadConfig()

        assertEquals(Message.NotWhitelisted.getDefault(locale), MessageConfig.notWhitelisted)
        assertEquals(Message.PlayerAdded.getDefault(locale), MessageConfig.playerAdded)

        val newLocate = Locale.RU
        messageConfig = MessageConfig(plugin, newLocate).apply { loadConfig() }

        assertEquals(Message.NotWhitelisted.getDefault(newLocate), MessageConfig.notWhitelisted)
        assertEquals(Message.PlayerAdded.getDefault(newLocate), MessageConfig.playerAdded)
    }

    @Test
    fun `loadConfig sets correct messages when config file exists`() {
        val configFile = File(plugin.dataFolder, locale.filePath)
        configFile.parentFile.mkdirs()

        val notWhitelistedCustomMessage = "Not whitelisted custom message"
        val playerAddedCustomMessage = "Player added custom message"
        YamlConfiguration.loadConfiguration(configFile).apply {
            set(Message.NotWhitelisted.key, notWhitelistedCustomMessage)
            set(Message.PlayerAdded.key, playerAddedCustomMessage)
            save(configFile)
        }

        messageConfig.loadConfig()

        assertEquals(notWhitelistedCustomMessage, MessageConfig.notWhitelisted)
        assertEquals(playerAddedCustomMessage, MessageConfig.playerAdded)
    }
}

package com.cocahonka.comfywhitelist.config.general

import be.seeseemelk.mockbukkit.MockBukkit
import be.seeseemelk.mockbukkit.ServerMock
import com.cocahonka.comfywhitelist.config.base.Locale
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.nio.file.Path


class GeneralConfigTest {

    private lateinit var generalConfig: GeneralConfig
    private lateinit var server: ServerMock
    private lateinit var plugin: Plugin

    @BeforeEach
    fun setUp(@TempDir tempDirPath: Path) {
        server = MockBukkit.mock()
        plugin = MockBukkit.createMockPlugin()
        generalConfig = GeneralConfig(plugin)

    }

    @AfterEach
    fun tearDown() {
        MockBukkit.unmock()
    }

    @Test
    fun `loadConfig sets default locale when config file does not exist`() {
        generalConfig.loadConfig()
        assertEquals(Locale.EN, GeneralConfig.locale)
    }

    @Test
    fun `loadConfig sets correct locale when config file exists`() {
        val filePath = GeneralConfig::class.java
            .getDeclaredField("filePath")
            .apply { isAccessible = true }
            .get(GeneralConfig.Companion::class.java) as String

        val localeKey = GeneralConfig::class.java
            .getDeclaredField("localeKey")
            .apply { isAccessible = true }
            .get(GeneralConfig.Companion::class.java) as String

        val configFile = File(plugin.dataFolder, filePath)
        configFile.parentFile.mkdirs()

        YamlConfiguration.loadConfiguration(configFile).apply {
            set(localeKey, Locale.RU.code)
            save(configFile)
        }

        generalConfig.loadConfig()
        assertEquals(Locale.RU, GeneralConfig.locale)
    }
}

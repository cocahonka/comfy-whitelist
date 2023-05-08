package com.cocahonka.comfywhitelist.config.message

import com.cocahonka.comfywhitelist.config.base.Locale
import com.cocahonka.comfywhitelist.config.message.Message
import com.cocahonka.comfywhitelist.config.message.Message.Companion.getMessageWithDefault
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MessageTest {
    @Test
    fun `getMessageWithDefault returns default message when configuration is empty`() {
        val locale = Locale.RU
        val defaultMessage = Message.WhitelistDisabled.getDefault(locale)

        val emptyFileConfiguration: FileConfiguration = YamlConfiguration()

        val messageWithDefault = emptyFileConfiguration.getMessageWithDefault(Message.WhitelistDisabled, locale)

        assertEquals(defaultMessage, messageWithDefault)
    }

    @Test
    fun `getMessageWithDefault returns configured message when configuration is not empty`() {
        val locale = Locale.RU
        val configuredMessage = "Configured message"

        val configFileConfiguration: FileConfiguration = YamlConfiguration()
        configFileConfiguration.set(Message.WhitelistDisabled.key, configuredMessage)

        val messageWithDefault = configFileConfiguration.getMessageWithDefault(Message.WhitelistDisabled, locale)

        assertEquals(configuredMessage, messageWithDefault)
    }

}
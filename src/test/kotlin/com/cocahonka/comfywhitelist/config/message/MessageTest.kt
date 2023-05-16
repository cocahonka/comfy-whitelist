package com.cocahonka.comfywhitelist.config.message

import com.cocahonka.comfywhitelist.config.base.Locale
import com.cocahonka.comfywhitelist.config.message.Message.Companion.getDefaultWithPrefix
import com.cocahonka.comfywhitelist.config.message.Message.Companion.getFormattedWithDefault
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MessageTest {
    @Test
    fun `getMessageWithDefault returns default message when configuration is empty`() {
        val locale = Locale.RU
        val defaultMessage = Message.WhitelistDisabled.getDefaultWithPrefix(locale)

        val emptyFileConfiguration: FileConfiguration = YamlConfiguration()

        val messageWithDefault = emptyFileConfiguration.getFormattedWithDefault(Message.WhitelistDisabled, locale)

        assertEquals(defaultMessage, messageWithDefault)
    }

    @Test
    fun `getMessageWithDefault returns configured message when configuration is not empty`() {
        val locale = Locale.RU
        val configuredMessage = "Configured message"
        val configuredMessageComponent = Message.joinWithPrefix(configuredMessage)


        val configFileConfiguration: FileConfiguration = YamlConfiguration()
        configFileConfiguration.set(Message.WhitelistDisabled.key, configuredMessage)

        val messageWithDefault = configFileConfiguration.getFormattedWithDefault(Message.WhitelistDisabled, locale)

        assertEquals(configuredMessageComponent, messageWithDefault)
    }

}
package com.cocahonka.comfywhitelist.config.message

import com.cocahonka.comfywhitelist.config.base.Locale
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.configuration.file.FileConfiguration

/**
 * Sealed class that represents a message to be displayed to the user.
 * @param key The configuration key for the message.
 */
sealed class Message(val key: String) {

    /**
     * Returns the default message for the given locale.
     * @param locale The locale to get the default message for.
     * @return The default message for the specified locale.
     */
    abstract fun getDefault(locale: Locale): Component

    abstract fun applyStyles(rawMessage: String): Component

    companion object {

        /**
         * Retrieves a message from the configuration using the message key, and falls back
         * to the default message for the given locale if the key is not found.
         * @param M The type of message object that extends [Message].
         * @param message The message object containing the key and default message.
         * @param locale The locale to be used for retrieving the default message if the key is not found in the configuration.
         * @return The message string from the configuration if the key exists, otherwise the default message for the specified locale.
         */
        fun <M : Message> FileConfiguration.getMessageWithDefault(
            message: M,
            locale: Locale,
        ): Component {
            val rawMessageFromConfig = this.getString(message.key)

            return if(rawMessageFromConfig == null) {
                message.getDefault(locale)
            } else {
                message.applyStyles(rawMessageFromConfig)
            }
        }

        val miniMessage = MiniMessage.miniMessage()
    }

    object General {
        object NoPermission : Message("no-permission") {
            override fun getDefault(locale: Locale): Component = applyStyles(
                when (locale) {
                    Locale.RU -> "Вы не в вайтлисте."
                    Locale.EN -> "You are not whitelisted."
                }
            )

            override fun applyStyles(rawMessage: String): Component =
                miniMessage.deserialize(
                    rawMessage,
                    MessageTagResolvers.warning
                )
        }

        object InvalidUsage : Message("invalid-usage") {
            override fun getDefault(locale: Locale): Component = applyStyles(
                when (locale) {
                    Locale.RU -> "Вы не в вайтлисте."
                    Locale.EN -> "You are not whitelisted."
                }
            )

            override fun applyStyles(rawMessage: String): Component =
                miniMessage.deserialize(
                    rawMessage,
                    MessageTagResolvers.warning
                )
        }

        object UnknownSubcommand : Message("unknown-subcommand") {
            override fun getDefault(locale: Locale): Component = applyStyles(
                when (locale) {
                    Locale.RU -> "Вы не в вайтлисте."
                    Locale.EN -> "You are not whitelisted."
                }
            )

            override fun applyStyles(rawMessage: String): Component =
                miniMessage.deserialize(
                    rawMessage,
                    MessageTagResolvers.warning
                )
        }

        object InvalidPlayerName : Message("invalid-player-name") {
            override fun getDefault(locale: Locale): Component = applyStyles(
                when (locale) {
                    Locale.RU -> "Вы не в вайтлисте."
                    Locale.EN -> "You are not whitelisted."
                }
            )

            override fun applyStyles(rawMessage: String): Component =
                miniMessage.deserialize(
                    rawMessage,
                    MessageTagResolvers.warning
                )
        }

        object PluginReloaded : Message("plugin-reloaded") {
            override fun getDefault(locale: Locale): Component = applyStyles(
                when (locale) {
                    Locale.RU -> "Вы не в вайтлисте."
                    Locale.EN -> "You are not whitelisted."
                }
            )

            override fun applyStyles(rawMessage: String): Component =
                miniMessage.deserialize(
                    rawMessage,
                    MessageTagResolvers.warning
                )
        }

    }

    object WhitelistStatus {
        object WhitelistEnabled : Message("whitelist-enabled") {
            override fun getDefault(locale: Locale): Component = applyStyles(
                when (locale) {
                    Locale.RU -> "Вы не в вайтлисте."
                    Locale.EN -> "You are not whitelisted."
                }
            )

            override fun applyStyles(rawMessage: String): Component =
                miniMessage.deserialize(
                    rawMessage,
                    MessageTagResolvers.warning
                )
        }

        object WhitelistDisabled : Message("whitelist-disabled") {
            override fun getDefault(locale: Locale): Component = applyStyles(
                when (locale) {
                    Locale.RU -> "Вы не в вайтлисте."
                    Locale.EN -> "You are not whitelisted."
                }
            )

            override fun applyStyles(rawMessage: String): Component =
                miniMessage.deserialize(
                    rawMessage,
                    MessageTagResolvers.warning
                )
        }

        object WhitelistAlreadyEnabled : Message("whitelist-already-enabled") {
            override fun getDefault(locale: Locale): Component = applyStyles(
                when (locale) {
                    Locale.RU -> "Вы не в вайтлисте."
                    Locale.EN -> "You are not whitelisted."
                }
            )

            override fun applyStyles(rawMessage: String): Component =
                miniMessage.deserialize(
                    rawMessage,
                    MessageTagResolvers.warning
                )
        }

        object WhitelistAlreadyDisabled : Message("whitelist-already-disabled") {
            override fun getDefault(locale: Locale): Component = applyStyles(
                when (locale) {
                    Locale.RU -> "Вы не в вайтлисте."
                    Locale.EN -> "You are not whitelisted."
                }
            )

            override fun applyStyles(rawMessage: String): Component =
                miniMessage.deserialize(
                    rawMessage,
                    MessageTagResolvers.warning
                )
        }

    }

    object PlayerManagement {
        object NotWhitelisted : Message("not-whitelisted") {
            override fun getDefault(locale: Locale): Component = applyStyles(
                when (locale) {
                    Locale.RU -> "Вы не в вайтлисте."
                    Locale.EN -> "You are not whitelisted."
                }
            )

            override fun applyStyles(rawMessage: String): Component =
                miniMessage.deserialize(
                    rawMessage,
                    MessageTagResolvers.warning
                )

        }

        object PlayerAdded : Message("player-added") {
            override fun getDefault(locale: Locale): Component = applyStyles(
                when (locale) {
                    Locale.RU -> "Вы не в вайтлисте."
                    Locale.EN -> "You are not whitelisted."
                }
            )

            override fun applyStyles(rawMessage: String): Component =
                miniMessage.deserialize(
                    rawMessage,
                    MessageTagResolvers.warning
                )
        }

        object PlayerRemoved : Message("player-removed") {
            override fun getDefault(locale: Locale): Component = applyStyles(
                when (locale) {
                    Locale.RU -> "Вы не в вайтлисте."
                    Locale.EN -> "You are not whitelisted."
                }
            )

            override fun applyStyles(rawMessage: String): Component =
                miniMessage.deserialize(
                    rawMessage,
                    MessageTagResolvers.warning
                )
        }

        object NonExistentPlayerName : Message("non-existent-player-name") {
            override fun getDefault(locale: Locale): Component = applyStyles(
                when (locale) {
                    Locale.RU -> "Вы не в вайтлисте."
                    Locale.EN -> "You are not whitelisted."
                }
            )

            override fun applyStyles(rawMessage: String): Component =
                miniMessage.deserialize(
                    rawMessage,
                    MessageTagResolvers.warning
                )
        }

    }

    object WhitelistDisplay {
        object WhitelistedPlayersList : Message("whitelisted-players-list") {
            override fun getDefault(locale: Locale): Component = applyStyles(
                when (locale) {
                    Locale.RU -> "Вы не в вайтлисте."
                    Locale.EN -> "You are not whitelisted."
                }
            )

            override fun applyStyles(rawMessage: String): Component =
                miniMessage.deserialize(
                    rawMessage,
                    MessageTagResolvers.warning
                )
        }

        object EmptyWhitelistedPlayersList : Message("empty-whitelisted-players-list") {
            override fun getDefault(locale: Locale): Component = applyStyles(
                when (locale) {
                    Locale.RU -> "Вы не в вайтлисте."
                    Locale.EN -> "You are not whitelisted."
                }
            )

            override fun applyStyles(rawMessage: String): Component =
                miniMessage.deserialize(
                    rawMessage,
                    MessageTagResolvers.warning
                )
        }

        object WhitelistCleared : Message("whitelist-cleared") {
            override fun getDefault(locale: Locale): Component = applyStyles(
                when (locale) {
                    Locale.RU -> "Вы не в вайтлисте."
                    Locale.EN -> "You are not whitelisted."
                }
            )

            override fun applyStyles(rawMessage: String): Component =
                miniMessage.deserialize(
                    rawMessage,
                    MessageTagResolvers.warning
                )
        }
    }

}

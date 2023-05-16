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
                    Locale.RU -> "<warning>У вас недостаточно полномочий для использования этой команды.</warning>"
                    Locale.EN -> "<warning>You do not have permission to use this command.</warning>"
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
                    Locale.RU -> "<warning>Недопустимое использование команды</warning> (правильное использование: <usage>)"
                    Locale.EN -> "<warning>Invalid command usage.</warning> (correct use: <usage>)"
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
                    Locale.RU -> "<warning>Неизвестная подкоманда.</warning> Введите /comfywl help для отображения доступных подкоманд."
                    Locale.EN -> "<warning>Unknown subcommand.</warning> Type /comfywl help for a list of commands."
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
                    Locale.RU -> "<warning>Некорректный формат имени игрока.</warning>"
                    Locale.EN -> "<warning>Invalid player name.</warning>"
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
                    Locale.RU -> "ComfyWhitelist <success>успешно перезагружен.</success>"
                    Locale.EN -> "ComfyWhitelist <success>has been successfully reloaded.</success>"
                }
            )

            override fun applyStyles(rawMessage: String): Component =
                miniMessage.deserialize(
                    rawMessage,
                    MessageTagResolvers.success
                )
        }

    }

    object WhitelistStatus {
        object WhitelistEnabled : Message("whitelist-enabled") {
            override fun getDefault(locale: Locale): Component = applyStyles(
                when (locale) {
                    Locale.RU -> "ComfyWhitelist <success>включен.</success>"
                    Locale.EN -> "ComfyWhitelist <success>enabled.</success>"
                }
            )

            override fun applyStyles(rawMessage: String): Component =
                miniMessage.deserialize(
                    rawMessage,
                    MessageTagResolvers.success
                )
        }

        object WhitelistDisabled : Message("whitelist-disabled") {
            override fun getDefault(locale: Locale): Component = applyStyles(
                when (locale) {
                    Locale.RU -> "ComfyWhitelist <off>выключен.</off>"
                    Locale.EN -> "ComfyWhitelist <off>disabled.</off>"
                }
            )

            override fun applyStyles(rawMessage: String): Component =
                miniMessage.deserialize(
                    rawMessage,
                    MessageTagResolvers.off
                )
        }

        object WhitelistAlreadyEnabled : Message("whitelist-already-enabled") {
            override fun getDefault(locale: Locale): Component = applyStyles(
                when (locale) {
                    Locale.RU -> "ComfyWhitelist <success>уже включен.</success>"
                    Locale.EN -> "ComfyWhitelist <success>already enabled.</success>"
                }
            )

            override fun applyStyles(rawMessage: String): Component =
                miniMessage.deserialize(
                    rawMessage,
                    MessageTagResolvers.success
                )
        }

        object WhitelistAlreadyDisabled : Message("whitelist-already-disabled") {
            override fun getDefault(locale: Locale): Component = applyStyles(
                when (locale) {
                    Locale.RU -> "ComfyWhitelist <off>уже выключен.</off>"
                    Locale.EN -> "ComfyWhitelist <off>already disabled.</off>"
                }
            )

            override fun applyStyles(rawMessage: String): Component =
                miniMessage.deserialize(
                    rawMessage,
                    MessageTagResolvers.off
                )
        }

    }

    object PlayerManagement {
        object NotWhitelisted : Message("not-whitelisted") {
            override fun getDefault(locale: Locale): Component = applyStyles(
                when (locale) {
                    Locale.RU -> "<warning>Вы не в вайтлисте.</warning>"
                    Locale.EN -> "<warning>You are not whitelisted.</warning>"
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
                    Locale.RU -> "Игрок <success><name></success> <success>добавлен</success> в вайтлист."
                    Locale.EN -> "Player <success><name></success> has been <success>added</success> to the whitelist."
                }
            )

            override fun applyStyles(rawMessage: String): Component =
                miniMessage.deserialize(
                    rawMessage,
                    MessageTagResolvers.success
                )
        }

        object PlayerRemoved : Message("player-removed") {
            override fun getDefault(locale: Locale): Component = applyStyles(
                when (locale) {
                    Locale.RU -> "Игрок <remove><name></remove> <remove>удален</remove> из вайтлиста."
                    Locale.EN -> "Player <remove><name></remove> has been <remove>removed</remove> from the whitelist."
                }
            )

            override fun applyStyles(rawMessage: String): Component =
                miniMessage.deserialize(
                    rawMessage,
                    MessageTagResolvers.remove
                )
        }

        object NonExistentPlayerName : Message("non-existent-player-name") {
            override fun getDefault(locale: Locale): Component = applyStyles(
                when (locale) {
                    Locale.RU -> "Игрока с именем <warning><name></warning> <warning>нет</warning> в вайтлисте."
                    Locale.EN -> "There is <warning>no</warning> player named <warning><name></warning> in the whitelist."
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
                    Locale.RU -> "Игроки в вайтлисте: <success><players></success>"
                    Locale.EN -> "Whitelisted players: <success><players></success>"
                }
            )

            override fun applyStyles(rawMessage: String): Component =
                miniMessage.deserialize(
                    rawMessage,
                    MessageTagResolvers.success
                )
        }

        object EmptyWhitelistedPlayersList : Message("empty-whitelisted-players-list") {
            override fun getDefault(locale: Locale): Component = applyStyles(
                when (locale) {
                    Locale.RU -> "В вайтлисте <off>нет игроков.</off>"
                    Locale.EN -> "Whitelist is <off>empty.</off>"
                }
            )

            override fun applyStyles(rawMessage: String): Component =
                miniMessage.deserialize(
                    rawMessage,
                    MessageTagResolvers.off
                )
        }

        object WhitelistCleared : Message("whitelist-cleared") {
            override fun getDefault(locale: Locale): Component = applyStyles(
                when (locale) {
                    Locale.RU -> "Все игроки <remove>удалены</remove> из вайтлиста."
                    Locale.EN -> "All players have been <remove>removed</remove> from the whitelist."
                }
            )

            override fun applyStyles(rawMessage: String): Component =
                miniMessage.deserialize(
                    rawMessage,
                    MessageTagResolvers.remove
                )
        }
    }

}

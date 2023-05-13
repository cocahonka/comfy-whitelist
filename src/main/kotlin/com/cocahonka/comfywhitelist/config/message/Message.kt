package com.cocahonka.comfywhitelist.config.message

import com.cocahonka.comfywhitelist.config.base.Locale
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
    abstract fun getDefault(locale: Locale): String

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
            locale: Locale
        ): String {
            return this.getString(message.key) ?: message.getDefault(locale)
        }
    }

    object NotWhitelisted : Message("not-whitelisted") {
        override fun getDefault(locale: Locale): String = when (locale) {
            Locale.RU -> "Вы не в вайтлисте."
            Locale.EN -> "You are not whitelisted."
        }
    }

    object PlayerAdded : Message("player-added") {
        override fun getDefault(locale: Locale): String = when (locale) {
            Locale.RU -> "Игрок %s добавлен в вайтлист."
            Locale.EN -> "Player %s has been added to the whitelist."
        }
    }

    object PlayerRemoved : Message("player-removed") {
        override fun getDefault(locale: Locale): String = when (locale) {
            Locale.RU -> "Игрок %s удален из вайтлиста."
            Locale.EN -> "Player %s has been removed from the whitelist."
        }
    }

    object WhitelistCleared : Message("whitelist-cleared") {
        override fun getDefault(locale: Locale): String = when (locale) {
            Locale.RU -> "Все игроки удалены из вайтлиста."
            Locale.EN -> "All players have been removed from the whitelist."
        }
    }

    object WhitelistEnabled : Message("whitelist-enabled") {
        override fun getDefault(locale: Locale): String = when (locale) {
            Locale.RU -> "ComfyWhitelist включен."
            Locale.EN -> "ComfyWhitelist enabled."
        }
    }

    object WhitelistDisabled : Message("whitelist-disabled") {
        override fun getDefault(locale: Locale): String = when (locale) {
            Locale.RU -> "ComfyWhitelist выключен."
            Locale.EN -> "ComfyWhitelist disabled."
        }
    }

    object PluginReloaded : Message("plugin-reloaded") {
        override fun getDefault(locale: Locale): String = when (locale) {
            Locale.RU -> "ComfyWhitelist успешно перезагружен."
            Locale.EN -> "ComfyWhitelist has been successfully reloaded."
        }
    }

    object NoPermission : Message("no-permission") {
        override fun getDefault(locale: Locale): String = when(locale) {
            Locale.RU -> "У вас недостаточно полномочий для использования этой команды."
            Locale.EN -> "You do not have permission to use this command."
        }
    }

    object InvalidUsage : Message("invalid-usage") {
        override fun getDefault(locale: Locale): String = when(locale) {
            Locale.RU -> "Недопустимое использование команды (правильное использование: %s)"
            Locale.EN -> "Invalid command usage. (correct use: %s)"
        }
    }

    object WhitelistedPlayersList : Message("whitelisted-players-list") {
        override fun getDefault(locale: Locale): String = when(locale) {
            Locale.RU -> "Игроки в вайтлисте: %s"
            Locale.EN -> "Whitelisted players: %s"
        }
    }

    object EmptyWhitelistedPlayersList : Message("empty-whitelisted-players-list") {
        override fun getDefault(locale: Locale): String = when(locale) {
            Locale.RU -> "В вайтлисте нет игроков."
            Locale.EN -> "Whitelist is empty."
        }
    }

    object UnknownSubcommand: Message("unknown-subcommand") {
        override fun getDefault(locale: Locale): String = when(locale) {
            Locale.RU -> "Неизвестная подкоманда. Введите /comfywl help для отображения доступных подкоманд."
            Locale.EN -> "Unknown subcommand. Type /comfywl help for a list of commands."
        }
    }

    object InvalidPlayerName: Message("invalid-player-name") {
        override fun getDefault(locale: Locale): String = when(locale) {
            Locale.RU -> "Некорректный формат имени игрока."
            Locale.EN -> "Invalid player name."
        }
    }

    object NonExistentPlayerName : Message("non-existent-player-name") {
        override fun getDefault(locale: Locale): String = when(locale) {
            Locale.RU -> "Игрока с именем %s нет в вайтлисте."
            Locale.EN -> "There is no player named %s in the whitelist."
        }
    }

    object WhitelistAlreadyEnabled : Message("whitelist-already-enabled") {
        override fun getDefault(locale: Locale): String = when(locale) {
            Locale.RU -> "ComfyWhitelist уже включен."
            Locale.EN -> "ComfyWhitelist already enabled."
        }
    }

    object WhitelistAlreadyDisabled : Message("whitelist-already-disabled") {
        override fun getDefault(locale: Locale): String = when(locale) {
            Locale.RU -> "ComfyWhitelist уже выключен."
            Locale.EN -> "ComfyWhitelist already disabled."
        }
    }

}

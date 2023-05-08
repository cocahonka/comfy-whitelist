package com.cocahonka.comfywhitelist.config

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
}

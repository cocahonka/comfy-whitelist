package com.cocahonka.comfywhitelist.config.base

import org.bukkit.Bukkit.getLogger

/**
 * Enum class that represents a supported locale.
 * @param code The locale code as a string.
 * @param filePath The path to the configuration file for the locale.
 */
enum class Locale(val code: String, val filePath: String) {
    RU("ru", "locales/messages_ru.yml"),
    EN("en", "locales/messages_en.yml");

    companion object {

        /**
         * Retrieves the Locale object based on the provided value.
         * If no matching locale is found, the default English locale is returned.
         * @param value The value to find a matching Locale object for.
         * @return The matching Locale object or the default English locale if not found.
         */
        fun fromString(value: String?): Locale {
            val locale = values().find { it.name.equals(value, true) }
            return if (locale == null){
                getLogger().warning("locale '$value' does not exist!\n" +
                        "so the english locale will be loaded")
                EN
            } else {
                locale
            }
        }
    }
}

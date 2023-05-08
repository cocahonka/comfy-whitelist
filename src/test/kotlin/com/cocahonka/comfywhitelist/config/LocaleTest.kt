package com.cocahonka.comfywhitelist.config

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import be.seeseemelk.mockbukkit.MockBukkit
import be.seeseemelk.mockbukkit.ServerMock
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.util.logging.Logger


class LocaleTest {
    private lateinit var server: ServerMock
    private lateinit var logger: Logger

    @BeforeEach
    fun setUp() {
        server = MockBukkit.mock()
        logger = server.logger
    }

    @AfterEach
    fun tearDown() {
        MockBukkit.unmock()
    }

    @Test
    fun `fromString should return correct locale`() {
        assertEquals(Locale.EN, Locale.fromString("en"))
        assertEquals(Locale.RU, Locale.fromString("ru"))
    }

    @Test
    fun `fromString should return default locale for non-existent locale`() {
        val outputStream = ByteArrayOutputStream()
        val printStream = PrintStream(outputStream)
        val locale = "non_existent_locale"
        System.setErr(printStream)

        assertEquals(Locale.EN, Locale.fromString(locale))

        System.setErr(System.err)

        val output = outputStream.toString()
        assert(output.contains("locale '$locale' does not exist!"))
        assert(output.contains("so the english locale will be loaded"))
    }

    @Test
    fun `fromString should be case-insensitive`() {
        assertEquals(Locale.EN, Locale.fromString("EN"))
        assertEquals(Locale.RU, Locale.fromString("RU"))
    }
}

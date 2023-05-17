import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.apache.tools.ant.filters.FixCrLfFilter
import org.apache.tools.ant.filters.ReplaceTokens
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.21"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.cocahonka"
version = "0.0.1"

repositories {
    maven(url = "https://repo.papermc.io/repository/maven-public/")
    maven(url = "https://jitpack.io")
}

val paperApiVersion: String by project
val mockBukkitVersion: String by project
dependencies {
    compileOnly("io.papermc.paper:paper-api:$paperApiVersion")

    implementation("com.github.cocahonka:comfy-whitelist-api:af9d085e3f")

    testImplementation(kotlin("test"))

    testImplementation("com.github.seeseemelk:MockBukkit-v$mockBukkitVersion")
}

tasks {
    test {
        useJUnitPlatform()
    }
    processResources {
        filter(FixCrLfFilter::class)
        filter(ReplaceTokens::class, "tokens" to mapOf("version" to project.version))
        filteringCharset = "UTF-8"
    }
    jar {
        enabled = false
    }

    val shadowJar by existing(ShadowJar::class) {
        archiveFileName.set("ComfyWhitelist-$version.jar")
        destinationDirectory.set(layout.buildDirectory.dir("../../paper_server/plugins"))
    }

    build {
        dependsOn(shadowJar)
    }
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "17"
        }
    }
}

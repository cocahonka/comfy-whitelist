import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.apache.tools.ant.filters.FixCrLfFilter
import org.apache.tools.ant.filters.ReplaceTokens
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.10"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.cocahonka"
version = "1.0.0"

repositories {
    maven(url = "https://repo.papermc.io/repository/maven-public/")
    maven(url = "https://jitpack.io")
}

val paperApiVersion: String by project
val mockBukkitVersion: String by project
val comfyWhitelistApiVersion: String by project
dependencies {
    compileOnly("io.papermc.paper:paper-api:$paperApiVersion")

    implementation("com.github.cocahonka:comfy-whitelist-api:$comfyWhitelistApiVersion")

    testImplementation(kotlin("test"))

    testImplementation("com.github.seeseemelk:MockBukkit-v$mockBukkitVersion")
}

configurations {
    create("withoutKotlinStdlib") {
        extendsFrom(configurations.runtimeClasspath.get())
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib-jdk8")
    }
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
        archiveFileName.set("ComfyWhitelist-$version-standalone.jar")
        destinationDirectory.set(layout.buildDirectory.dir("libs"))
    }

    val shadowJarLightweight by creating(ShadowJar::class) {
        archiveFileName.set("ComfyWhitelist-$version-lightweight.jar")
        destinationDirectory.set(layout.buildDirectory.dir("libs"))
        configurations = listOf(project.configurations["withoutKotlinStdlib"])
        from(sourceSets.main.get().output)

        exclude("META-INF/*.kotlin_module")
        exclude("META-INF/*.DSA")
        exclude("META-INF/*.RSA")
        exclude("META-INF/*.SF")
    }

    register("standalone") {
        group = "build"
        dependsOn(shadowJar)
    }

    register("lightweight") {
        group = "build"
        dependsOn(shadowJarLightweight)
    }

    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "17"
        }
    }
}

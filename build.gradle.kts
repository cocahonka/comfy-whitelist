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
}

val paperApiVersion: String by project
val striktCoreVersion: String by project
dependencies {
    compileOnly("io.papermc.paper:paper-api:$paperApiVersion")

    testImplementation(kotlin("test"))

    testImplementation("io.strikt:strikt-core:$striktCoreVersion")

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

plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    id("com.gradle.plugin-publish") version "2.2.1"
}

group = "at.hugob"
version = "0.0.0"

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation("org.danilopianini:publish-on-central:9.+")
    implementation("com.gradleup.shadow:shadow-gradle-plugin:9+")
}

gradlePlugin {
    website.set("https://github.com/Hugo5000/GradleCommonPlugin")
    vcsUrl.set("https://github.com/Hugo5000/GradleCommonPlugin")
    plugins {
        register("common") {
            id = "at.hugob.common"
            implementationClass = "at.hugob.CommonPlugin"
            displayName = "Hugos Common Gradle Plugin"
            description = "Basic configuration for Hugos java plugins!"
            tags.set(listOf("mine"))
        }
        register("publish") {
            displayName = "Hugos Publish Gradle Plugin"
            id = "at.hugob.publish"
            implementationClass = "at.hugob.PublishPlugin"
            description = "Basic configuration for Hugos publishing for libraries!"
            tags.set(listOf("mine"))
        }
        register("shadow") {
            displayName = "Hugos Shadow Gradle Plugin"
            id = "at.hugob.shadow"
            implementationClass = "at.hugob.ShadowPlugin"
            description = "Basic configuration for Hugos for using Shadow!"
            tags.set(listOf("mine"))
        }
    }
}
publishing {
    repositories {
        maven {
            name = "localRepo"
            url = layout.projectDirectory.dir("../local-repo").asFile.toURI()
        }
    }
}
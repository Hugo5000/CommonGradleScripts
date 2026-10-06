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
}

gradlePlugin {
    website.set("https://github.com/Hugo5000/GradleCommonPlugin")
    vcsUrl.set("https://github.com/Hugo5000/GradleCommonPlugin")
    plugins {
        register("at.hugob.common") {
            id = "at.hugob.common"
            implementationClass = "at.hugob.CommonPlugin"
            displayName = "Hugos Common Gradle Plugin"
            description = "Basic configuration for Hugos java plugins!"
            tags.set(listOf("mine"))
        }
        register("at.hugob.publish") {
            displayName = "Hugos Publish Gradle Plugin"
            id = "at.hugob.publish"
            implementationClass = "at.hugob.PublishPlugin"
            description = "Basic configuration for Hugos publishing for libraries!"
            tags.set(listOf("mine"))
        }
    }
}

//pluginManagement {
//    resolutionStrategy {
//        eachPlugin {
//            if (requested.id.namespace == "com.example") {
//                useModule("com.example:sample-plugins:1.0.0")
//            }
//        }
//    }
//    repositories {
//        maven {
//            url = uri("./maven-repo")
//        }
//        gradlePluginPortal()
//        ivy {
//            url = uri("./ivy-repo")
//        }
//    }
//}
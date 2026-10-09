package at.hugob

import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.api.Plugin
import org.gradle.api.Project

class ShadowPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.pluginManager.apply("com.gradleup.shadow")

        val extension = project.extensions.create(
            "customShadow",
            ShadowPluginExtension::class.java
        )

        val shadowJar = project.tasks.named(
            "shadowJar",
            ShadowJar::class.java
        ) {
            archiveClassifier.set("")
            extension.relocations.forEach { (source, target) ->
                relocate(source, target)
            }
        }

        project.tasks.named("build") {
            dependsOn(shadowJar)
        }
    }
}

open class ShadowPluginExtension {
    var relocations: MutableMap<String, String> = mutableMapOf()
    fun relocate(from: String, to: String) {
        relocations[from] = to
    }
}

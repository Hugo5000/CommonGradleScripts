package at.hugob

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.api.tasks.javadoc.Javadoc
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.gradle.plugins.ide.idea.model.IdeaModel

class CommonPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.pluginManager.apply("java")
        project.pluginManager.apply("idea")

        project.repositories.mavenCentral()

        project.dependencies.add("compileOnly", "org.jetbrains:annotations:26.+")
        project.dependencies.add("compileOnly", "at.hugob:annotations:+")

        project.extensions.configure<JavaPluginExtension> {
            toolchain {
                languageVersion.set(JavaLanguageVersion.of(21))
            }
            withSourcesJar()
            withJavadocJar()
        }
        project.extensions.configure<IdeaModel> {
            module {
                isDownloadJavadoc = true
                isDownloadSources = true
            }
        }
        project.extensions.configure<SourceSetContainer> {
            named("main") {
                java.srcDir("src")
                resources.srcDir("resources")
            }
            named("test") {
                java.srcDir("test")
            }
        }
        project.tasks.withType<JavaCompile> {
            options.compilerArgs.add("-parameters")
            options.encoding = "UTF-8"
        }
        project.tasks.withType<Javadoc> {
            options.encoding = "UTF-8"
        }
    }
}

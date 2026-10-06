package at.hugob

import org.danilopianini.gradle.mavencentral.PublishOnCentralExtension
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.provider.Property
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.withType
import org.gradle.plugins.signing.SigningExtension
import javax.inject.Inject

class PublishPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.pluginManager.apply {
            apply("signing")
            apply("maven-publish")
            apply("org.danilopianini.publish-on-central") // version 9.+ ...
        }

        val githubUsername = project.property("githubUsername") as String
        val githubToken = project.property("githubToken") as String

        val publishName = project.property("name") as String
        val gitrepo = project.property("gitrepo") as String
        val description = project.property("description") as String

        val publish = project.extensions.create<PublishExtension>("publish")

        project.repositories.mavenCentral()

        project.extensions.configure<JavaPluginExtension> {
            withSourcesJar()
            withJavadocJar()
        }

        project.extensions.configure<PublishingExtension> {
            publications {
                withType<MavenPublication> {
                    pom {
                        developers {
                            publish.developers.forEach { dev ->
                                developer {
                                    if (dev.name.isPresent) {
                                        name.set(dev.name.get())
                                    }
                                    if (dev.email.isPresent) {
                                        email.set(dev.email)
                                    }
                                    if (dev.url.isPresent) {
                                        url.set(dev.url.get())
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        project.extensions.configure<PublishOnCentralExtension> {
            repoOwner.set("Hugo5000") // Used to populate the default value for projectUrl and scmConnection
            projectDescription.set(description)
            // The following values are the default, if they are ok with you, just omit them
            projectLongName.set(publishName)
            licenseName.set("GNU General Public License version 3")
            licenseUrl.set("https://opensource.org/license/gpl-3-0/")
            projectUrl.set("https://github.com/${gitrepo}")
            scmConnection.set("scm:git:https://github.com/${gitrepo}")

            /*
             * The publications can be sent to other destinations, e.g. GitHub
             * The task name would be 'publishAllPublicationsToGitHubRepository'
             */
            repository("https://maven.pkg.github.com/OWNER/REPOSITORY", "GitHub") {
                user.set(githubUsername)
                password.set(githubToken)
            }
        }

        project.extensions.configure<SigningExtension> {
            val signingKey = project.property("signingKey") as String
            val signingPassword = project.property("signingPassword") as String
            useInMemoryPgpKeys(signingKey, signingPassword)
        }
    }
}
abstract class PublishExtension {
    abstract var developers: NamedDomainObjectContainer<Developer>

    @Inject
    constructor(objects: ObjectFactory) {
        developers = objects.domainObjectContainer(Developer::class.java)
    }
}

abstract class Developer {
    abstract val name: Property<String>
    abstract val email: Property<String>
    abstract val url: Property<String>
}
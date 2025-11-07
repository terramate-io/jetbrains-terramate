plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.21"
    id("org.jetbrains.intellij.platform") version "2.2.1"
}

group = "io.terramate"
version = "0.0.1"

repositories {
    mavenCentral()
    
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    
    intellijPlatform {
        intellijIdeaCommunity("2024.2")
        
        // LSP4IJ for Language Server Protocol support
        plugin("com.redhat.devtools.lsp4ij:0.13.0")
    }
}

// Configure Gradle IntelliJ Platform Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin.html
intellijPlatform {
    buildSearchableOptions = false
    
    pluginConfiguration {
        version = "0.0.1"
        
        ideaVersion {
            sinceBuild = "242"  // IntelliJ 2024.2 minimum for lsp4ij
            untilBuild = provider { null }  // Support all future IDE versions
        }
        
        changeNotes = """
            <h3>0.0.1</h3>
            <ul>
                <li>Initial release</li>
                <li>Syntax highlighting for Terramate files (.tm, .tm.hcl, .tmgen)</li>
                <li>Language Server Protocol integration with terramate-ls</li>
                <li>Code completion and validation</li>
                <li>Configurable language server settings</li>
            </ul>
        """.trimIndent()
    }
    
    pluginVerification {
        ides {
            // Test against multiple recent IDE versions for compatibility
            ide("IC-2024.2")  // IntelliJ IDEA Community 2024.2 (minimum supported)
            ide("IC-2024.3")  // IntelliJ IDEA Community 2024.3
            ide("IC-2025.1")  // IntelliJ IDEA Community 2025.1
            ide("IC-2025.2")  // IntelliJ IDEA Community 2025.2 (latest)
            ide("IU-2025.2")  // IntelliJ IDEA Ultimate 2025.2 (latest)
        }
    }
    
    signing {
        certificateChain = providers.environmentVariable("CERTIFICATE_CHAIN")
        privateKey = providers.environmentVariable("PRIVATE_KEY")
        password = providers.environmentVariable("PRIVATE_KEY_PASSWORD")
    }
    
    publishing {
        token = providers.environmentVariable("PUBLISH_TOKEN")
    }
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "21"
        targetCompatibility = "21"
    }

    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "21"
    }
}

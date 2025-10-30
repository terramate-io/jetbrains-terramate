package io.terramate.intellij.lsp

import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.openapi.project.Project
import com.redhat.devtools.lsp4ij.LanguageServerFactory
import com.redhat.devtools.lsp4ij.client.LanguageClientImpl
import com.redhat.devtools.lsp4ij.server.OSProcessStreamConnectionProvider
import com.redhat.devtools.lsp4ij.server.StreamConnectionProvider
import io.terramate.intellij.settings.TerramateSettings
import java.io.File

/**
 * Language server factory for Terramate.
 * Creates connection provider for terramate-ls language server.
 */
class TerramateLspServerFactory : LanguageServerFactory {

    override fun createConnectionProvider(project: Project): StreamConnectionProvider {
        return TerramateLanguageServer(project)
    }

    override fun createLanguageClient(project: Project): LanguageClientImpl {
        return TerramateLanguageClient(project)
    }
}

/**
 * Connection provider for terramate-ls language server.
 * Extends OSProcessStreamConnectionProvider for automatic process management.
 */
class TerramateLanguageServer(private val project: Project) : OSProcessStreamConnectionProvider() {

    init {
        try {
            val settings = TerramateSettings.getInstance()
            val serverPath = findLanguageServerPath(settings)

            val args = if (settings.languageServerArgs.isNotEmpty()) {
                settings.languageServerArgs
            } else {
                listOf("-mode=stdio")
            }

            val commandLine = GeneralCommandLine()
            commandLine.exePath = serverPath
            commandLine.addParameters(args)
            commandLine.withWorkDirectory(project.basePath)
            commandLine.withParentEnvironmentType(GeneralCommandLine.ParentEnvironmentType.CONSOLE)

            setCommandLine(commandLine)

            // Log successful initialization
            println("Terramate LS: Initialized with server at: $serverPath")
            println("Terramate LS: Arguments: $args")
            println("Terramate LS: Working directory: ${project.basePath}")
        } catch (e: Exception) {
            println("Terramate LS: Failed to initialize: ${e.message}")
            e.printStackTrace()
            throw e
        }
    }

    private fun findLanguageServerPath(settings: TerramateSettings): String {
        // Check custom path first
        if (settings.languageServerBinPath.isNotEmpty()) {
            val customPath = File(settings.languageServerBinPath)
            println("Terramate LS: Checking custom path: ${settings.languageServerBinPath}")
            if (customPath.exists() && customPath.canExecute()) {
                println("Terramate LS: Found at custom path")
                return settings.languageServerBinPath
            }
            val error = "Language server binary not found at: ${settings.languageServerBinPath}"
            println("Terramate LS: $error")
            throw IllegalStateException(error)
        }

        // Search in PATH
        val serverName = if (System.getProperty("os.name").lowercase().contains("win")) {
            "terramate-ls.exe"
        } else {
            "terramate-ls"
        }

        println("Terramate LS: Searching for '$serverName' in PATH")
        val pathEnv = System.getenv("PATH") ?: ""
        println("Terramate LS: PATH = $pathEnv")
        val paths = pathEnv.split(File.pathSeparator)

        for (pathDir in paths) {
            val serverFile = File(pathDir, serverName)
            if (serverFile.exists() && serverFile.canExecute()) {
                val foundPath = serverFile.absolutePath
                println("Terramate LS: Found at: $foundPath")
                return foundPath
            }
        }

        val error = "terramate-ls not found in PATH. Please install it from https://terramate.io/docs/cli/installation " +
                "or configure a custom path in Settings > Languages & Frameworks > Terramate"
        println("Terramate LS: $error")
        throw IllegalStateException(error)
    }
}

/**
 * Custom language client for Terramate with enhanced logging.
 */
class TerramateLanguageClient(project: Project) : LanguageClientImpl(project) {

    override fun logMessage(message: org.eclipse.lsp4j.MessageParams) {
        val level = when (message.type) {
            org.eclipse.lsp4j.MessageType.Error -> "ERROR"
            org.eclipse.lsp4j.MessageType.Warning -> "WARN"
            org.eclipse.lsp4j.MessageType.Info -> "INFO"
            org.eclipse.lsp4j.MessageType.Log -> "LOG"
            else -> "UNKNOWN"
        }
        println("Terramate LS [$level]: ${message.message}")
        super.logMessage(message)
    }
}

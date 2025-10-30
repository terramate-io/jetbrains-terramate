package io.terramate.intellij.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages
import com.redhat.devtools.lsp4ij.LanguageServerManager

/**
 * Action to restart the Terramate language server.
 * Useful when the server crashes or needs to be restarted after configuration changes.
 */
class RestartLanguageServerAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return

        try {
            val lsManager = LanguageServerManager.getInstance(project)

            // Stop and restart the Terramate language server by ID
            lsManager.stop("terramateLanguageServer")
            lsManager.start("terramateLanguageServer")

            Messages.showInfoMessage(
                project,
                "Terramate language server has been restarted.",
                "Language Server Restarted"
            )
        } catch (ex: Exception) {
            Messages.showErrorDialog(
                project,
                "Failed to restart language server: ${ex.message}",
                "Restart Failed"
            )
        }
    }
}

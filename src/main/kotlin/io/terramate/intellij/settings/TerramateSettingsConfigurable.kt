package io.terramate.intellij.settings

import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import javax.swing.JComponent
import javax.swing.JPanel

/**
 * Settings UI for Terramate plugin.
 * Provides configuration options for the language server.
 */
class TerramateSettingsConfigurable : Configurable {
    private val settings = TerramateSettings.getInstance()

    private val enabledCheckbox = JBCheckBox("Enable Language Server")
    private val binPathField = TextFieldWithBrowseButton()
    private val argsField = JBTextField()
    private val traceComboBox = ComboBox(arrayOf("off", "messages", "verbose"))

    private var settingsPanel: JPanel? = null

    init {
        binPathField.addBrowseFolderListener(
            "Select terramate-ls Binary",
            "Select the path to the terramate-ls executable",
            null,
            FileChooserDescriptorFactory.createSingleFileDescriptor()
        )
    }

    override fun getDisplayName(): String = "Terramate"

    override fun createComponent(): JComponent {
        settingsPanel = FormBuilder.createFormBuilder()
            .addComponent(enabledCheckbox)
            .addSeparator()
            .addLabeledComponent("Binary Path:", binPathField, 1, false)
            .addTooltip("Path to terramate-ls binary. Leave empty to auto-detect from PATH.")
            .addLabeledComponent("Arguments:", argsField, 1, false)
            .addTooltip("Command-line arguments for terramate-ls (comma-separated)")
            .addLabeledComponent("Trace Level:", traceComboBox, 1, false)
            .addTooltip("LSP trace level for debugging")
            .addComponentFillVertically(JPanel(), 0)
            .panel

        reset()
        return settingsPanel!!
    }

    override fun isModified(): Boolean {
        return enabledCheckbox.isSelected != settings.languageServerEnabled ||
                binPathField.text != settings.languageServerBinPath ||
                argsField.text != settings.languageServerArgs.joinToString(", ") ||
                traceComboBox.selectedItem != settings.languageServerTrace
    }

    override fun apply() {
        settings.languageServerEnabled = enabledCheckbox.isSelected
        settings.languageServerBinPath = binPathField.text
        settings.languageServerArgs = argsField.text.split(",").map { it.trim() }.filter { it.isNotEmpty() }
        settings.languageServerTrace = traceComboBox.selectedItem as String
    }

    override fun reset() {
        enabledCheckbox.isSelected = settings.languageServerEnabled
        binPathField.text = settings.languageServerBinPath
        argsField.text = settings.languageServerArgs.joinToString(", ")
        traceComboBox.selectedItem = settings.languageServerTrace
    }
}

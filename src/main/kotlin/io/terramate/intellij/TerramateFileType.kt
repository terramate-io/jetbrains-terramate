package io.terramate.intellij

import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

/**
 * File type definition for Terramate files.
 */
object TerramateFileType : LanguageFileType(TerramateLanguage) {
    override fun getName() = "Terramate"

    override fun getDescription() = "Terramate configuration file"

    override fun getDefaultExtension() = "tm"

    override fun getIcon(): Icon = TerramateIcons.FILE
}

package io.terramate.intellij

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.psi.FileViewProvider

/**
 * PSI file representation for Terramate files.
 */
class TerramateFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, TerramateLanguage) {
    override fun getFileType() = TerramateFileType

    override fun toString() = "Terramate File"
}

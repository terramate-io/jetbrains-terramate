package io.terramate.intellij

import com.intellij.ide.IconProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import javax.swing.Icon

/**
 * Provides custom icons for Terramate files.
 */
class TerramateIconProvider : IconProvider() {
    override fun getIcon(element: PsiElement, flags: Int): Icon? {
        val file = element as? PsiFile ?: return null
        return if (file.fileType == TerramateFileType) TerramateIcons.FILE else null
    }
}

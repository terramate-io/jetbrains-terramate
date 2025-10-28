package io.terramate.intellij

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode

/**
 * Base PSI element for Terramate language.
 */
class TerramatePsiElement(node: ASTNode) : ASTWrapperPsiElement(node)

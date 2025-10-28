package io.terramate.intellij

import com.intellij.lang.Language

/**
 * Language definition for Terramate.
 */
object TerramateLanguage : Language("Terramate") {
    private fun readResolve(): Any = TerramateLanguage

    override fun getDisplayName() = "Terramate"

    override fun isCaseSensitive() = true
}

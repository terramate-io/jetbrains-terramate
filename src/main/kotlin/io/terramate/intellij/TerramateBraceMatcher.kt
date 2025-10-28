package io.terramate.intellij

import com.intellij.lang.BracePair
import com.intellij.lang.PairedBraceMatcher
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IElementType

/**
 * Provides brace matching for Terramate language.
 */
class TerramateBraceMatcher : PairedBraceMatcher {
    override fun getPairs() = arrayOf(
        BracePair(TerramateTokenTypes.LBRACE, TerramateTokenTypes.RBRACE, true),
        BracePair(TerramateTokenTypes.LBRACK, TerramateTokenTypes.RBRACK, false),
        BracePair(TerramateTokenTypes.LPAREN, TerramateTokenTypes.RPAREN, false)
    )

    override fun isPairedBracesAllowedBeforeType(lbraceType: IElementType, contextType: IElementType?) = true

    override fun getCodeConstructStart(file: PsiFile, openingBraceOffset: Int) = openingBraceOffset
}

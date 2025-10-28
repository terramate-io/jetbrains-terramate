package io.terramate.intellij

import com.intellij.psi.tree.IElementType
import com.intellij.psi.TokenType

/**
 * Token types for Terramate language.
 */
object TerramateTokenTypes {
    @JvmField val LBRACE = TerramateTokenType("LBRACE")
    @JvmField val RBRACE = TerramateTokenType("RBRACE")
    @JvmField val LBRACK = TerramateTokenType("LBRACK")
    @JvmField val RBRACK = TerramateTokenType("RBRACK")
    @JvmField val LPAREN = TerramateTokenType("LPAREN")
    @JvmField val RPAREN = TerramateTokenType("RPAREN")

    @JvmField val LINE_COMMENT = TerramateTokenType("LINE_COMMENT")
    @JvmField val BLOCK_COMMENT = TerramateTokenType("BLOCK_COMMENT")

    @JvmField val IDENTIFIER = TerramateTokenType("IDENTIFIER")
    @JvmField val STRING = TerramateTokenType("STRING")
    @JvmField val NUMBER = TerramateTokenType("NUMBER")

    @JvmField val KEYWORD = TerramateTokenType("KEYWORD")
    @JvmField val OPERATOR = TerramateTokenType("OPERATOR")

    @JvmField val WHITESPACE = TokenType.WHITE_SPACE
    @JvmField val BAD_CHARACTER = TokenType.BAD_CHARACTER
}

class TerramateTokenType(debugName: String) : IElementType(debugName, TerramateLanguage) {
    override fun toString() = "TerramateTokenType." + super.toString()
}

package io.terramate.intellij

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.tree.IElementType

/**
 * Syntax highlighter for Terramate language.
 * Based on the TextMate grammar from the VSCode extension.
 */
class TerramateSyntaxHighlighter : SyntaxHighlighterBase() {
    override fun getHighlightingLexer(): Lexer = TerramateLexer()

    override fun getTokenHighlights(tokenType: IElementType?): Array<TextAttributesKey> {
        return when (tokenType) {
            TerramateTokenTypes.LINE_COMMENT, TerramateTokenTypes.BLOCK_COMMENT -> COMMENT_KEYS
            TerramateTokenTypes.STRING -> STRING_KEYS
            TerramateTokenTypes.NUMBER -> NUMBER_KEYS
            TerramateTokenTypes.KEYWORD -> KEYWORD_KEYS
            TerramateTokenTypes.OPERATOR -> OPERATOR_KEYS
            TerramateTokenTypes.LBRACE, TerramateTokenTypes.RBRACE -> BRACES_KEYS
            TerramateTokenTypes.LBRACK, TerramateTokenTypes.RBRACK -> BRACKETS_KEYS
            TerramateTokenTypes.LPAREN, TerramateTokenTypes.RPAREN -> PARENS_KEYS
            TerramateTokenTypes.BAD_CHARACTER -> BAD_CHAR_KEYS
            else -> EMPTY_KEYS
        }
    }

    companion object {
        val COMMENT = TextAttributesKey.createTextAttributesKey("TERRAMATE_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT)
        val STRING = TextAttributesKey.createTextAttributesKey("TERRAMATE_STRING", DefaultLanguageHighlighterColors.STRING)
        val NUMBER = TextAttributesKey.createTextAttributesKey("TERRAMATE_NUMBER", DefaultLanguageHighlighterColors.NUMBER)
        val KEYWORD = TextAttributesKey.createTextAttributesKey("TERRAMATE_KEYWORD", DefaultLanguageHighlighterColors.KEYWORD)
        val OPERATOR = TextAttributesKey.createTextAttributesKey("TERRAMATE_OPERATOR", DefaultLanguageHighlighterColors.OPERATION_SIGN)
        val BRACES = TextAttributesKey.createTextAttributesKey("TERRAMATE_BRACES", DefaultLanguageHighlighterColors.BRACES)
        val BRACKETS = TextAttributesKey.createTextAttributesKey("TERRAMATE_BRACKETS", DefaultLanguageHighlighterColors.BRACKETS)
        val PARENS = TextAttributesKey.createTextAttributesKey("TERRAMATE_PARENS", DefaultLanguageHighlighterColors.PARENTHESES)
        val BAD_CHARACTER = TextAttributesKey.createTextAttributesKey("TERRAMATE_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER)

        private val COMMENT_KEYS = arrayOf(COMMENT)
        private val STRING_KEYS = arrayOf(STRING)
        private val NUMBER_KEYS = arrayOf(NUMBER)
        private val KEYWORD_KEYS = arrayOf(KEYWORD)
        private val OPERATOR_KEYS = arrayOf(OPERATOR)
        private val BRACES_KEYS = arrayOf(BRACES)
        private val BRACKETS_KEYS = arrayOf(BRACKETS)
        private val PARENS_KEYS = arrayOf(PARENS)
        private val BAD_CHAR_KEYS = arrayOf(BAD_CHARACTER)
        private val EMPTY_KEYS = emptyArray<TextAttributesKey>()
    }
}

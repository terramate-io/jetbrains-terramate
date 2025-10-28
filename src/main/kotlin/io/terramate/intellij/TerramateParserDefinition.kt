package io.terramate.intellij

import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet

/**
 * Parser definition for Terramate language.
 * This is a minimal implementation since we rely on the LSP for most parsing functionality.
 */
class TerramateParserDefinition : ParserDefinition {
    override fun createLexer(project: Project?): Lexer = TerramateLexer()

    override fun createParser(project: Project?): PsiParser = TerramateParser()

    override fun getFileNodeType(): IFileElementType = FILE

    override fun getCommentTokens(): TokenSet = COMMENTS

    override fun getStringLiteralElements(): TokenSet = STRINGS

    override fun createElement(node: ASTNode?): PsiElement = TerramatePsiElement(node!!)

    override fun createFile(viewProvider: FileViewProvider): PsiFile = TerramateFile(viewProvider)

    override fun getWhitespaceTokens(): TokenSet = WHITE_SPACE

    companion object {
        val FILE = IFileElementType(TerramateLanguage)
        val COMMENTS = TokenSet.create(TerramateTokenTypes.LINE_COMMENT, TerramateTokenTypes.BLOCK_COMMENT)
        val STRINGS = TokenSet.create(TerramateTokenTypes.STRING)
        val WHITE_SPACE = TokenSet.create(TerramateTokenTypes.WHITESPACE)
    }
}

package io.terramate.intellij

import com.intellij.lexer.LexerBase
import com.intellij.psi.tree.IElementType

/**
 * Lexer for Terramate language.
 * Provides proper tokenization for syntax highlighting.
 */
class TerramateLexer : LexerBase() {
    private var buffer: CharSequence = ""
    private var startOffset = 0
    private var endOffset = 0
    private var tokenStart = 0
    private var tokenEnd = 0
    private var tokenType: IElementType? = null

    companion object {
        private val KEYWORDS = setOf(
            "terramate", "config", "git", "cloud", "stack", "globals", "lets", "map",
            "import", "generate_hcl", "generate_file", "content", "tm_dynamic",
            "assert", "output", "script", "sharing_backend", "vendor", "define",
            "bundle", "metadata", "scaffolding", "input", "export", "component", "scaffold",
            "true", "false", "null", "if", "else", "endif", "for", "in", "endfor"
        )
    }

    override fun start(buffer: CharSequence, startOffset: Int, endOffset: Int, initialState: Int) {
        this.buffer = buffer
        this.startOffset = startOffset
        this.endOffset = endOffset
        this.tokenStart = startOffset
        this.tokenEnd = startOffset
        this.tokenType = null
        advance()
    }

    override fun getState() = 0

    override fun getTokenType() = tokenType

    override fun getTokenStart() = tokenStart

    override fun getTokenEnd() = tokenEnd

    override fun advance() {
        if (tokenEnd >= endOffset) {
            tokenType = null
            tokenStart = endOffset
            tokenEnd = endOffset
            return
        }

        tokenStart = tokenEnd
        val char = buffer[tokenEnd]

        when {
            // Whitespace
            char.isWhitespace() -> {
                tokenEnd++
                while (tokenEnd < endOffset && buffer[tokenEnd].isWhitespace()) {
                    tokenEnd++
                }
                tokenType = TerramateTokenTypes.WHITESPACE
            }

            // Line comment starting with #
            char == '#' -> {
                tokenEnd++
                while (tokenEnd < endOffset && buffer[tokenEnd] != '\n') {
                    tokenEnd++
                }
                tokenType = TerramateTokenTypes.LINE_COMMENT
            }

            // Line comment // or block comment /*
            char == '/' && tokenEnd + 1 < endOffset -> {
                val next = buffer[tokenEnd + 1]
                when (next) {
                    '/' -> {
                        tokenEnd += 2
                        while (tokenEnd < endOffset && buffer[tokenEnd] != '\n') {
                            tokenEnd++
                        }
                        tokenType = TerramateTokenTypes.LINE_COMMENT
                    }
                    '*' -> {
                        tokenEnd += 2
                        while (tokenEnd < endOffset - 1) {
                            if (buffer[tokenEnd] == '*' && buffer[tokenEnd + 1] == '/') {
                                tokenEnd += 2
                                break
                            }
                            tokenEnd++
                        }
                        tokenType = TerramateTokenTypes.BLOCK_COMMENT
                    }
                    else -> {
                        tokenEnd++
                        tokenType = TerramateTokenTypes.OPERATOR
                    }
                }
            }

            // String literals
            char == '"' -> {
                tokenEnd++
                while (tokenEnd < endOffset) {
                    val c = buffer[tokenEnd]
                    if (c == '"') {
                        tokenEnd++
                        break
                    }
                    if (c == '\\' && tokenEnd + 1 < endOffset) {
                        tokenEnd += 2
                    } else {
                        tokenEnd++
                    }
                }
                tokenType = TerramateTokenTypes.STRING
            }

            // Numbers
            char.isDigit() -> {
                tokenEnd++
                while (tokenEnd < endOffset && (buffer[tokenEnd].isDigit() || buffer[tokenEnd] == '.')) {
                    tokenEnd++
                }
                // Handle scientific notation
                if (tokenEnd < endOffset && (buffer[tokenEnd] == 'e' || buffer[tokenEnd] == 'E')) {
                    tokenEnd++
                    if (tokenEnd < endOffset && (buffer[tokenEnd] == '+' || buffer[tokenEnd] == '-')) {
                        tokenEnd++
                    }
                    while (tokenEnd < endOffset && buffer[tokenEnd].isDigit()) {
                        tokenEnd++
                    }
                }
                tokenType = TerramateTokenTypes.NUMBER
            }

            // Braces, brackets, parentheses
            char == '{' -> {
                tokenEnd++
                tokenType = TerramateTokenTypes.LBRACE
            }
            char == '}' -> {
                tokenEnd++
                tokenType = TerramateTokenTypes.RBRACE
            }
            char == '[' -> {
                tokenEnd++
                tokenType = TerramateTokenTypes.LBRACK
            }
            char == ']' -> {
                tokenEnd++
                tokenType = TerramateTokenTypes.RBRACK
            }
            char == '(' -> {
                tokenEnd++
                tokenType = TerramateTokenTypes.LPAREN
            }
            char == ')' -> {
                tokenEnd++
                tokenType = TerramateTokenTypes.RPAREN
            }

            // Operators
            char in "=!<>+-*%&|:?." -> {
                tokenEnd++
                // Handle multi-character operators
                if (tokenEnd < endOffset) {
                    val next = buffer[tokenEnd]
                    if ((char == '=' && next == '=') ||
                        (char == '!' && next == '=') ||
                        (char == '<' && next == '=') ||
                        (char == '>' && next == '=') ||
                        (char == '&' && next == '&') ||
                        (char == '|' && next == '|') ||
                        (char == '.' && next == '.')) {
                        tokenEnd++
                        if (char == '.' && tokenEnd < endOffset && buffer[tokenEnd] == '.') {
                            tokenEnd++ // ...
                        }
                    }
                }
                tokenType = TerramateTokenTypes.OPERATOR
            }

            // Identifiers and keywords
            char.isLetter() || char == '_' -> {
                tokenEnd++
                while (tokenEnd < endOffset) {
                    val c = buffer[tokenEnd]
                    if (c.isLetterOrDigit() || c == '_' || c == '-') {
                        tokenEnd++
                    } else {
                        break
                    }
                }
                val text = buffer.subSequence(tokenStart, tokenEnd).toString()
                tokenType = if (KEYWORDS.contains(text)) {
                    TerramateTokenTypes.KEYWORD
                } else {
                    TerramateTokenTypes.IDENTIFIER
                }
            }

            // Everything else
            else -> {
                tokenEnd++
                tokenType = TerramateTokenTypes.BAD_CHARACTER
            }
        }
    }

    override fun getBufferSequence() = buffer

    override fun getBufferEnd() = endOffset
}

package io.terramate.intellij

import com.intellij.lang.Commenter

/**
 * Provides comment support for Terramate language.
 * Supports both line comments (#, //) and block comments.
 */
class TerramateCommenter : Commenter {
    override fun getLineCommentPrefix() = "#"

    override fun getBlockCommentPrefix() = "/*"

    override fun getBlockCommentSuffix() = "*/"

    override fun getCommentedBlockCommentPrefix() = null

    override fun getCommentedBlockCommentSuffix() = null
}

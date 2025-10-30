package io.terramate.intellij

import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

/**
 * Icons for Terramate plugin.
 */
object TerramateIcons {
    @JvmField
    val FILE: Icon = IconLoader.getIcon("/icons/terramate.svg", TerramateIcons::class.java)

    @JvmField
    val LOGO: Icon = IconLoader.getIcon("/icons/terramate.svg", TerramateIcons::class.java)
}

package io.terramate.intellij.settings

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

/**
 * Persistent settings for Terramate plugin.
 * Stores language server configuration.
 */
@State(
    name = "io.terramate.intellij.settings.TerramateSettings",
    storages = [Storage("TerramateSettings.xml")]
)
class TerramateSettings : PersistentStateComponent<TerramateSettings> {
    var languageServerEnabled: Boolean = true
    var languageServerBinPath: String = ""
    var languageServerArgs: List<String> = listOf("-mode=stdio")
    var languageServerTrace: String = "off" // off, messages, verbose

    override fun getState(): TerramateSettings = this

    override fun loadState(state: TerramateSettings) {
        XmlSerializerUtil.copyBean(state, this)
    }

    companion object {
        fun getInstance(): TerramateSettings {
            return ApplicationManager.getApplication().getService(TerramateSettings::class.java)
        }
    }
}

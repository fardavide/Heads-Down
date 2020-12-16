package studio.forface.headsdown.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesSetKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import studio.forface.headsdown.model.PackageName

class SettingsSource(
    private val dataStore: DataStore<Preferences>
) {

    fun appsWithBlockedHeadsUp(): Flow<Set<PackageName>> =
        appsWithBlockedHeadsUpRaw().map { set -> set.map(::PackageName).toSet() }

    suspend fun addToBlockHeadsUp(packageName: PackageName) {
        dataStore.edit { preferences ->
            preferences[AppsWithBlockedHeadsUpKey] =
                appsWithBlockedHeadsUpRaw().first() + packageName.value
        }
    }

    suspend fun removeFromBlockHeadsUp(packageName: PackageName) {
        dataStore.edit { preferences ->
            preferences[AppsWithBlockedHeadsUpKey] =
                appsWithBlockedHeadsUpRaw().first() - packageName.value
        }
    }


    private fun appsWithBlockedHeadsUpRaw(): Flow<Set<String>> =
        dataStore.data.map { preferences ->
            val packagesList = preferences[AppsWithBlockedHeadsUpKey] ?: emptySet()
            packagesList
        }

    companion object {
        val AppsWithBlockedHeadsUpKey =
            preferencesSetKey<String>("AppsWithBlockedHeadsUpKey")
    }
}

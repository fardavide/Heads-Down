package studio.forface.headsdown.usecase

import studio.forface.headsdown.data.AppRepository
import studio.forface.headsdown.model.App
import studio.forface.headsdown.model.AppSettings

class AddToShouldBlockHeadsUp(
    private val repo: AppRepository
) {

    suspend operator fun invoke(app: App) {
        repo.updateAppSettings(app, AppSettings(shouldBlockHeadsUp = true))
    }
}

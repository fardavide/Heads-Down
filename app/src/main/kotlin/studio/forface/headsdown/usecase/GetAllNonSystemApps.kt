package studio.forface.headsdown.usecase

import studio.forface.headsdown.data.AppRepository

class GetAllNonSystemApps(
    private val repo: AppRepository
) {

    operator fun invoke() =
        repo.allNonSystemApps()
}

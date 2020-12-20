package studio.forface.headsdown.usecase

import studio.forface.headsdown.data.AppRepository

class GetAllApps(
    private val repo: AppRepository
) {

    operator fun invoke() =
        repo.allApps()
}

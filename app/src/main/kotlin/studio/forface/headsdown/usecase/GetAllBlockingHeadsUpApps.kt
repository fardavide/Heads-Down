package studio.forface.headsdown.usecase

import studio.forface.headsdown.data.AppRepository

class GetAllBlockingHeadsUpApps(
    private val repo: AppRepository
) {

    operator fun invoke() =
        repo.allBlockingHeadsUpApps()
}

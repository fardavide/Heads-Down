package studio.forface.headsdown.usagestats

import android.Manifest.permission.PACKAGE_USAGE_STATS
import android.app.AppOpsManager
import android.app.AppOpsManager.OPSTR_GET_USAGE_STATS
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Process
import androidx.core.app.AppOpsManagerCompat
import androidx.core.app.AppOpsManagerCompat.MODE_ALLOWED
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlin.time.seconds


class UsageStatsAccessVerifier(
    private val context: Context
) {

    val hasUsageStatsAccess = flow {
        while(true) {
            emit(hasUsageStatsAccess())
            delay(3.seconds)
        }
    }

    private fun hasUsageStatsAccess(): Boolean {
        val mode = AppOpsManagerCompat.noteOpNoThrow(
            context,
            OPSTR_GET_USAGE_STATS,
            Process.myUid(),
            context.packageName
        )
        return if (mode == AppOpsManager.MODE_DEFAULT) {
            context.checkCallingOrSelfPermission(PACKAGE_USAGE_STATS) == PERMISSION_GRANTED
        } else {
            mode == MODE_ALLOWED
        }
    }
}

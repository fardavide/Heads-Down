package studio.forface.headsdown.utils

import co.touchlab.kermit.Logger

infix fun Logger.v(message: Any) {
    v(message.toString(), AppTag)
}

infix fun Logger.d(message: Any) {
    d(message.toString(), AppTag)
}

infix fun Logger.i(message: Any) {
    i(message.toString(), AppTag)
}

infix fun Logger.w(message: Any) {
    w(message.toString(), AppTag)
}

infix fun Logger.e(message: Any) {
    e(message.toString(), AppTag)
}

private const val AppTag = "HeadsDown"

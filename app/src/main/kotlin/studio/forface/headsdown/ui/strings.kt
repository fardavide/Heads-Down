package studio.forface.headsdown.ui

// TODO use intl
val Strings = object : StringResources() {}

abstract class StringResources {

    val AppName = "Heads Down"

    val GrandNotificationAccessAction = "Grant access"

    val BlockHeadsUpForSelectedAppsMessage = "Block heads up for selected apps"
    val NotificationAccessRequiredMessage =
        "Notification access is required for blocking the Heads up notifications"
}

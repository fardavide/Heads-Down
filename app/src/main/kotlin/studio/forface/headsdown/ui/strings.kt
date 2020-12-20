package studio.forface.headsdown.ui

// TODO use intl
val Strings = object : StringResources() {}

abstract class StringResources {

    // Common
    val AppName = "Heads Down"
    val AppPackage = "studio.forface.headsdown"
    val NotificationChannelName = "HeadsDown overlay notifications"
    val NotificationChannelDescription = "HeadsDown needs to post an empty notification with " +
        "Heads up in order to mask other Heads up"

    // Actions
    val GrandNotificationAccessAction = "Grant access"

    // Messages
    val BlockHeadsUpForSelectedAppsMessage = "Block heads up for selected apps"
    val NotificationAccessRequiredMessage =
        "Notification access is required for blocking the Heads up notifications"
}

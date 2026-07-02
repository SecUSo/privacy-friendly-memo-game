package org.secuso.privacyfriendlymemory.ui

import android.content.Context
import org.secuso.pfacore.model.dialog.AbortElseDialog
import org.secuso.pfacore.ui.dialog.show
import org.secuso.privacyfriendlymemory.R

/**
 * Small bridge so the Java activities can show a dialog through PFA-Core's dialog DSL instead of
 * building a Google MaterialAlertDialogBuilder directly. The library still uses Material under the
 * hood, but the call goes through PFA-Core.
 */
object Dialogs {

    /**
     * Asks the user to confirm leaving a running game. [onQuit] runs when the user accepts;
     * cancelling or dismissing keeps the game.
     */
    @JvmStatic
    fun confirmQuitGame(context: Context, onQuit: Runnable) {
        AbortElseDialog.build(context) {
            title = { "" }
            content = { context.getString(R.string.quit_game_text) }
            acceptLabel = context.getString(R.string.quit_yes)
            abortLabel = context.getString(R.string.quit_no)
            onElse = { onQuit.run() }
            handleDismiss = false
        }.show()
    }
}

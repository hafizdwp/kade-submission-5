package me.hafizdwp.kade_submission_5.data.model

/**
 * @author hafizdwp
 * 06/01/2020
 **/
data class GnaData(
        val minute: String,
        val goalScorer: String = "",
        val goalType: GoalType
) {
    enum class GoalType {
        HOME, AWAY
    }
}
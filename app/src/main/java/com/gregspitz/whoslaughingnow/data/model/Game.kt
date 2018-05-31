package com.gregspitz.whoslaughingnow.data.model

/**
 * Model to represent the data for a single Who's Laughing Now game
 * One correct answer and several (ideally 3 wrong answers)
 * @param correctAnswer The correct answer for this game
 * @param wrongAnswers A list of all the wrong answers
 */
data class Game(val correctAnswer: Laugher, val wrongAnswers: List<Laugher>)

package android.gameengine.memoryforsalsa.models

import android.gameengine.memoryforsalsa.utils.DEFAULT_ICONS

class MemoryGame(private val boardSize: BoardSize) {

    val cards: List<MemoryCard>
    var numPairsFound = 0

    private var numcardFlips = 0
    private var indexOfSelectedCard : Int? = null


    init {

        val chosenImages = DEFAULT_ICONS.shuffled().take(boardSize.getNumPairs())
        val randomizedImages = (chosenImages + chosenImages).shuffled()
        cards = randomizedImages.map { MemoryCard(it) }
    }

    fun flipCard(position: Int): Boolean {
        numcardFlips++
        val currCard = cards[position]
        var foundMatch = false

        if (indexOfSelectedCard == null) {
            // 0 or 2 cards previously flipped over
            restoreCards()
            indexOfSelectedCard = position
        }
        else {
            // 1 card previously flipped over
            foundMatch = checkForMatch(indexOfSelectedCard!!, position)
            indexOfSelectedCard = null
        }

        currCard.isFaceUp = !currCard.isFaceUp
        return foundMatch
    }

    private fun checkForMatch(position1: Int, position2: Int): Boolean {

        if (cards[position1].identifier != cards[position2].identifier) {
            return false
        }

        cards[position1].isMatched = true
        cards[position2].isMatched = true
        numPairsFound++
        return true

    }

    private fun restoreCards() {
        for (card in cards) {
            if (!card.isMatched){
                card.isFaceUp = false
            }
        }
    }

    fun haveWonGame(): Boolean {
        return numPairsFound == boardSize.getNumPairs()
        return numPairsFound == boardSize.getNumPairs()
    }

    fun isCardFaceUp(position: Int): Boolean {
        return cards[position].isFaceUp
    }

    fun getNumMoves(): Int {
        return numcardFlips / 2
    }

}

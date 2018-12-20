package kr.ac.knu.lecture.game.blackjack;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rokim on 2018. 5. 26..
 */
public class Hand {
    private Deck deck;
    @Getter
    private List<Card> cardList = new ArrayList<>();

    public Hand(Deck deck){
        this.deck = deck;
    }

    // hit button 클릭 시, 덱에서 카드 한 장을 뽑아 손패에 저장한 후, return하는 method
    public Card drawCard() {
        Card card = deck.drawCard();
        cardList.add(card);

        return card;
    }

    public int getCardSum() {
        int countAce = 0;
        int cardSum = 0;
        for (Card card : cardList) {
            int rank = card.getRank();
            if(rank == 1){
                countAce++;
            }
            if(rank > 10){
                rank = 10;
            }
            cardSum += rank;
        }
        for (int i = 0; i < countAce; i++) {
            if(cardSum <= 11){
                cardSum +=10;
            }
        }
        return cardSum;
    }



    public void reset() {
        cardList.clear();
    }
}

package kr.ac.knu.lecture.game.blackjack;

import kr.ac.knu.lecture.exception.NotEnoughBalanceException;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by rokim on 2018. 5. 26..
 */
public class Player {
    @Getter
    private long balance;
    @Getter
    private long currentBet;
    @Getter
    private boolean isPlaying;
    @Getter
    private Hand hand;
    @Getter
    @Setter
    private boolean isDoubleDown = false;   //doubledown을 사용했는지 판단
    @Getter
    private boolean isBlackJack = false;    //blackjack 판단

    public Player(long seedMoney, Hand hand) {
        this.balance = seedMoney;
        this.hand = hand;

        isPlaying = false;
    }

    public void reset() {
        hand.reset();
        isPlaying = false;
    }

    public void placeBet(long bet) {
        if (balance < bet) {
            throw new NotEnoughBalanceException();
        }

        balance -= bet;
        currentBet = bet;

        isPlaying = true;
    }

    public void deal() {
        hand.drawCard();
        hand.drawCard();

        if(hand.getCardSum() == 21) {
            isBlackJack = true;
            stand();
        }
    }
    
    public void win() {
        if(isBlackJack) {
            balance += currentBet * 1.5;
            return ;
        }
        if( isDoubleDown )
        {
            balance += currentBet * 4;
        }
        else
            balance += currentBet * 2;
    }

    public void tie() {
        balance += currentBet;
    }

    public void lost() {
    }

    public Card hitCard() {
        return hand.drawCard();
    }

    public void stand() {
        this.isPlaying = false;
    }

}

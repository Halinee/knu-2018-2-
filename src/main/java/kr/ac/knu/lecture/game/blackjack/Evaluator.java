package kr.ac.knu.lecture.game.blackjack;

import java.util.Map;

/**
 * Created by rokim on 2018. 5. 27..
 */
public class Evaluator {
    private Map<String, Player> playerMap;
    private Dealer dealer;

    public Evaluator(Map<String, Player> playerMap, Dealer dealer) {
        this.playerMap = playerMap;
        this.dealer = dealer;
    }

    public boolean evaluate() {
        if (playerMap.values().stream().anyMatch(player -> player.isPlaying())) {
            return false;
        }

        int dealerResult = dealer.getHand().getCardSum();

        playerMap.forEach((s, player) -> {
            int playerResult = player.getHand().getCardSum();

            if(playerResult > 21){
                if(dealerResult > 21){
                    player.tie();
                }
                else
                    player.lost();
            }
            else if(playerResult == dealerResult){
                player.tie();
            }

            else // playerResult <= 21
            {
                if(dealerResult > 21){
                    player.win();
                }
                else if(dealerResult > playerResult){
                    player.lost();
                }
                else if(dealerResult < playerResult){
                    player.win();
                }

            }

        });

        return true;
    }


}

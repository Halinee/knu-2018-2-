package kr.ac.knu.lecture.game.blackjack;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by rokim on 2018. 5. 26..
 */
public class GameRoom {
    @Getter
    private final String roomId;
    @Getter
    private final Dealer dealer;
    @Getter
    private final Map<String, Player> playerList;
    @Getter
    private final Deck deck;
    @Getter
    private boolean isFinished;

    private boolean isHitClicked = false;

    private final Evaluator evaluator;

    public GameRoom(Deck deck) {
        this.roomId = UUID.randomUUID().toString();
        this.deck = deck;
        this.dealer = new Dealer(new Hand(deck));
        this.playerList = new HashMap<>();
        this.evaluator = new Evaluator(playerList, dealer);
        this.isFinished = true;
    }

    public void addPlayer(String playerName, long seedMoney) {
        Player player = new Player(seedMoney, new Hand(deck));

        playerList.put(playerName, player);
    }

    public void removePlayer(String playerName) {
        playerList.remove(playerName);
    }

    public void reset() {
        dealer.reset();
        playerList.forEach((s, player) -> player.reset());
    }

    public void bet(String name, long bet) {
        Player player = playerList.get(name);

        player.placeBet(bet);
    }

    public void deal() {
        this.isFinished = false;
        dealer.deal();
        playerList.forEach((s, player) -> player.deal());
    }

    public Card hit(String name) {
        Player player = playerList.get(name);
        isHitClicked = true;
        return player.hitCard();
    }

    public void stand(String name) {
        Player player = playerList.get(name);

        player.stand();
    }

    //더블다운 하면 게임 끝
    public void doubledown(String name){
        Player player = playerList.get(name);

        player.setDoubleDown(true);
        player.placeBet(player.getCurrentBet());    // 처음 배팅 금액만큼 배팅
        player.hitCard();   // 카드 뽑기
        player.stand();     // 게임 끝내기
    }

    public void surrender(String name){
        Player player = playerList.get(name);
        player.setSurrender(true);
        player.placeBet(player.getCurrentBet());
        player.stand();
    }

    public void playDealer() {
        dealer.play();
        evaluator.evaluate();
        this.isFinished = true;
    }


}

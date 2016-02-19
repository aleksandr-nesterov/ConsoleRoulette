package com.gamesys.roulette.core;

import com.gamesys.roulette.model.Player;

import java.util.List;
import java.util.Random;

import static com.gamesys.roulette.model.Bet.Type.*;
import static com.gamesys.roulette.model.Player.Result.LOOSER;
import static com.gamesys.roulette.model.Player.Result.WINNER;

/**
 * Console Roulette class starts the roulette game.
 *
 * @author Alexander Nesterov
 * @version 1.0
 */
public class Roulette {

    /**
     * Define winners between a list of players.
     *
     * @param players - set of players
     * @param winningNumber - winning number
     */
    public void defineWinners(final List<Player> players, final int winningNumber) {
        for (Player player : players) {
            if (player.getBet().getType() == EVEN && isWinningNumberEven(winningNumber)) {
                winner(player, 2);
            } else if (player.getBet().getType() == ODD && !isWinningNumberEven(winningNumber)) {
                winner(player, 2);
            } else if (player.getBet().getType() == NUMBER && winningNumber == player.getBet().getNumber()) {
                winner(player, 36);
            } else {
                player.setResult(LOOSER);
            }
        }
    }

    /**
     * Generate number between [0, 36]
     *
     * @return number
     */
    public int generateNumber() {
        Random random = new Random();
        int number = random.nextInt(37);
        return number;
    }

    private void winner(final Player player, final int rate) {
        double winningSum = player.getSum() * rate;
        player.setResult(WINNER);
        player.setWinningSum(winningSum);
    }

    private boolean isWinningNumberEven(final int winningNumber) {
        return winningNumber % 2 == 0;
    }

}

package com.gamesys.roulette.core;

import com.gamesys.roulette.model.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static com.gamesys.roulette.model.Bet.Type.NUMBER;
import static com.gamesys.roulette.model.Player.Result.WINNER;
import static java.lang.String.format;

/**
 * @author Alexander Nesterov
 * @version 1.0
 */
public class RouletteRunnable implements Runnable {
    // could be place in config.properties
    private static final int TIMEOUT = 30;

    private final Roulette roulette;
    private final BlockingQueue<Player> queue;

    public RouletteRunnable(Roulette roulette) {
        this.roulette = roulette;
        this.queue = new LinkedBlockingQueue<>();
    }

    /**
     * Starts thread
     */
    public void startGame() {
        System.out.println("Console roulette game is started. Please enter your bet:");
        Thread thread = new Thread(this);
        thread.start();
    }

    /**
     * Put player in game
     *
     * @throws RouletteException if a player could not be placed
     * @param player
     */
    public void placePlayerInGame(Player player) {
        boolean result = queue.offer(player);
        if (!result) {
            throw new RouletteException(format("Could not start game for user [%s]", player));
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(TIMEOUT);
            } catch (InterruptedException e) {
                System.err.println("A game is interrupted for some reason...");
                break;
            }
            // read players
            List<Player> players = new ArrayList<>();
            while(true) {
                Player player = queue.poll();
                if (player == null) break;
                players.add(player);
            }
            if (!players.isEmpty()) {
                // compute the results
                int winningNumber = roulette.generateNumber();
                printHeader(winningNumber);
                roulette.defineWinners(players, winningNumber);
                printResult(players);
            }
        }
    }

    private void printResult(final List<Player> players) {
        for (Player player : players) {
            if (player.getResult() == WINNER) printWinner(player);
            else printLooser(player);
        }
        System.out.println("");
    }

    private void printHeader(final int winningNumber) {
        System.out.println("");
        System.out.printf("Number: %s\n", winningNumber);
        System.out.printf("%-10s %-4s %8s %9s\n", "Player", "Bet", "Outcome", "Winnings");
        System.out.println("---");
    }

    private void printWinner(final Player player) {
        System.out.printf("%-10s %4s %8s %9.2f\n", player.getName(), player.getBet().getType() == NUMBER ?
                player.getBet().getNumber() : player.getBet().getType(), "WIN", player.getWinningSum());
    }

    private void printLooser(final Player player) {
        System.out.printf("%-10s %4s %8s %9.2f\n", player.getName(), player.getBet().getType() == NUMBER ?
                player.getBet().getNumber() : player.getBet().getType(), "LOSE", 0.0);
    }
}

package com.gamesys.roulette.test;

import com.gamesys.roulette.core.Roulette;
import com.gamesys.roulette.model.Bet;
import com.gamesys.roulette.model.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static com.gamesys.roulette.model.Bet.Type.*;
import static com.gamesys.roulette.model.Player.Result.LOOSER;
import static com.gamesys.roulette.model.Player.Result.WINNER;

/**
 * @author Alexander Nesterov
 * @version 1.0
 */
public class RouletteTest {

    private Roulette roulette;
    private Player player1;
    private Player player2;
    private Player player3;

    @Before
    public void init() {
        roulette = new Roulette();
        player1 = new Player();
        player1.setName("James");
        player1.setBet(new Bet(EVEN));
        player1.setSum(2.0);
        player2 = new Player();
        player2.setName("Nick");
        player2.setBet(new Bet(ODD));
        player2.setSum(3.5);
        player3 = new Player();
        player3.setName("John");
        player3.setBet(new Bet(5, NUMBER));
        player3.setSum(10.0);
    }

    @Test
    public void testCase1Valid() {
        final int winningNumber = 1;
        roulette.defineWinners(Arrays.asList(player1), winningNumber);
        Assert.assertEquals(LOOSER, player1.getResult());
    }

    @Test
    public void testCase2Valid() {
        final int winningNumber = 2;
        roulette.defineWinners(Arrays.asList(player1), winningNumber);
        Assert.assertEquals(WINNER, player1.getResult());
        Assert.assertEquals("4.0", player1.getWinningSum().toString());
    }

    @Test
    public void testCase3Valid() {
        final int winningNumber = 1;
        roulette.defineWinners(Arrays.asList(player2), winningNumber);
        Assert.assertEquals(WINNER, player2.getResult());
        Assert.assertEquals("7.0", player2.getWinningSum().toString());
    }

    @Test
    public void testCase4Valid() {
        final int winningNumber = 2;
        roulette.defineWinners(Arrays.asList(player2), winningNumber);
        Assert.assertEquals(LOOSER, player2.getResult());
    }

    @Test
    public void testCase5Valid() {
        final int winningNumber = 5;
        roulette.defineWinners(Arrays.asList(player3), winningNumber);
        Assert.assertEquals(WINNER, player3.getResult());
        Assert.assertEquals("360.0", player3.getWinningSum().toString());
    }

    @Test
    public void testCase6Valid() {
        final int winningNumber = 4;
        roulette.defineWinners(Arrays.asList(player3), winningNumber);
        Assert.assertEquals(LOOSER, player3.getResult());
    }
}

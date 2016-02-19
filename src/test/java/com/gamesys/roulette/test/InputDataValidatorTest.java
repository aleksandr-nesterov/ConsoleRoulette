package com.gamesys.roulette.test;

import com.gamesys.roulette.core.InputDataValidator;
import com.gamesys.roulette.core.RouletteException;
import com.gamesys.roulette.model.Bet;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static com.gamesys.roulette.model.Bet.Type.*;

/**
 * @author Alexander Nesterov
 * @version 1.0
 */
public class InputDataValidatorTest {

    private InputDataValidator validator;

    @Before
    public void initValidator() {
        final List<String> players = Arrays.asList("James", "David", "Nick");
        final String maxBetSum = "100";
        validator = new InputDataValidator(new HashSet<>(players), maxBetSum);
    }

    // test name
    @Test
    public void testCase1Valid() {
        final String name = "James";
        Assert.assertEquals(name, validator.validateName(name));
    }

    @Test(expected = RouletteException.class)
    public void testCase2Invalid() {
        final String name = "Jeff";
        validator.validateName(name);
    }

    // test bet
    @Test
    public void testCase3Valid() {
        final String bet = "EVEN";
        final Bet expectedBet = new Bet(EVEN);
        Assert.assertEquals(expectedBet, validator.validateBet(bet));
    }

    @Test
    public void testCase4Valid() {
        final String bet = "ODD";
        final Bet expectedBet = new Bet(ODD);
        Assert.assertEquals(expectedBet, validator.validateBet(bet));
    }

    @Test
    public void testCase5Valid() {
        // corner case
        final String bet = "1";
        final Bet expectedBet = new Bet(Integer.valueOf(bet), NUMBER);
        Assert.assertEquals(expectedBet, validator.validateBet(bet));
    }

    @Test
    public void testCase6Valid() {
        // corner case
        final String bet = "36";
        final Bet expectedBet = new Bet(Integer.valueOf(bet), NUMBER);
        Assert.assertEquals(expectedBet, validator.validateBet(bet));
    }

    @Test
    public void testCase7Valid() {
        final String bet = "20";
        final Bet expectedBet = new Bet(Integer.valueOf(bet), NUMBER);
        Assert.assertEquals(expectedBet, validator.validateBet(bet));
    }

    @Test(expected = RouletteException.class)
    public void testCase8Invalid() {
        final String bet = "0";
        validator.validateBet(bet);
    }

    @Test(expected = RouletteException.class)
    public void testCase9Invalid() {
        final String bet = "37";
        validator.validateBet(bet);
    }

    @Test(expected = RouletteException.class)
    public void testCase10Invalid() {
        final String bet = "1.10";
        validator.validateBet(bet);
    }

    // test sum
    @Test
    public void testCase11Valid() {
        final String sum = "0.01";
        Assert.assertEquals(Double.valueOf(sum), validator.validateSum(sum));
    }

    @Test
    public void testCase12Valid() {
        final String sum = "1";
        Assert.assertEquals(Double.valueOf(sum), validator.validateSum(sum));
    }

    @Test
    public void testCase13Valid() {
        final String sum = "1.1";
        Assert.assertEquals(Double.valueOf(sum), validator.validateSum(sum));
    }

    @Test(expected = RouletteException.class)
    public void testCase14Invalid() {
        final String sum = "0";
        validator.validateSum(sum);
    }

    @Test(expected = RouletteException.class)
    public void testCase15Invalid() {
        final String sum = "0.0";
        validator.validateSum(sum);
    }

    @Test(expected = RouletteException.class)
    public void testCase16Invalid() {
        final String sum = "-1";
        validator.validateSum(sum);
    }

    @Test(expected = RouletteException.class)
    public void testCase17Invalid() {
        final String sum = "-1.10";
        validator.validateSum(sum);
    }

    @Test(expected = RouletteException.class)
    public void testCase18Invalid() {
        final String sum = "1.";
        validator.validateSum(sum);
    }

    @Test(expected = RouletteException.class)
    public void testCase19Invalid() {
        final String sum = "101";
        validator.validateSum(sum);
    }
}

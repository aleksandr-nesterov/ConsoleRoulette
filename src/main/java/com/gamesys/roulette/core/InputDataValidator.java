package com.gamesys.roulette.core;

import com.gamesys.roulette.model.Bet;

import java.util.Set;

import static com.gamesys.roulette.model.Bet.Type.*;
import static java.lang.String.format;

/**
 * InputDataValidator class is used to validate input data
 *
 * @author Alexander Nesterov
 * @version 1.0
 */
public class InputDataValidator {

    private final Set<String> availablePlayerNames;
    private final int maxBetSum;

    public InputDataValidator(Set<String> availablePlayerNames, String maxBetSum) {
        this.availablePlayerNames = availablePlayerNames;
        if (!maxBetSum.matches("\\d+")) throw new RouletteException(String.format("Invalid max.bet.sum [%s]", maxBetSum));
        this.maxBetSum = Integer.valueOf(maxBetSum);
    }

    /**
     * Validate entered name. The name should be in a Set.
     *
     * @param name -- name parameter
     * @throws RouletteException if name is not in a Set
     * @return valid name
     */
    public String validateName(final String name) {
        if (!availablePlayerNames.contains(name)) {
            String errorMessage = "Invalid input data. Player [%s] does not exist.";
            throw new RouletteException(format(errorMessage, name));
        }
        return name;
    }

    /**
     * Validate entered bet. Bet should be EVEN, ODD or a number between 1-36
     *
     * @param bet -- bet parameter
     * @throws RouletteException if bet is invalid
     * @return {@link com.gamesys.roulette.model.Bet}
     */
    public Bet validateBet(final String bet) {
        if (bet.equals("EVEN")) {
            return new Bet(EVEN);
        } else if (bet.equals("ODD")) {
            return new Bet(ODD);
        } else {
            try {
                int b = Integer.parseInt(bet);
                if (b < 1 || b > 36) {
                    throw new RouletteException();
                }
                return new Bet(b, NUMBER);
            } catch (Exception e) {
                String errorMessage = "Invalid input data. You must type either a number from 1-36, EVEN or ODD.";
                throw new RouletteException(errorMessage);
            }
        }
    }

    /**
     * Validate entered sum. Must be a positive decimal number.
     * The number should be less than max.bet.sum property
     *
     * @param sum - sum parameter
     * @throws RouletteException - if sum is invalid
     * @return sum
     */
    public Double validateSum(final String sum) {
        try {
            if (sum.matches("\\d+.\\d\\d?|\\d+")) {
                Double value = Double.parseDouble(sum);
                if (value == 0) throw new RouletteException();
                if (value > maxBetSum) throw new RouletteException();
                return value;
            }
            throw new RouletteException();
        } catch (Exception e) {
            throw new RouletteException(format("Invalid input data. Bet sum [%s] is invalid.", sum));
        }
    }

}

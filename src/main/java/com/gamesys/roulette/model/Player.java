package com.gamesys.roulette.model;

/**
 * Model class which consists of all validated input data and winning results.
 *
 * @author Alexander Nesterov
 * @version 1.0
 */
public class Player {

    private String name;
    private Bet bet;
    private Double sum;
    private Result result;
    private Double winningSum;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bet getBet() {
        return bet;
    }

    public void setBet(Bet bet) {
        this.bet = bet;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Double getWinningSum() {
        return winningSum;
    }

    public void setWinningSum(Double winningSum) {
        this.winningSum = winningSum;
    }

    public enum Result {
        WINNER, LOOSER
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        return !(name != null ? !name.equals(player.name) : player.name != null);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Player{");
        sb.append("name='").append(name).append('\'');
        sb.append(", bet=").append(bet);
        sb.append(", sum=").append(sum);
        sb.append(", result=").append(result);
        sb.append(", winningSum=").append(winningSum);
        sb.append('}');
        return sb.toString();
    }
}

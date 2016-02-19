package com.gamesys.roulette;

import com.gamesys.roulette.core.*;
import com.gamesys.roulette.model.Player;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static java.lang.String.format;

/**
 * Launcher class starts a roulette game
 *
 * @author Alexander Nesterov
 * @version 1.0
 */
public class Launcher {

    private static final String CONFIG = "config.properties";
    private static final String FILE_PROPERTY_NAME = "file";
    private static final String MAX_BET_SUM_PROPERTY_NAME = "max.bet.sum";

    private final RouletteRunnable rouletteRunnable;
    private final InputDataValidator validator;

    /**
     * Constructor
     *
     * @throws RouletteException if config properties file or property name 'file' does not exist
     */
    public Launcher() {
        this.rouletteRunnable = new RouletteRunnable(new Roulette());
        ConfigValues configValues = getConfigContents();
        if (configValues.getFileName() == null) {
            throw new RouletteException(format("Could not find [%s] property in [%s] file!",
                    FILE_PROPERTY_NAME, CONFIG));
        }
        if (configValues.getMaxBetSum() == null) {
            throw new RouletteException(format("Could not find [%s] property in [%s] file!",
                    MAX_BET_SUM_PROPERTY_NAME, CONFIG));
        }
        this.validator = new InputDataValidator(getPlayerNamesFromFile(configValues.getFileName()),
                configValues.getMaxBetSum());
    }

    /**
     * Start roulette game and prepare for reading input data
     */
    public void start() {
        rouletteRunnable.startGame();
        readInputData();
    }

    /**
     * Try to get the contents of config.properties file.
     *
     * @return {@link ConfigValues}
     * @throws RouletteException if the config properties file does no exists or some IO error
     */
    private ConfigValues getConfigContents() {
        try (InputStream in = Launcher.class.getClassLoader().getResourceAsStream(CONFIG)) {
            Properties prop = new Properties();
            prop.load(in);
            return new ConfigValues(prop.getProperty(FILE_PROPERTY_NAME), prop.getProperty(MAX_BET_SUM_PROPERTY_NAME));
        } catch (Exception e) {
            throw new RouletteException(format("Could not load [%s] file.", CONFIG));
        }
    }

    /**
     * Read player names from file. Ignore duplicates.
     *
     * @param filePath - path to file location
     * @return a Set of players.
     * @throws RouletteException if IO error
     */
    private Set<String> getPlayerNamesFromFile(final String filePath) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            Set<String> playerNames = new HashSet<>();
            String playerName;
            while ((playerName = bufferedReader.readLine()) != null) {
                if (!playerNames.add(playerName)) {
                    System.err.printf("Player with name [%s] is already present. Will be ignored.\n", playerName);
                }
            }
            return playerNames;
        } catch (IOException e) {
            throw new RouletteException(format("Could not get player names from [%s] file.", filePath));
        }
    }

    /**
     * Read typed data. Create {@link Player} object and place a player to a game.
     *
     * @throws RouletteException if input data is invalid
     */
    private void readInputData() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] inputData = line.trim().split(" ");
            try {
                if (inputData.length != 3) {
                    throw new RouletteException("Invalid input data. Please try again.");
                }
                rouletteRunnable.placePlayerInGame(createPlayer(inputData));
            } catch (RouletteException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    /**
     * Validate typed data and create {@link Player} object.
     *
     * @param inputData - typed data
     * @return player
     * @throws RouletteException if data is invalid.
     */
    private Player createPlayer(final String[] inputData) {
        Player player = new Player();
        player.setName(validator.validateName(inputData[0]));
        player.setBet(validator.validateBet(inputData[1]));
        player.setSum(validator.validateSum(inputData[2]));
        return player;
    }

    public static void main(String[] args) {
        try {
            Launcher launcher = new Launcher();
            launcher.start();
        } catch (RouletteException e) {
            System.err.println(e.getMessage());
        }
    }

}

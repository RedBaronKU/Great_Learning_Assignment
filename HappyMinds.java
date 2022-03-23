import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Random;

class HappyMinds {

    static class Players {
        String name; // Storing the name of Players
        int rank = Integer.MAX_VALUE; // to store individual rank of each players, initialized to maxInteger
        ArrayList<Integer> successfulTurnLog = new ArrayList<Integer>(); // to log the history of each Successful turn
        int targetLeft; // This keeps track of how much more points a player needs

        public boolean isAllowedToPlayTurn() { // to check if player is allowed to play next turn
            if (rank != Integer.MAX_VALUE)
                return false;

            if (successfulTurnLog.size() > 1) // if 2 turns are already played
            {
                /* This is to check if player's last 2 turn was 1 */
                if (successfulTurnLog.get(successfulTurnLog.size() - 1) == 1
                        && successfulTurnLog.get(successfulTurnLog.size() - 2) == 1) {
                    System.out.println("You have 1 as your two previous turns.. Sorry you miss this turn");
                    successfulTurnLog.add(0); // Logging an empty turn.
                    return false;
                }

            }
            return true;
        }

        public Players() {
            System.out.println("Game loaded... Press Enter");
        }

        public Players(String name, int target) {
            this.name = name;
            this.targetLeft = target; // Set initial targetLeft to target
        }

        protected int returnRank() { // to return current rank at any point
            return rank;
        }

        protected void setRank(int rank) { // to set rank from Game Class
            this.rank = rank;
        }

        public String returnName() { // to return name
            return this.name;
        }

        public int returnTargetLeft() {
            return targetLeft;
        }

        public void resigterSuccessfulTurn(int turnPoint) {
            successfulTurnLog.add(turnPoint);
            this.targetLeft -= turnPoint;
        }

    }

    static class Game extends Players {
        int target; // to set the point target
        int numberOfPlayers; // Number of Players
        int diceSides = 6; // to keep track of dice diceSides
        String rule = "\n - The order in which the users roll the dice is decided randomly at the start of the game.\n - If a player rolls the value '6' then they immediately get another chance to roll again and move\nahead in the game.\n- If a player rolls the value '1' two consecutive times then they are forced to skip their next turn\nas a penalty.\n";
        ArrayList<String> winnerList = new ArrayList<String>(); // Winner List Rankwise

        public Game(int givenTarget, int playerCount) {
            target = givenTarget;
            numberOfPlayers = playerCount;
        }

        public boolean isGameOver(Players[] players) {
            if (winnerList.size() == numberOfPlayers) {
                return true;
            }
            if (winnerList.size() == numberOfPlayers - 1) {
                for (Players player : players) {
                    if (player.returnRank() == Integer.MAX_VALUE) {
                        player.setRank(numberOfPlayers); // Setting last player's rank to last and ending the round
                        winnerList.add(player.returnName()); // Add Players' name to Winner List
                    }
                }
                return true;
            }
            return false;
        }

        public void appendPlayerToWinnerList(Players player) {
            winnerList.add(player.returnName()); // Adding Player Name to Winner List
            player.setRank(winnerList.size()); // Setting rank for Player
        }

        public void setGameOver() {
            // to abruptly end game no implementation required as such
        }

        public void displayRules() {
            System.out.println(rule);
        }

        public void checkScore(Players player) {
            if (player.returnTargetLeft() == 0) {
                appendPlayerToWinnerList(player);
                System.out.println(
                        "You finished the Game, Congrats, step back and relax, Rank is:" + player.returnRank());
            }
        }

    }

    public static Players[] shufflePlayers(Players[] players) { // Randomize the player order
        Random rand = new Random();
        for (int i = 0; i < players.length; i++) {
            int randomIndexToSwap = rand.nextInt(players.length);
            Players tempPlayer = players[randomIndexToSwap];
            players[randomIndexToSwap] = players[i];
            players[i] = tempPlayer;
        }
        return players;
    }

    public static int rollADice() {
        Random rand = new Random();
        return rand.nextInt(6) + 1; // Generating random out of 6
    }

    public static void makeAPlayingTurn(Players player, Game game, Scanner sc) {
        System.out.println(player.returnName() + "'s turn. Press r to roll a dice!!");
        String playCommand;
        do {// A vary primitive yet effective way to wait till the user hit 'r'
            playCommand = sc.nextLine();
            if (playCommand.equals("rules")) {
                game.displayRules();
            }
        } while (!playCommand.equals("r"));
        System.out.println("");
        int turnValue = rollADice();
        if (turnValue <= player.targetLeft) {
            // Successful turn register
            player.resigterSuccessfulTurn(turnValue);
            System.out.println(
                    player.returnName() + " got " + turnValue + ", remaining target is:" + player.returnTargetLeft());
            game.checkScore(player);
        } else {
            System.out.println("Turn uregistered, This value is greater that your points left");
            return;
        }
        if (turnValue == 6 && player.returnTargetLeft() != 0) {
            System.out.println("You got 6... play another turn.\n");
            makeAPlayingTurn(player, game, sc);
        }

    }

    /*
     * This was my own Comparator
     * 
     * public static class PlayerComparator implements Comparator<Players> {
     * public int compare(Players p1, Players p2) {
     * if (p1.returnTargetLeft() > p2.returnTargetLeft())
     * return -1;
     * else if (p1.returnTargetLeft() < p2.returnTargetLeft())
     * return 1;
     * 
     * ***** If both player have finished game, real time result would be based on
     * their own rank
     * 
     * else if (p1.returnTargetLeft() == 0 && p2.returnTargetLeft() == 0) {
     * 
     * 
     * return p1.returnRank() > p2.returnRank() ? -1 : 1;
     * }
     * return 0;
     * }
     * }
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println(
                "Welcome to Game of Dice, To check rules type 'rules' in command without '' To play press space\n"); // Setting
                                                                                                                     // up
                                                                                                                     // Program
        boolean isSessionActive = true; // Setting game session active
        //// #region [sessionStart]
        while (isSessionActive) {

            // #region [getting_Initial_game_data]
            int numberOfPlayers = 0, target = 0; // Setting up Environment
            boolean isValidInput = true;
            do {
                try {
                    System.out.println("Enter the target");
                    target = sc.nextInt();
                } catch (Exception e) {
                    System.out.println("Invalid target, Please try again");
                    isValidInput = false;
                }
                isValidInput = target > 0 ? true : false;
                sc.nextLine();
            } while (!isValidInput);
            do {
                try {
                    System.out.println("Enter the number of Players");
                    numberOfPlayers = sc.nextInt();
                } catch (Exception e) {
                    System.out.println("Invalid target, Please try again");
                    isValidInput = false;
                }
                sc.nextLine();
            } while (!isValidInput);
            // #endregion

            // #region [Game/player_creation]
            Game game = new Game(target, numberOfPlayers); // Setting up Game
            Players[] players = new Players[numberOfPlayers];
            sc.nextLine();
            for (int i = 0; i < numberOfPlayers; i++) {
                System.out.println("Enter Player " + (i + 1) + "'s name");
                String playerName = sc.nextLine();
                players[i] = new Players(playerName, target); // Creating players
            }
            // #endregion

            // #region [Radomizing players]
            players = shufflePlayers(players);
            System.out.println("The order of players is\n");
            int index = 1;
            for (Players player : players) {
                System.out.println("Player " + player.returnName() + " will play at " + index);
                index++;
            }
            System.out.println("");
            // #endregion

            // #region [Game]
            while (!game.isGameOver(players)) {
                // #region[Playing_a_turn]
                for (Players player : players) {
                    if (player.isAllowedToPlayTurn()) {
                        makeAPlayingTurn(player, game, sc);
                    }
                    System.out.println("");
                }
                // #endregion

                // #region[display_Rank_post_each_turn]
                System.out.println("  Player's Name .....  Points left ............... Rank");

                for (Players player : players) {
                    System.out
                            .println(player.returnName() + " .... " + player.returnTargetLeft() + " ....  "
                                    + (player.returnRank() == Integer.MAX_VALUE ? " Still in-game"
                                            : player.returnRank()));
                }
                System.out.println("\n");

                /*
                 * Unfortunately the onLine editor isn't supporting my own Comparator,
                 * I am unable to check the real time data. I'm leaving this here, for
                 * inspection.
                 * 
                 * 
                 * 
                 * PlayerComparator obj = new PlayerComparator();
                 * PriorityQueue<Players> playerRankList;
                 * playerRankList = new PriorityQueue<Players>(numberOfPlayers, obj);
                 * int tempRank = 1;
                 * while (playerRankList.size() > 0) {
                 * Players tempPlayer = playerRankList.poll();
                 * System.out
                 * .println(tempPlayer.returnName() + " " + tempPlayer.returnTargetLeft() + " "
                 * + tempRank);
                 * }
                 * 
                 */
                // #endregion

            }
            // #endregion
            System.out.println("/n/nThe Final rank are:/n");
            for (Players player : players) {
                System.out.println(player.returnName() + " finished at " + player.returnRank());
            }
            /* Up for Next Round ? */
            sc.nextLine();
            System.out.println("Wanna Continue Press y/Y?");
            String wannaContinue = sc.nextLine();
            isSessionActive = wannaContinue.equals("Y") || wannaContinue.equals("y") ? true : false;
        }
        //// #region [sessionEnd]
        sc.close();
        // Aai Sapath test case likhta but abhi online editor se kaam kar raha hu

    }
}

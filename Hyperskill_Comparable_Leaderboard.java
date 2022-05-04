import java.util.*;

/**
 * 
 * This problem is from Jetbrains Academy topic - Comparable
 * 
 * @author Jason Zhang
 * @version 1.0
 * @since 2022-05-04
 *
 * Problem statement:
*   Leaderboard:
*
*       There is an application to create leaderbords of e-sports competitions. 
*       It uses the Score class to represent a score of each player. 
*       This class has two fields: player for the player's name and totalScore for that player's total score. 
*       To build a leaderbord, the Score objects need to be compared. 
*       A Score object is considered bigger than another Score if it's totalScore value is bigger. 
*       If totalScore values of two Score objects are the same, such objects must be compared by their player values. See the example below.
*
*   Sample Input 1:
*       Ann 162
*       Zipper 121
*       Flash 121
*       CoolDoge 200
*
*   Sample Output 1:
*       [Flash=121, Zipper=121, Ann=162, CoolDoge=200]
*/

class Hyperskill_Comparable_Leaderboard {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        List<Score> scores = new ArrayList<>();

        while (sc.hasNextLine()) {
            String[] input = sc.nextLine().split(" ");
            Score score = new Score(input[0], Integer.parseInt(input[1]));
            scores.add(score);
        }

        Collections.sort(scores);
        System.out.println(scores);

        sc.close();
    }
}

class Score implements Comparable<Score> {
    private final String player;
    private final int totalScore;

    public Score(String player, int totalScore) {
        this.player = player;
        this.totalScore = totalScore;
    }

    public String getPlayer() {
        return player;
    }

    public int getTotalScore() {
        return totalScore;
    }

    @Override
    public String toString() {
        return player + '=' + totalScore;
    }

    @Override
    public int compareTo(Score score) {
        if (this.totalScore == score.getTotalScore()) {
            return this.player.compareTo(score.getPlayer());
        } else {
            return Integer.compare(this.totalScore, score.getTotalScore());
        }

    }
}
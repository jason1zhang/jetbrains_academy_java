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
*   Ratings:
*
*       A website uses Rating class to store information about ratings of publications. 
*       It has two integer fields: upVotes and downVotes and implements Comparable interface. 
*       Your task is to write it's compareTo method. Two Rating objects must be compared by a "net rating" 
*       which is calculated as a difference between the number of upvotes and the number of downvotes.
*/

public class Hyperskill_Comparable_Ratings {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        List<Rating> list = new ArrayList<>();

        // when input through the keyboard, to signal the input completion, press "Ctrl + Z", then press "Enter" key
        while (sc.hasNextLine()) {
            int[] votes = Arrays.stream(sc.nextLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
                    
            Rating rating = createRating(votes[0], votes[1]);
            list.add(rating);
        }

        Collections.sort(list);
        
        // Checker.check(list);

        // System.out.println(list);
        for (Rating rating : list) {
            System.out.println(rating);
        }

        sc.close();
    }

    private static Rating createRating(int up, int down) {
        Rating rating = new Rating();
        rating.setUpVotes(up);
        rating.setDownVotes(down);
        return rating;
    }
}

class Rating implements Comparable<Rating> {
    private int upVotes;
    private int downVotes;

    public int getUpVotes() {
        return upVotes;
    }

    public void setUpVotes(int upVotes) {
        this.upVotes = upVotes;
    }

    public int getDownVotes() {
        return downVotes;
    }

    public void setDownVotes(int downVotes) {
        this.downVotes = downVotes;
    }

    @Override
    public int compareTo(Rating rating) {
        int netRating1 = this.upVotes - this.downVotes;
        int netRating2 = rating.getUpVotes() - rating.getDownVotes();

        return Integer.compare(netRating1, netRating2);
    }

    @Override
    public String toString() {
        return String.format("Rating {upVotes = %d, downVotes = %d}", this.upVotes, this.downVotes);
    }
}
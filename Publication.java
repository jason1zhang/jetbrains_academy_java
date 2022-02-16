/**
 * Polymorphism  Publication and its subclasses
 * @author Jason Zhang
 * @version 1.0
 * @since 2022-02-17
 * 
 * You are given four classes — Publication, Newspaper, Article and Announcement. You need to
 * override the methods getType() and getDetails() in classes inherited from the class Publication.
 *
 * Then you need to implement getInfo() in the class Publication using getType() and getDetails().
 * The method should return a String with a type of publication in the first place, then details
 * in round brackets and the title after a colon. Examples are shown below.
 */

class Publication {

    private String title;

    public Publication(String title) {
        this.title = title;
    }

    public final String getInfo() {
        // write your code here
        return this.getType() + this.getDetails()  + ": " + this.title;
    }

    public String getType() {
        return "Publication";
    }

    public String getDetails() {
        return "";
    }
}

class Newspaper extends Publication {

    private String source;

    public Newspaper(String title, String source) {
        super(title);
        this.source = source;
    }

    // write your code here
    @Override
    public String getType() {
        return "Newspaper";
    }

    @Override
    public String getDetails() {
        return " (source - " + this.source + ")";
    }
}

class Article extends Publication {

    private String author;

    public Article(String title, String author) {
        super(title);
        this.author = author;
    }

    // write your code here
    @Override
    public String getType() {
        return "Article";
    }

    @Override
    public String getDetails() {
        return " (author - " + this.author + ")";
    }
}

class Announcement extends Publication {

    private int daysToExpire;

    public Announcement(String title, int daysToExpire) {
        super(title);
        this.daysToExpire = daysToExpire;
    }

    // write your code here
    @Override
    public String getType() {
        return "Announcement";
    }

    @Override
    public String getDetails() {
        return " (days to expire - " + this.daysToExpire + ")";
    }
}
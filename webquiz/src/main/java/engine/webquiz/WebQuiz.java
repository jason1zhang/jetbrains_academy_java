package engine.webquiz;

import java.util.ArrayList;

public class WebQuiz {
    private String title;
    private String text;
    private ArrayList<String> options;

    public WebQuiz() {
    }

    public WebQuiz(String title, String text, ArrayList<String> options) {
        this.title = title;
        this.text = text;
        this.options = options;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<String> getOptions() {
        return this.options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }
}

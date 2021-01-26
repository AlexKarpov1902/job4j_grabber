package ru.job4j.html;

import java.util.Date;

public class Post {

    String link;

    String text;

    Date date;

    public Post(String link, String text, Date date) {
        this.link = link;
        this.text = text;
        this.date = date;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Post{"
                + "link='" + link + '\''
                + ", text='" + text + '\''
                + ", date=" + date
                + '}';
    }
}

package ru.job4j.html;

import java.util.Date;
import java.util.Objects;

public class Post {

    private String name;

    private String link;

    private String text;

    private Date date;

    public Post(String name, String link, String text, Date date) {
        this.name = name;
        this.link = link;
        this.text = text;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
                + "name='" + name + '\''
                + ", link='" + link + '\''
                + ", text='" + text + '\''
                + ", date=" + date
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return Objects.equals(name, post.name) && Objects.equals(link, post.link) && Objects.equals(text, post.text) && Objects.equals(date, post.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, link, text, date);
    }
}

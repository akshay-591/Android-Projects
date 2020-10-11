package com.kumar.akshay.flickerbrowser;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class DataEntry  implements Serializable {
    private static final long serialVersionUID = 1L;
    private String title;
    private String author;
    private String authorId;
    private String link;
    private String Tags;
    private String images;

    DataEntry(String title, String author, String authorId, String link, String tags, String images) {
        this.title = title;
        this.author = author;
        this.authorId = authorId;
        this.link = link;
        Tags = tags;
        this.images = images;
    }

    String getTitle() {
        return title;
    }

    String getAuthor() {
        return author;
    }

    String getAuthorId() {
        return authorId;
    }

    String getLink() {
        return link;
    }

    String getTags() {
        return Tags;
    }

    String getImages() {
        return images;
    }

    @Override
    public String toString() {
        return
                "title=" + title + "\n" +
                        "author=" + author + "\n" +
                        "authorId=" + authorId + "\n" +
                        "link=" + link + "\n" +
                        "Tags=" + Tags + "\n" +
                        "images=" + images + "\n";
    }
}

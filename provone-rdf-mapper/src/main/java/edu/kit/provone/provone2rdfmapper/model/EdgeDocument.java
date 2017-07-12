package edu.kit.provone.provone2rdfmapper.model;

/**
 * Created by mukhtar on 02.12.16.
 */
public class EdgeDocument {
    private String title;

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "EdgeDocument{" +
                "title='" + title + '\'' +
                '}';
    }

    public void setTitle(String title) {
        this.title = title;
    }
}


package com.example.virtualvolunteer.SearchPage;

import com.example.virtualvolunteer.Upload;

public class SearchResult {
    // POJO for search results

    private String name;
    private String bio;
    private Upload image;

    public SearchResult(String name, String bio, Upload image) {
        this.name = name;
        this.bio = bio;
        this.image = image;
    }

    public String getName() {
        return this.name;
    }

    public String getBio() {
        return this.bio;
    }

    public Upload getImageURL() {
        return this.image;
    }

    public void setName(String name) {
        this.name = name;
    }

}

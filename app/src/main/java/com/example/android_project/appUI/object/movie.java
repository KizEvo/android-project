package com.example.android_project.appUI.object;

import java.time.LocalDate;

public class movie {
    private String movieName;
    private String directorName;
    private String[] casterName;
    private String category;
    private LocalDate debutDate;
    private int duration;   //in min
    private String language;

    movie(String movieName, String directorName, String[] casterName,
                             String category, LocalDate debutDate, int duration, String language) {
        this.movieName = movieName;
        this.directorName = directorName;
        this.casterName = casterName;
        this.category = category;
        this.debutDate = debutDate;
        this.duration = duration;
        this.language = language;
    }

    public String getMovieName(){
        return movieName;
    }

    public String getDirectorName(){
        return directorName;
    }

    public String[] getCasterName(){
        return casterName;
    }

    public String getCategory(){
        return category;
    }

    public LocalDate getDebutDate(){
        return debutDate;
    }

    public int getDuration(){
        return duration;
    }

    public String getLanguage(){
        return language;
    }
}
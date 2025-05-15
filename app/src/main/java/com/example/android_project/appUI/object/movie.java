package com.example.android_project.appUI.object;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class movie {
    private String posterName;
    private String movieID;
    private String movieName;
    private String directorName;
    private String[] casterName;
    private String category;
    private String debutDate;
    private int duration;   //in min
    private String language;

    public movie(String movieID, String posterName, String movieName, String directorName, String[] casterName,
                             String category, String debutDate, int duration, String language) {
        this.posterName = posterName;
        this.movieID = movieID;
        this.movieName = movieName;
        this.directorName = directorName;
        this.casterName = casterName;
        this.category = category;
        this.debutDate = debutDate;
        this.duration = duration;
        this.language = language;
    }

    public String getPosterName(){
        return posterName;
    }

    public String getMovieID(){
        return movieID;
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

    public String getConcatenateCasterName(){
        StringBuilder result = new StringBuilder();
        int i = 0;
        if(casterName.length != 0) {
            i = 0;
            while (i < casterName.length) {
                result.append(casterName[i]);
                if(i != casterName.length - 1)
                    result.append(", ");
                i++;
            }
        }
        return result.toString();
    }

    public String getCategory(){
        return category;
    }

    public String getDebutDate(){
        return debutDate;
    }

    public int getDuration(){
        return duration;
    }

    public String getLanguage(){
        return language;
    }

}
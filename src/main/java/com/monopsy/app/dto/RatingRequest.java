package com.monopsy.app.dto;
import jakarta.validation.constraints.*;

public class RatingRequest {
    @NotNull @Min(1) @Max(5) private Integer score;
    private String feedback;

    public Integer getScore()     { return score; }
    public String getFeedback()   { return feedback; }
    public void setScore(Integer v)    { this.score = v; }
    public void setFeedback(String v)  { this.feedback = v; }
}

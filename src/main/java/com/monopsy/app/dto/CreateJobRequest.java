package com.monopsy.app.dto;
import jakarta.validation.constraints.*;

public class CreateJobRequest {
    @NotBlank private String title, description, location, category, jobType, salary;
    private String contactPhone;
    private Double latitude, longitude;

    public String getTitle()        { return title; }
    public String getDescription()  { return description; }
    public String getLocation()     { return location; }
    public String getCategory()     { return category; }
    public String getJobType()      { return jobType; }
    public String getSalary()       { return salary; }
    public String getContactPhone() { return contactPhone; }
    public Double getLatitude()     { return latitude; }
    public Double getLongitude()    { return longitude; }
    public void setTitle(String v)        { this.title = v; }
    public void setDescription(String v)  { this.description = v; }
    public void setLocation(String v)     { this.location = v; }
    public void setCategory(String v)     { this.category = v; }
    public void setJobType(String v)      { this.jobType = v; }
    public void setSalary(String v)       { this.salary = v; }
    public void setContactPhone(String v) { this.contactPhone = v; }
    public void setLatitude(Double v)     { this.latitude = v; }
    public void setLongitude(Double v)    { this.longitude = v; }
}

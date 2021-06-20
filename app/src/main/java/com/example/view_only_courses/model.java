package com.example.view_only_courses;

public class model
{
    public model() {
    }

    String course,email,name,purl,video_url,pdf_url;

    public model(String course, String email, String name, String purl, String video_url, String pdf_url) {
        this.course = course;
        this.email = email;
        this.name = name;
        this.purl = purl;
        this.video_url = video_url;
        this.pdf_url = pdf_url;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getPdf_url() {
        return pdf_url;
    }

    public void setPdf_url(String pdf_url) {
        this.pdf_url = pdf_url;
    }
}


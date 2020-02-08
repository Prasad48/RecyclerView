package com.bhavaniprasad.recyclerview.model;

public class RepoResponse {

    public String id;
    public String name;
    public String full_name;
    public owner owner;
    public String contributors_url;

    public RepoResponse(String id, String name, String full_name, RepoResponse.owner owner, String contributors_url) {
        this.id = id;
        this.name = name;
        this.full_name = full_name;
        this.owner = owner;
        this.contributors_url = contributors_url;
    }

    public class owner {
        public String avatar_url;
        public String login;
        private String id;

        public owner(String avatar_url, String login, String id) {
            this.avatar_url = avatar_url;
            this.login = login;
            this.id = id;
        }

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public RepoResponse.owner getOwner() {
        return owner;
    }

    public void setOwner(RepoResponse.owner owner) {
        this.owner = owner;
    }

    public String getContributors_url() {
        return contributors_url;
    }

    public void setContributors_url(String contributors_url) {
        this.contributors_url = contributors_url;
    }
}

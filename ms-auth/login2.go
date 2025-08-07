package main

import (
	"context"
	"fmt"
	"golang.org/x/oauth2"
	"io/ioutil"
	"log"
	"net/http"
)

var oauth2Config = &oauth2.Config{
	ClientID:     "a1a8af7e-9c26-4cb6-9000-de9f8d317321",
	ClientSecret: "62F8Q~iwybVWmYx495TeSbykfzP.7oJxT3kX.aoM",
	RedirectURL:  "https://29b1a6aa08f8.ngrok-free.app/api/callback",
	Scopes:       []string{"User.Read"},
	Endpoint: oauth2.Endpoint{
		AuthURL:  "https://login.microsoftonline.com/common/oauth2/v2.0/authorize",
		TokenURL: "https://login.microsoftonline.com/common/oauth2/v2.0/token",
	},
}

func main() {
	http.HandleFunc("/", startLogin)
	http.HandleFunc("/api/callback", handleCallback)
	http.HandleFunc("/logout", logout) // Add logout handler
	fmt.Println("Server started at http://localhost:7071")
	log.Fatal(http.ListenAndServe(":7071", nil))
}

func startLogin(w http.ResponseWriter, r *http.Request) {
	url := oauth2Config.AuthCodeURL("state-token", oauth2.AccessTypeOffline)
	http.Redirect(w, r, url, http.StatusFound)
}

func handleCallback(w http.ResponseWriter, r *http.Request) {
	code := r.URL.Query().Get("code")
	token, err := oauth2Config.Exchange(context.Background(), code)
	if err != nil {
		http.Error(w, "Failed to exchange token: "+err.Error(), http.StatusInternalServerError)
		return
	}

	client := oauth2Config.Client(context.Background(), token)
	resp, err := client.Get("https://graph.microsoft.com/v1.0/me")
	if err != nil {
		http.Error(w, "Failed to get user info: "+err.Error(), http.StatusInternalServerError)
		return
	}
	defer resp.Body.Close()
	data, err := ioutil.ReadAll(resp.Body)
	if err != nil {
		http.Error(w, "Failed to read user info: "+err.Error(), http.StatusInternalServerError)
		return
	}
	fmt.Fprintf(w, "User Info: %s", string(data))
	fmt.Fprintf(w, `{"access_token": "%s", "id_token": "%s"}`, token.AccessToken, token.Extra("id_token"))
}

func logout(w http.ResponseWriter, r *http.Request) {
	// Optional: Clear any local session or token storage
	// Redirect to Microsoft logout URL
	logoutURL := "https://login.microsoftonline.com/common/oauth2/v2.0/logout?post_logout_redirect_uri=http://localhost:7071"
	http.Redirect(w, r, logoutURL, http.StatusFound)
}

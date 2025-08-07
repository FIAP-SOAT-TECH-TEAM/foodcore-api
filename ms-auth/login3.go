package main

import (
	"encoding/json"
	"fmt"
	"github.com/go-resty/resty/v2"
	"log"
	"net/http"
)

const (
	tenantID     = "2cd2417e-6923-48f1-b686-fa1c9be865cd"
	clientID     = "a1a8af7e-9c26-4cb6-9000-de9f8d317321"
	clientSecret = "62F8Q~iwybVWmYx495TeSbykfzP.7oJxT3kX.aoM"
	scope        = "api://a1a8af7e-9c26-4cb6-9000-de9f8d317321/.default" // e.g., "api://your-api-client-id/.default"
	apiURL       = "http://localhost:7071/items"                         // URL of the API to call
)

func main() {
	token, err := getAzureJWT()
	if err != nil {
		log.Fatalf("Error obtaining JWT: %v", err)
	}

	fmt.Println("Obtained token:", token)
	if err := callAPI(token); err != nil {
		log.Fatalf("Error calling API: %v", err)
	}
}

// getAzureJWT retrieves an access token from Azure AD
func getAzureJWT() (string, error) {
	client := resty.New()

	response, err := client.R().
		SetHeader("Content-Type", "application/x-www-form-urlencoded").
		SetFormData(map[string]string{
			"grant_type":    "client_credentials",
			"client_id":     clientID,
			"client_secret": clientSecret,
			"scope":         scope,
		}).
		Post(fmt.Sprintf("https://login.microsoftonline.com/%s/oauth2/v2.0/token", tenantID))

	if err != nil {
		return "", fmt.Errorf("request error: %v", err)
	}

	if response.StatusCode() != http.StatusOK {
		return "", fmt.Errorf("failed to get token: %s", response.String())
	}

	var result map[string]interface{}
	if err := json.Unmarshal(response.Body(), &result); err != nil {
		return "", fmt.Errorf("failed to parse token response: %v", err)
	}

	accessToken, ok := result["access_token"].(string)
	if !ok {
		return "", fmt.Errorf("access_token not found in response")
	}

	return accessToken, nil
}

// callAPI uses the JWT to authenticate against the API
func callAPI(token string) error {
	client := resty.New()

	response, err := client.R().
		SetHeader("Authorization", "Bearer "+token).
		Get(apiURL)

	if err != nil {
		return fmt.Errorf("request error: %v", err)
	}

	if response.StatusCode() != http.StatusOK {
		return fmt.Errorf("failed to call API: %s", response.String())
	}

	fmt.Printf("API response: %s\n", response.String())
	return nil
}

package main

import (
	"encoding/json"
	"fmt"
	"github.com/valyala/fasthttp"
	"io"
	"net/http"
	"net/url"
	"strings"
)

func passwordLogin(ctx *fasthttp.RequestCtx) {
	const (
		clientID     = "a1a8af7e-9c26-4cb6-9000-de9f8d317321"
		clientSecret = "62F8Q~iwybVWmYx495TeSbykfzP.7oJxT3kX.aoM"
		scope        = "openid offline_access"
	)

	type LoginRequest struct {
		Username string `json:"username"`
		Password string `json:"password"`
	}

	var loginReq LoginRequest
	if err := json.Unmarshal(ctx.PostBody(), &loginReq); err != nil {
		ctx.SetStatusCode(400)
		ctx.SetBody([]byte("JSON inválido"))
		return
	}

	username := loginReq.Username
	password := loginReq.Password

	tokenURL := "https://quatroquatros.b2clogin.com/quatroquatros.onmicrosoft.com/oauth2/v2.0/token?p=B2C_1_SIGNUP_SIGNIN"

	fmt.Println(tokenURL)

	form := url.Values{}
	form.Set("grant_type", "password")
	form.Set("client_id", clientID)
	form.Set("client_secret", clientSecret)
	form.Set("username", username)
	form.Set("password", password)
	form.Set("scope", scope)

	req, _ := http.NewRequest("POST", tokenURL, strings.NewReader(form.Encode()))
	req.Header.Set("Content-Type", "application/x-www-form-urlencoded")

	resp, err := http.DefaultClient.Do(req)
	if err != nil {
		ctx.SetStatusCode(500)
		ctx.SetBody([]byte("Erro ao chamar Azure: " + err.Error()))
		return
	}
	defer resp.Body.Close()

	body, _ := io.ReadAll(resp.Body)
	if resp.StatusCode != http.StatusOK {
		ctx.SetStatusCode(resp.StatusCode)
		ctx.SetBody(body)
		return
	}

	ctx.SetContentType("application/json")
	ctx.SetStatusCode(200)
	ctx.SetBody(body)
}

func login(ctx *fasthttp.RequestCtx) {
	const (
		tenant       = "quatroquatros" //"SEU_TENANT"
		policy       = "B2C_1_SIGNUP_SIGNIN"
		clientID     = "e16c2642-083d-4464-9353-b3ba2c08dbec"             //"SEU_CLIENT_ID"
		redirectURI  = "https://e9b3b1809f3d.ngrok-free.app/api/callback" //"https://SUA_FUNC.azurewebsites.net/api/callback"
		scope        = "openid offline_access"
		responseType = "code"
	)

	authURL := fmt.Sprintf("https://%s.b2clogin.com/%s.onmicrosoft.com/%s/oauth2/v2.0/authorize", tenant, tenant, policy)

	q := url.Values{}
	q.Set("client_id", clientID)
	q.Set("response_type", responseType)
	q.Set("redirect_uri", redirectURI)
	q.Set("scope", scope)
	q.Set("response_mode", "query")
	q.Set("state", "xyz123")

	redirect := fmt.Sprintf("%s?%s", authURL, q.Encode())
	ctx.Redirect(redirect, fasthttp.StatusFound)

}

func callback(ctx *fasthttp.RequestCtx) {
	code := string(ctx.URI().QueryArgs().Peek("code"))
	if code == "" {
		ctx.SetStatusCode(400)
		ctx.SetBody([]byte("Missing code"))
		return
	}

	const (
		tenant       = "quatroquatros.onmicrosoft.com" //"SEU_TENANT.onmicrosoft.com"
		policy       = "B2C_1_SIGNUP_SIGNIN"
		clientID     = "e16c2642-083d-4464-9353-b3ba2c08dbec"             //"SEU_CLIENT_ID"
		clientSecret = "iSB8Q~eVNuCL2Pk9NVNiWqemE5Q3tJ5misp3Hb9v"         //"SEU_CLIENT_SECRET"
		redirectURI  = "https://e9b3b1809f3d.ngrok-free.app/api/callback" //"https://SUA_FUNC.azurewebsites.net/api/callback"
	)

	tokenURL := fmt.Sprintf("https://%s.b2clogin.com/%s.onmicrosoft.com/%s/oauth2/v2.0/authorize", tenant, tenant, policy)

	form := url.Values{}
	form.Set("grant_type", "authorization_code")
	form.Set("client_id", clientID)
	form.Set("client_secret", clientSecret)
	form.Set("code", code)
	form.Set("redirect_uri", redirectURI)
	form.Set("scope", "openid offline_access")

	req, _ := http.NewRequest("POST", tokenURL, strings.NewReader(form.Encode()))
	req.Header.Set("Content-Type", "application/x-www-form-urlencoded")

	resp, err := http.DefaultClient.Do(req)
	if err != nil {
		ctx.SetStatusCode(500)
		ctx.SetBody([]byte("Erro ao chamar Azure: " + err.Error()))
		return
	}
	defer resp.Body.Close()

	body, _ := io.ReadAll(resp.Body)
	if resp.StatusCode != http.StatusOK {
		ctx.SetStatusCode(resp.StatusCode)
		ctx.SetBody(body)
		return
	}

	// Retorna os tokens (access_token, id_token, refresh_token)
	ctx.SetContentType("application/json")
	ctx.SetStatusCode(200)
	ctx.SetBody(body)
}

func main() {
	fmt.Println("Server running at http://localhost:7071/api/login")
	fasthttp.ListenAndServe(":7071", func(ctx *fasthttp.RequestCtx) {
		switch string(ctx.Path()) {
		case "/api/login":
			passwordLogin(ctx)
		case "/api/login/oauth2/code":
			login(ctx)
		case "/api/callback":
			callback(ctx)
		default:
			ctx.Error("Not found", fasthttp.StatusNotFound)
		}
	})
}

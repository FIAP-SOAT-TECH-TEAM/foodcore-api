package main

// func main() {
// 	fmt.Println("Callback funcionando em http://localhost:7071/api/callback")
// 	fasthttp.ListenAndServe(":7071", func(ctx *fasthttp.RequestCtx) {
// 		switch string(ctx.Path()) {
// 		case "/api/callback":
// 			callback(ctx)
// 		default:
// 			ctx.Error("Not found", fasthttp.StatusNotFound)
// 		}
// 	})
// }

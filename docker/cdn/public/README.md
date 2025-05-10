# CDN fake para armazenamento de imagens

Este diretório contém a configuração e os arquivos para o CDN fake usado no projeto food-core-api.

## Estrutura de diretórios

```
public/
  ├── products/       # Imagens de produtos
  ├── categories/     # Imagens de categorias
  └── 404.html        # Página de erro 404
```

## Como funciona

O CDN fake é implementado usando um contêiner Nginx que serve os arquivos estáticos.
As imagens são armazenadas nos diretórios correspondentes e são acessíveis através de URLs como:

- <http://localhost:8082/products/x-burger.jpg>
- <http://localhost:8082/categories/burgers.jpg>

## Uso na aplicação

Quando um produto é cadastrado com uma imagem, a imagem é armazenada no CDN e o caminho relativo
é salvo no banco de dados. Ao recuperar o produto, a URL completa é construída concatenando
a URL base do CDN com o caminho relativo.

Na aplicação, a URL base do CDN é configurada através da propriedade `app.cdn.base-url`.

Exemplo:

- URL base: <http://localhost:8082>
- Caminho relativo: products/x-burger.jpg
- URL completa: <http://localhost:8082/products/x-burger.jpg>

## Como adicionar imagens manualmente

1. Coloque as imagens dos produtos na pasta `products/`
2. Coloque as imagens das categorias na pasta `categories/`
3. Certifique-se de que os nomes dos arquivos correspondem aos que estão no banco de dados

## Limitações

Esta é uma implementação simples para desenvolvimento e testes. Em produção,
recomenda-se usar um serviço de CDN adequado como AWS S3, Google Cloud Storage, etc.

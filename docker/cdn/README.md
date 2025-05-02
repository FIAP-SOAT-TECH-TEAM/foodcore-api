# CDN para Armazenamento de Imagens

Este diretório contém a configuração e os arquivos necessários para o CDN (Content Delivery Network) usado para armazenar e servir imagens de produtos e categorias.

## Estrutura de Diretórios

```
cdn/
├── nginx.conf                      # Configuração do Nginx
├── public/                         # Diretório raiz do CDN
│   ├── 404.html                    # Página de erro customizada
│   ├── products/                   # Imagens de produtos
│   │   └── placeholder.jpg         # Imagem placeholder para produtos
│   └── categories/                 # Imagens de categorias
```

## Como Funciona

O CDN é um servidor Nginx configurado para servir arquivos estáticos com otimização de cache e compressão. Ele é acessível através da URL `http://localhost:8082` durante o desenvolvimento local.

### Armazenamento de Imagens

As imagens são armazenadas em diretórios organizados:

- Produtos: `/products/{productId}/{filename}`
- Categorias: `/categories/{categoryId}/{filename}`

### Acesso às Imagens

As imagens podem ser acessadas através das URLs:

- Produtos: `http://localhost:8082/products/{productId}/{filename}`
- Categorias: `http://localhost:8082/categories/{categoryId}/{filename}`

## Benefícios

- **Desempenho**: Cache e compressão otimizados
- **Escalabilidade**: Facilmente substituível por um CDN real em produção
- **Organização**: Estrutura clara de diretórios para diferentes tipos de imagens
- **CORS**: Configurado para permitir acesso de qualquer origem
- **Monitoramento**: Endpoint de healthcheck em `/healthcheck`

## Em Produção

Em um ambiente de produção, este CDN local seria substituído por um serviço de CDN real, como:

- AWS CloudFront
- Google Cloud CDN
- Cloudflare
- Akamai

A configuração do aplicativo através do `app.cdn.public-url` permitiria uma transição suave para um serviço de CDN externo.

# 🍔 Food Core API

<div align="center">

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=FIAP-SOAT-TECH-TEAM_food-core-api&metric=alert_status&token=19e960f56f10089f0c8d262863b33c62a92dbc46)](https://sonarcloud.io/summary/new_code?id=FIAP-SOAT-TECH-TEAM_food-core-api)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=FIAP-SOAT-TECH-TEAM_food-core-api&metric=code_smells&token=19e960f56f10089f0c8d262863b33c62a92dbc46)](https://sonarcloud.io/summary/new_code?id=FIAP-SOAT-TECH-TEAM_food-core-api)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=FIAP-SOAT-TECH-TEAM_food-core-api&metric=duplicated_lines_density&token=19e960f56f10089f0c8d262863b33c62a92dbc46)](https://sonarcloud.io/summary/new_code?id=FIAP-SOAT-TECH-TEAM_food-core-api)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=FIAP-SOAT-TECH-TEAM_food-core-api&metric=ncloc&token=19e960f56f10089f0c8d262863b33c62a92dbc46)](https://sonarcloud.io/summary/new_code?id=FIAP-SOAT-TECH-TEAM_food-core-api)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=FIAP-SOAT-TECH-TEAM_food-core-api&metric=reliability_rating&token=19e960f56f10089f0c8d262863b33c62a92dbc46)](https://sonarcloud.io/summary/new_code?id=FIAP-SOAT-TECH-TEAM_food-core-api)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=FIAP-SOAT-TECH-TEAM_food-core-api&metric=sqale_index&token=19e960f56f10089f0c8d262863b33c62a92dbc46)](https://sonarcloud.io/summary/new_code?id=FIAP-SOAT-TECH-TEAM_food-core-api)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=FIAP-SOAT-TECH-TEAM_food-core-api&metric=sqale_rating&token=19e960f56f10089f0c8d262863b33c62a92dbc46)](https://sonarcloud.io/summary/new_code?id=FIAP-SOAT-TECH-TEAM_food-core-api)
</div>

API de gerenciamento de pedidos para restaurantes fast-food, desenvolvida como parte do curso de Arquitetura de Software
da FIAP (Tech Challenge).

<div align="center">
  <a href="#visao-geral">Visão Geral</a> •
  <a href="#arquitetura">Arquitetura</a> •
  <a href="#infra">Infraestrutura</a> •
  <a href="#tecnologias">Tecnologias</a> •
  <a href="#diagramas">Diagramas</a> •
  <a href="#eventstorming">Event Storming</a> •
  <a href="#taskboard">Task Board</a> •
  <a href="#dicionario">Dicionário de linguagem ubíqua</a> •
  <a href="#instalacao-e-uso">Instalação e Uso</a> •
  <a href="#provisionamento-na-nuvem">Provisionar o projeto na nuvem</a> •
  <a href="#estrutura-do-projeto">Estrutura do Projeto</a> • <br/>
  <a href="#apis">APIs</a> •
  <a href="#banco-de-dados">Banco de Dados</a> •
  <a href="#resolucao-de-problemas">Resolução de Problemas</a> •
  <a href="#contribuicao-e-licenca">Contribuição e Licença</a>
</div><br>

> 📽️ Vídeo de demonstração da arquitetura: [https://www.youtube.com/watch?v=soaATSbSRPc](https://www.youtube.com/watch?v=XgUpOKJjqak)<br>

<h2 id="visao-geral">📋 Visão Geral</h2>

O sistema implementa um serviço de auto-atendimento para uma lanchonete de fast-food, permitindo que os clientes façam
pedidos e acompanhem o status do seu pedido sem a necessidade de interação com um atendente. A aplicação também inclui
um painel administrativo para gerenciamento de produtos, clientes e acompanhamento de pedidos.

### Principais recursos

- **Auto-atendimento**: Interface para clientes realizarem pedidos
- **Identificação de cliente**: Por CPF ou cadastro com nome e e-mail
- **Pagamento integrado**: Via QRCode do Mercado Pago
- **Acompanhamento de pedido**: Status em tempo real (Recebido, Em preparação, Pronto, Finalizado)
- **Painel administrativo**: Gerenciamento de produtos, categorias e pedidos

<h2 id="arquitetura">🧱 Arquitetura</h2>
<details>
<summary>Expandir para mais detalhes</summary>

Este projeto segue os princípios da **Arquitetura Limpa (Clean Architecture)** com o objetivo de manter um core de negócio independente, purista e facilmente testável. O desenho modular segue uma separação clara de responsabilidades entre camadas, respeitando dependências unidirecionais e regras de isolamento.

### 🎯 Princípios Adotados

- O **core** (domain, application e interface adapters) **não possui dependências de frameworks**
- O uso de bibliotecas externas (como Spring, MapStruct ou JPA) está **restrito à infraestrutura**
- Todas as interfaces de entrada e saída são representadas por **portas (interfaces)** no core
- O fluxo é baseado em **casos de uso (UseCases)** acionados por adaptadores de interface
- As comunicações são feitas por **gateways**, permitindo **inversão de dependência**
- A arquitetura permite **extração futura para microsserviços**, sem acoplamento com tecnologias específicas

---

### 📐 Diagrama de Fluxo

![Diagrama de Fluxo](docs/diagrams/user-flowchart.svg)

### Monolito Modular (Spring Modulith)

A aplicação é estruturada como um monolito modular usando Spring Modulith, com contextos limitados (bounded contexts)
bem definidos para cada domínio de negócio:

![Diagrama Monolito Modular](docs/diagrams/monolito-modular.svg)

Cada módulo:

- É autocontido, com seus próprios adaptadores, portas e domínio
- Comunica-se com outros módulos através de eventos de domínio
- Pode ser extraído como um serviço independente no futuro

### Eventos de Domínio

O sistema utiliza eventos de domínio assíncronos entre módulos, permitindo:

- Comunicação desacoplada
- Notificações entre contextos limitados
- Fácil migração para uma arquitetura distribuída no futuro

</details>

<h2 id="infra">🌐 Infraestrutura</h2>
<details>
<summary>Expandir para mais detalhes</summary>

A infraestrutura do projeto é baseada em containers Docker, orquestrados com Kubernetes e provisionados via Terraform. A aplicação é dividida em módulos, cada um com suas próprias responsabilidades e adaptadores.

## ☁️ Provisionamento de Infraestrutura com Terraform

A infraestrutura é provisionada de forma automatizada e reprodutível usando o **Terraform**, uma ferramenta de infraestrutura como código (IaC). O fluxo é organizado em etapas que garantem a criação segura e modular dos recursos no Azure.

![Terraform Infraestrutura](docs/diagrams/terraform.png)

---

### 🔄 Fluxo de Execução

#### 1. **Inicialização**

- Carrega a configuração do backend remoto (para manter o estado do Terraform) e os provedores necessários.

#### 2. **Carregamento de Variáveis**

- As variáveis são separadas por responsabilidade:
  - `Common Variables`: configurações compartilhadas.
  - `AKS Variables`: definições específicas do cluster Kubernetes.
  - `Blob Storage Variables`: informações do armazenamento de blobs.
  - `Public IP Variables`: configurações de IP público.

#### 3. **Provisionamento de Recursos**

- Criação dos principais recursos de infraestrutura:
  - **Resource Group**: grupo de recursos principal do Azure.
  - **Public IP**: IP público para serviços de entrada.
  - **Blob Storage**:
    - `Storage Account`: conta de armazenamento no Azure.
    - `Storage Container`: container para armazenar arquivos (ex: estado do Terraform ou imagens).
  - **AKS Cluster**: cluster do Azure Kubernetes Service.
  - **Assign Network Role**: atribui as permissões de rede necessárias ao AKS.

#### 4. **Coleta de Outputs**

- Ao final da execução, o Terraform retorna informações essenciais:
  - Nome e ID do Resource Group
  - Nome do cluster AKS
  - IP público (FQDN e endereço)
  - Nome e conexão do Storage Account
  - Nome do container no Blob Storage

---

### ✅ Vantagens do Provisionamento com Terraform

- **Reprodutibilidade**: qualquer ambiente (dev, staging, prod) pode ser recriado com exatidão.
- **Automação**: reduz intervenção manual, evita erros e melhora consistência.
- **Modularização**: separação de variáveis e responsabilidades torna o código mais limpo e reutilizável.
- **Infra como Código**: o estado da infraestrutura é versionado e auditável via Git.

---

## ⚙️ Infraestrutura e Arquitetura no Kubernetes

A aplicação está implantada no **Azure Kubernetes Service (AKS)**, utilizando práticas modernas de escalabilidade, observabilidade e isolamento de responsabilidades para garantir alta disponibilidade, segurança e performance.

### 📌 Visão Geral

![Diagrama da Kubernets](docs/diagrams/kubernetsDiagram.png)

---

### 🧩 Componentes Principais

#### 🧑‍💻 Usuário Web/Mobile

- A interação começa com o usuário via navegador ou aplicativo.
- Todo o tráfego HTTPS passa pelo **NGINX Ingress Controller**, responsável pelo roteamento.

#### 🌐 Ingress NGINX Controller

- Atua como gateway de entrada do cluster.
- Roteia requisições conforme o caminho:
  - `/api` → **Order Management API**
  - `/adminer` → **Interface do banco**
  - `/kibana` → **Dashboard de observabilidade**

---

### 🧱 API Namespace

#### 🚀 Order API Pod

- Core da aplicação: processa pedidos, persiste dados e integra com o **MercadoPago**.
- Gera logs de aplicação e banco, enviados ao namespace de observabilidade.

#### ⚖️ Horizontal Pod Autoscaler (HPA)

- Escala automaticamente os pods com base em **uso de CPU e memória**.
- Monitora continuamente os pods e ajusta a quantidade conforme a carga do sistema.

##### 🧪 Probes e Configurações de Saúde

- **Liveness Probe**: reinicia o pod se estiver travado.
- **Readiness Probe**: verifica se o pod está pronto para receber requisições.
- **Startup Probe**: usada na inicialização para garantir que o pod esteja saudável antes de ativar as outras probes.

##### 📊 Políticas de Recursos

- **Requests & Limits**: define recursos mínimos e máximos para o pod.
- **Node Affinity**: aloca pods em nós apropriados para melhor performance.

---

### 🗃️ Armazenamento e Dados

#### Order Database

- Banco relacional que armazena os dados dos pedidos e transações.

#### Image Storage

- Serviço de armazenamento de imagens de produtos ou comprovantes de pedidos.

---

### 📡 Integração com MercadoPago

- A **Order API** comunica-se diretamente com a API de pagamentos.
- Processa **QR Codes**, escuta **webhooks** e confirma **transações em tempo real**.

---

### 📊 Observabilidade com EFK Stack (EFK Namespace)

- **Fluentd**: coleta e roteia logs de aplicação e banco.
- **Elasticsearch**: armazena os logs com capacidade de pesquisa.
- **Kibana**: interface para visualização e análise de logs via `/kibana`.

---

### ✅ Benefícios da Arquitetura

- **Escalabilidade automática com HPA**
- **Observabilidade centralizada com EFK**
- **Roteamento seguro e flexível via NGINX**
- **Separação clara de responsabilidades por namespace**
- **Alta disponibilidade e performance no AKS**

</details>

<h2 id="tecnologias">🔧 Tecnologias</h2>

<details>
<summary>Expandir para mais detalhes</summary>

### Backend

- **Java 21**: Linguagem principal
- **Spring Boot 3.4**: Framework base
- **Spring Modulith**: Para organização modular da aplicação
- **Spring Data JPA**: Persistência e acesso a dados
- **Spring Security**: Segurança e autenticação
- **MapStruct**: Mapeamento entre DTOs e entidades
- **Lombok**: Redução de código boilerplate

### Banco de Dados

- **PostgreSQL**: Banco de dados relacional principal
- **Liquibase**: Migrações de banco de dados

### Infraestrutura & Observabilidade

- **Docker**: Containerização
- **Docker Compose**: Orquestração de containers
- **Kubernetes (AKS)**: Orquestração de containers em produção
- **Terraform**: Provisionamento de infraestrutura como código
- **Gradle**: Gerenciamento de dependências e builds
- **SonarQube/SonarCloud**: Análise estática de código
- **Azure DevOps**: Armazenamento de imagens com o Azure Blob Storage
- **GitHub Actions**: CI/CD
- **Swagger/OpenAPI**: Documentação de API

### Integração

- **Mercado Pago API**: Processamento de pagamentos

</details>

<h2 id="diagramas">📊 Diagramas</h2>

<details>
<summary>Expandir para mais detalhes</summary>

### Modelo de Domínio

![Diagrama Modelo de Domínio](docs/diagrams/domain_model.svg)

---

### 🛒 Fluxo de Realização do Pedido e Pagamento

![Eventos de domínio - Criação de Pedido](docs/diagrams/order-created.svg)

#### 🎯 Fluxo Clean Arch

![Fluxo do Usuário - Criação de Pedido](docs/diagrams/UserFlow.png)

---

### 🍳 Fluxo de Preparação e Entrega do Pedido

![Eventos de domínio - Preparação e Entrega do Pedido](docs/diagrams/order-preparing.svg)

#### 🎯 Fluxo Clean Arch

![Fluxo do Restaurante - Preparação e Entrega](docs/diagrams/AdminFlow.png)

---

### 💳 Fluxo de Compra e Pagamento

#### 📈 Diagrama Sequencial

![Diagrama Sequencial - Compra e Pagamento](docs/diagrams/sequencialDiagram.png)

</details>

<h2 id="eventstorming"> 💡Event Storming</h2>
<details>
<summary>Expandir para mais detalhes</summary>

### Event Storming Miro

- <https://miro.com/app/board/uXjVIAFD_zg=/?share_link_id=933422566141>

![image](https://github.com/user-attachments/assets/1c5261a3-60ab-45de-ae4c-86b3afe28db9)
![image](https://github.com/user-attachments/assets/29611638-e684-4244-b3b6-6ae19e725bc4)
</details>

<h2 id="taskboard"> 📌Task Board</h2>
<details>
<summary>Expandir para mais detalhes</summary>

### Board de tarefas Linear App

![image](https://github.com/user-attachments/assets/0c1a5e19-aae3-4270-84ad-64c67daf64b9)
</details>

<h2 id="dicionario">📖 Dicionário de linguagem ubíqua</h2>
<details>
<summary>Expandir para mais detalhes</summary>

### Termos essenciais para a aplicação

- **Admin (Administrador)**
  Usuário com privilégios elevados, responsável pela gestão de usuários, permissões e configurações do sistema.

- **Adquirente**
  Instituição financeira responsável por processar transações de pagamento do sistema. No nosso caso, a adquirente é
  representada pela integração com o [Mercado Pago](https://www.mercadopago.com.br).

- **Authentication (Autenticação)**
  Processo de validação da identidade de um usuário por meio de login.

- **Authorization (Autorização)**
  Controle de acesso baseado em permissões e papéis (roles). Exemplo: apenas administradores podem listar todos os
  usuários.

- **Catalog (Catálogo de Produtos)**
  Conjunto organizado dos produtos disponíveis para seleção e montagem de pedidos.

- **Category (Categoria)**
  Classificação dos produtos por tipo (ex.: lanches, bebidas, sobremesas).

- **Combo**
  Conjunto personalizado por um cliente, composto por: lanche, acompanhamento, bebida e sobremesa.

- **Customer (Cliente)**
  Pessoa que realiza um pedido no sistema. Pode se identificar com CPF, cadastrar nome/e-mail ou seguir como convidado (
  guest).

- **Guest (Convidado)**
  Cliente que realiza um pedido sem se identificar ou criar conta. Atua como usuário temporário.

- **Mercado Pago Integration (Integração com Mercado Pago)**
  Serviço externo utilizado para processar pagamentos eletrônicos dos pedidos.

- **Order (Pedido)**
  Conjunto de itens selecionados por um cliente para consumo. Pode incluir um ou mais combos.

- **Order Item (Item do Pedido)**
  Produto específico dentro de um pedido. Pode ser parte de um combo ou avulso.

- **Payment (Pagamento)**
  Etapa posterior à finalização do pedido. Utiliza integração com o Mercado Pago para processar as transações
  financeiras.

- **Expiração (Pagamento)**
  Tempo de expiração para pagamento de QrCode gerado pelo adquirente. Por padrão, 30 minutos, após esgotar o tempo o
  pedido relacionado é cancelado.

- **Product (Produto)**
  Qualquer item disponível para venda, como lanches, bebidas, sobremesas ou acompanhamentos.

- **Role (Papel)**
  Função atribuída a um usuário. Define suas permissões de acesso no sistema (ex.: ADMIN, ATENDENTE, GUEST).

- **Status do Pedido**
  Representa o estado atual de um pedido. Exemplos: *Em preparação*, *Pronto*, *Entregue*, *Cancelado*.

- **Stock (Estoque)**
  Representa a quantidade disponível de cada produto no sistema.

- **TID (Transaction ID)**
  Identificador único de uma transação na adquirente, fornecido após o pagamento.

- **User (Usuário)**
  Pessoa autenticada no sistema. Pode possuir diferentes papéis, como ADMIN, ATENDENTE ou GUEST.

</details>

<h2 id="instalacao-e-uso">🚀 Instalação e Uso</h2>

<details>
<summary>Expandir para mais detalhes</summary>

### Requisitos

- Docker e Docker Compose
- Ngrok (para testes locais de webhook)
- JDK 21+
- Gradle 8.0+

### Script Centralizador `food`

O projeto utiliza um script centralizador `food` para gerenciar todas as operações:

```bash
./food [comando] [opções]
```

#### Comandos Principais

| Comando       | Descrição                                           |
|---------------|-----------------------------------------------------|
| `start:all`   | Inicia toda a infraestrutura e a aplicação          |
| `start:infra` | Inicia apenas a infraestrutura (banco)              |
| `start:app`   | Inicia apenas a aplicação                           |
| `stop:all`    | Para todos os serviços                              |
| `stop:infra`  | Para apenas a infraestrutura                        |
| `stop:app`    | Para apenas a aplicação                             |
| `restart:all` | Reinicia todos os serviços                          |
| `restart:app` | Reinicia apenas a aplicação                         |
| `db:up`       | Aplica migrações do banco de dados                  |
| `db:reset`    | Reseta o banco de dados                             |
| `logs`        | Exibe logs dos containers                           |
| `logs:app`    | Exibe logs apenas da aplicação                      |
| `logs:db`     | Exibe logs apenas do banco de dados                 |
| `status`      | Exibe status dos containers                         |
| `clean`       | Remove containers, imagens e volumes não utilizados |
| `help`        | Exibe a mensagem de ajuda                           |

#### Opções

- `--build`, `-b`: Reconstrói as imagens antes de iniciar
- `--force`, `-f`: Força a execução sem confirmação

### Iniciando o Ambiente do Zero

### 🛠️ Como configurar o ambiente local com Ngrok

Para que sua aplicação local receba os webhooks de forma funcional (especialmente em endpoints que estão em `localhost`), é necessário utilizar o [Ngrok](https://ngrok.com/).

### ⚙️ Passo a passo para configurar o Ngrok

1. **Baixe o Ngrok:**
    - Acesse: [https://ngrok.com/download](https://ngrok.com/download) e faça o download de acordo com seu sistema operacional.

2. **Instale e autentique o Ngrok (apenas na primeira vez):**

   ```bash
   ngrok config add-authtoken SEU_TOKEN_DO_NGROK

3. **Exponha a porta da aplicação:**

   ```bash
   ngrok http 80
   ```

4. **Copie o link gerado:**
    - O Ngrok irá gerar uma URL do tipo `https://abc123.ngrok.io` que redireciona para `http://localhost`.

5. **Atualize o .env:**
    - No arquivo `docker\.env`, adicione a URL do Ngrok como base para os webhooks (não esqueça de adicionar o caminho `/api/payments/webhook` para que o webhook funcione corretamente):

   ```properties
   MERCADO_PAGO_NOTIFICATION_URL=https://abc123.ngrok.io/api/payments/webhook
   ```

Com o Ngrok configurado, agora precisamos subir a aplicação.

>### ⚠️ Ambientes
>
>O projeto suporta diferentes ambientes com diferentes conjuntos de dados:
>
>- **Produção (perfil: prod)**: Apenas dados essenciais
>- **Desenvolvimento (perfil: dev/local)**: Dados essenciais + dados adicionais para testes
>
>Por default, a aplicação iniciará em modo produção. Caso deseje alterar, edite `docker\.env` com o perfil escolhido:
>
>```bash
>SPRING_PROFILES_ACTIVE=nome_do_perfil
>```

### Iniciando a Aplicação Localmente (via Docker Compose)

```bash
# Clone o repositório
git clone https://github.com/soat-fiap/food-core-api.git
cd food-core-api
cd docker

# Execute a aplicação
docker compose up -d
```

### Iniciando a Aplicação Localmente (via Script Centralizador)

```bash
# Clone o repositório
git clone https://github.com/soat-fiap/food-core-api.git
cd food-core-api

# Baixar o dos2unix para converter os arquivos de script
sudo apt install dos2unix     # Debian/Ubuntu
brew install dos2unix         # macOS

# Converter os arquivos de script para o formato Unix
dos2unix food scripts/*.sh

# Tornar o script principal executável
chmod +x food scripts/*.sh

# Iniciar infraestrutura (banco, adminer)
./food start:infra

# Resetar e configurar o banco de dados
./food db:reset

# Iniciar a aplicação
./food start:app --build

# Ou iniciar tudo de uma vez
./food start:all --build
```

> ⚠️ O pacote `dos2unix` é necessário pois os scripts foram criados em ambiente Windows e podem conter quebras de linha no formato `CRLF`, incompatíveis com sistemas `Unix`.

### Acessando a Aplicação

- **API**: <http://localhost/api>
- **Swagger/OpenAPI**: <http://localhost/api/swagger-ui.html>
- **Adminer (gerenciador de banco de dados)**: <http://localhost:8081>
  - Sistema: PostgreSQL
  - Servidor: db
  - Usuário: postgres
  - Senha: postgres
  - Banco: fastfood

### Testando a Aplicação (Fluxo de compra 🛒)

> ⚠️ O fluxo completo requer dados de catálogos, produtos e estoque, que são automaticamente populados pelos seeders do Liquibase ao iniciar a aplicação.

Para realizar um fluxo de compra na aplicação, você pode seguir os passos abaixo:

1. **Identificação do cliente** (Opcional):
   Você pode se identificar criando um usuário ou seguir como um convidado:
   - Caso queria se identificar, crie um usuário com os dados abaixo. Informe `nome + email`, apenas `CPF` ou ambos:

   ```http
   POST /users
   Content-Type: application/json
   {
     "guest": false,
     "name": "João da Silva",
     "username": "Jão3",
     "email": "joao@example.com",
     "document": "929.924.370-00"
   }
    ```

   - Caso queira seguir como convidado, envie o payload vazio ou com o campo `guest = true`:

   ```http
   POST /users
   Content-Type: application/json
   {
      "guest": true
   }
    ```

   ou

    ```http
   POST /users
   Content-Type: application/json
   {
   }
    ```

   > ⚠️ Reenviar o mesmo payload irá retornar o usuário já existente.

2. **Realizar Pedido**:
   - Crie um pedido com os produtos disponíveis:

   ```http
   POST /orders
   Content-Type: application/json
   {
     "userId": 1,
     "items": [
       {
         "productId": 1,
         "name": "X-Burger",
         "quantity": 2,
         "unitPrice": 22.90,
         "observation": "Sem cebola"
       },
       {
         "productId": 2,
         "name": "X-Bacon",
         "quantity": 1,
         "unitPrice": 24.90,
         "observation": "Capricha no bacon"
       }
     ]
   }
   ```

   - Se o pedido for criado com sucesso, o status retornado será RECEIVED.

3. **Acessar QrCode para Pagamento**:
   - Após criar o pedido, você receberá o id do pedido que será utilizado nessa rota para gerar o QrCode.

   ```
    GET /orders/{orderId}/qrCode
    ```

   - Com o retorno, você poderá copiar o valor de qrCode e utiliza-lo no site [QRCode Monkey](https://www.qrcode-monkey.com/) para gerar o QrCode.

4. **Escaneie o QrCode com o aplicativo do Mercado Pago**:
   - Abra o aplicativo do Mercado Pago e escaneie o QrCode gerado.
   - Siga as instruções para concluir o pagamento.
   - Após o pagamento ser efetuado, o Mercado Pago notificará a aplicação via webhook:

   ```http
   POST /payments/webhook
    ```

   - Este webhook atualizará automaticamente o status do pedido para APPROVED. Se o pagamento não for concluído no tempo limite, o status será alterado para CANCELED.

5. **Acompanhar o Status do pagamento do pedido**:
   - Você pode acompanhar o status do pagamento do seu pedido a qualquer momento:

   ```
    GET /payments/{orderId}/status
    ```

   - Caso o status do pagamento seja `APPROVED`, o pedido foi confirmado e já estará sendo preparado pelo restaurante.

6. **Preparação do Pedido (Admin/Restaurante)**:
   - Logue com o usuário admin.

    ```http
    POST /users/login
    Content-Type: application/json
    {
   "email": "admin@fastfood.com",
   "password": "admin123"
    }
    ```

   - Após o login, busque todas os pedidos ativos ou busque seu pedido pelo id dele:

   ```
    GET /orders/active
    GET /orders/{orderId}
    ```

    > ⚠️ O pedido foi alterado para `PREPARING` automaticamente após aprovação do pagamento.
7. **Marcar o pedido como pronto (Admin/Restaurante)**:
    - Quando o pedido estiver pronto, você poderá marca-lo como pronto para que o usuário possa retira-lo:

    ```http
    PATCH /orders/{orderId}/status
    Content-Type: application/json
    {
      "status": "READY"
    }
    ```

    > ⚠️ Futuramente, o usuário será notificado quando o pedido dele estiver pronto.

8. **Finalizar Pedido (Admin/Restaurante)**:
   - Quando o pedido for retirado pelo cliente, você poderá finalizar o pedido:

    ```http
    PATCH /orders/{orderId}/status
    Content-Type: application/json
    {
      "status": "COMPLETED"
    }
    ```

9. **Verificar pedido finalizado (Admin/Restaurante)**:
   - Você pode verificar o status do pedido a qualquer momento:

    ```
    GET /orders/{orderId}
    ```

   - O status final será `COMPLETED` quando o pedido for retirado pelo cliente.
   - O pedido finalizado também não aparecerá mais na lista de pedidos ativos:

    ```
    GET /orders/active
    ```

</details>

<h2 id="provisionamento-na-nuvem">☁️ Como provisionar o projeto na nuvem</h2>
<details>
<summary>Expandir para mais detalhes</summary>

Este projeto utiliza **infraestrutura como código** com Terraform para provisionamento no Azure, e Helm para deploy no AKS.

### Requisitos

- **Azure CLI**: Para interagir com o Azure ([instalação](#1-azure-cli))
- **Terraform**: Para provisionamento da infraestrutura ([instalação](#2-terraform))
- **Helm**: Para gerenciar o Kubernetes ([instalação](#3-helm))
- **Kubectl**: Para interagir com o cluster Kubernetes ([instalação](#4-kubectl))
- **Docker**: Para construir e enviar imagens ([instalação](#5-docker))
- **K6**: Para testes de carga ([instalação](#6-k6))

### 🔧 Instalação dos Requisitos

Siga os passos abaixo para instalar as ferramentas necessárias no seu ambiente:

---

#### 1. Azure CLI

```bash
# Windows (via PowerShell)
Invoke-WebRequest -Uri https://aka.ms/installazurecliwindows -OutFile .\AzureCLI.msi; Start-Process msiexec.exe -Wait -ArgumentList '/I AzureCLI.msi /quiet'; rm .\AzureCLI.msi

# macOS (Homebrew)
brew install azure-cli

# Linux (APT)
curl -sL https://aka.ms/InstallAzureCLIDeb | sudo bash

# Verifique a instalação
az --version
```

#### 2. Terraform

```bash
# macOS/Linux
brew tap hashicorp/tap
brew install hashicorp/tap/terraform

# Windows (choco)
choco install terraform

# Verifique a instalação
terraform -v
```

#### 3. Helm

```bash
# macOS
brew install helm

# Windows (choco)
choco install kubernetes-helm

# Linux
curl https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3 | bash

# Verifique a instalação
helm version --short
```

#### 4. Kubectl

```bash
# macOS
brew install kubectl

# Windows (choco)
choco install kubernetes-cli

# Linux
curl -LO "https://dl.k8s.io/release/$(curl -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
chmod +x kubectl
sudo mv kubectl /usr/local/bin/

# Verifique a instalação
kubectl version --client
```

#### 5. Docker

```bash
# macOS
brew install --cask docker


# Windows
choco install docker-desktop


# Linux
sudo apt-get install docker.io
sudo systemctl start docker
sudo systemctl enable docker

# Verifique a instalação
docker --version
```

#### 6. K6

```bash
# macOS
brew install k6

# Windows (choco)
choco install k6

# Linux (Ubuntu)
sudo apt install gnupg ca-certificates
curl -fsSL https://dl.k6.io/key.gpg | sudo gpg --dearmor -o /usr/share/keyrings/k6-archive-keyring.gpg
echo "deb [signed-by=/usr/share/keyrings/k6-archive-keyring.gpg] https://dl.k6.io/deb stable main" | sudo tee /etc/apt/sources.list.d/k6.list
sudo apt update
sudo apt install k6

# Verifique a instalação
k6 version
```

## 🚀 Passo a passo

### 1. Crie uma conta de Armazenamento e um Container no Azure

Essa conta será usada para armazenar o `terraform.tfstate`. Você pode criar isso pelo portal do Azure ou com os comandos CLI abaixo:

```bash
az storage account create --name nomeDaConta --resource-group nomeDoGrupo --location brazilsouth --sku Standard_LRS
az storage container create --account-name nomeDaConta --name tfstate
```

### 2. Crie o arquivo terraform.tfvars

Crie um arquivo `terraform.tfvars` na raiz do projeto com as seguintes variáveis:

```hcl
subscription_id = "SUA_SUBSCRIPTION_ID_AZURE"
```

### 3. Faça login na sua conta Azure

Instale o Azure CLI e faça login na sua conta:

```bash
az login
```

### 4. Execute o Terraform

```bash
terraform init
terraform plan -var-file=terraform.tfvars
terraform apply -var-file=terraform.tfvars
 ```

Consulte os outputs gerados:

```bash
terraform output
```

> ⚠️ A connection string é um valor sensível, logo, será exibida de forma mascarada com `<sensitive>`. Para recuperá-la, use o comando:
>
> ```bash
> terraform output -raw storage_account_connection_string
>```

### 5. Faça build da imagem Docker e dê push para o Docker Hub

```bash
docker build -t seu-usuario/seu-app:tag .
docker push seu-usuario/seu-app:tag
```

### 6. Configure os valores do Helm com os outputs do Terraform

Após executar o Terraform, copie os valores de saída necessários (resource group, IP público, connection string e nome do container do Azure Storage) e atualize o arquivo values.yaml do Helm Chart com essas informações:

```yaml
service.beta.kubernetes.io/azure-load-balancer-resource-group: "SEU_RESOURCE_GROUP"
loadBalancerIP: "SEU_IP_PUBLICO"
connectionString: "SEU_STORAGE_CONNECTION_STRING"
containerName: "SEU_CONTAINER_NAME"
```

### 7. Atualize o kubeconfig para se conectar ao novo cluster AKS

```bash
az aks get-credentials --resource-group seu-grupo --name seu-cluster
```

### 8. Empacote e instale o Helm chart

```bash
cd kubernetes
helm package foodcoreapi
helm install foodcoreapi ./foodcoreapi-0.1.0.tgz
```

### 9. Execute teste de estresse com K6

```bash
k6 run stress-test.js
```

</details>

<h2 id="estrutura-do-projeto">📁 Estrutura do Projeto</h2>

<details>
<summary>Expandir para mais detalhes</summary>

O projeto segue uma estrutura modular organizada por domínios:

```
food-core-api/
│
├── src/
│   ├── main/
│   │   ├── java/com/soat/fiap/food/core/api/
│   │   │   ├── FoodCoreApiApplication.java     # Aplicação principal
│   │   │   │
│   │   │   ├── catalog                         # Módulo responsável pelo catálogo (catálogos, categorias e produtos)
│   │   │   │   ├── core                        # Camada de domínio e aplicação
│   │   │   │   │    ├── application            # Casos de uso e DTOs de entrada
│   │   │   │   │    ├── domain                 # Entidades, VOs, eventos e exceções do domínio
│   │   │   │   │    └── interfaceadapters
│   │   │   │   │        ├── bff                # Camada de interface web (controllers e presenters)
│   │   │   │   │        ├── dto                # DTOs e mapeadores da camada de apresentação
│   │   │   │   │        └── gateways           # Interfaces dos gateways (ports de saída)
│   │   │   │   └── infrastructure              # Implementações técnicas (web, persistência, eventos, configurações)
│   │   │   │
│   │   │   ├── order                           # Módulo responsável pelos pedidos
│   │   │   │     ├── core                      # Lógica de domínio e regras de negócio
│   │   │   │     └── infrastructure            # Implementações de persistência, web e eventos
│   │   │   │
│   │   │   ├── payment                         # Módulo responsável pelos pagamentos e integração com Mercado Pago
│   │   │   │   ├── core                        # Casos de uso, entidades, eventos e VOs de pagamento
│   │   │   │   └── infrastructure              # Web, integração externa (Mercado Pago) e persistência
│   │   │   │
│   │   │   ├── user                            # Módulo responsável pela gestão de usuários e autenticação
│   │   │   │   ├── core                        # Casos de uso, modelo de domínio e validações
│   │   │   │   └── infrastructure              # Controllers e persistência
│   │   │   ├── shared/                         # Componentes compartilhados
│   │   │   │   ├── core                        # VOs e exceções genéricas
│   │   │   │   ├── interfaceadapters           # Gateways genéricos e DTOs utilitários
│   │   │   │   └── infrastructure              # Configurações globais, autenticação JWT, eventos e storage
│   │   │
│   │   └── resources/
│   │       ├── application.yml                 # Configurações gerais
│   │       ├── application-dev.yml             # Configurações de desenvolvimento
│   │       ├── application-prod.yml            # Configurações de produção
│   │       └── db/changelog/                   # Migrações Liquibase
│   │
│   └── test/                                   # Testes
│
├── docker/                                     # Arquivos Docker
│   ├── Dockerfile
│   ├── docker-compose.yml
│   └── services/                               # Serviços adicionais
│
│
├── kubernetes/
│   └── foodcoreapi/                            # Chart Helm principal da aplicação
│       ├── Chart.yaml                          # Metadata do chart
│       ├── Chart.lock                          # Dependências travadas
│       ├── values.yaml                         # Configurações parametrizáveis do chart
│       └── templates/                          # Templates Helm
│           ├── api/                            # Subcomponentes da API
│           │   ├── adminer/                    # Adminer (gerenciador de DB)
│           │   ├── api/                        # FoodCore API (app principal)
│           │   ├── postgresql/                 # StatefulSet do banco PostgreSQL
│           │   └── namespace.yaml              # Namespace da aplicação
│           ├── common/                         # Componentes reutilizáveis
│           │   ├── ingress/                    # Ingress + ExternalNames
│           │   └── volume/                     # StorageClass
│           └── efk/                            # Stack EFK para logging
│               ├── elasticsearch/              # StatefulSet e ConfigMap
│               ├── fluentd/                    # DaemonSet + RBAC
│               ├── kibana/                     # Interface Kibana
│               └── namespace.yaml
│
│
├── terraform/
│   ├── backend.tf                              # Configuração do backend remoto (ex: Azure Storage para o state)
│   ├── main.tf                                 # Composição dos módulos e recursos
│   ├── provider.tf                             # Configuração do provedor (Azure)
│   ├── outputs.tf                              # Outputs globais da infraestrutura
│   ├── variables.tf                            # Variáveis globais
│   └── modules/                                # Módulos reutilizáveis para recursos Azure
│       ├── aks/                                # Criação do cluster AKS (Kubernetes)
│       ├── blob/                               # Storage Account e Containers
│       ├── public_ip/                          # Endereços IP públicos
│       └── resource_group/                     # Resource Group base do ambiente
│
├── scripts/                                    # Scripts de gerenciamento
│
├── docs/                                       # Documentação
│
├── food                                        # Script centralizador
└── README.md                                   # Este arquivo
```

### 🧱 Estrutura Modular (Clean Architecture)

Cada módulo (ex: `catalog`, `order`, `payment`, etc.) segue a mesma estrutura padrão, baseada nos princípios da Clean Architecture, com separação clara entre regras de negócio, adaptação e infraestrutura.

```
módulo/                                 # Módulo da aplicação (ex: catalog)
├── core/                               # Camada de domínio e aplicação (Core Business Rules)
│   ├── application/                    # Camada de aplicação (Application Business Rules)
│   │   ├── inputs/                     # DTOs de entrada para casos de uso
│   │   │   └── mappers/                # Mapeadores Input -> Domínio
│   │   └── usecases/                   # Casos de uso (Application Business Rules)
│   ├── domain/                         # Camada de domínio (Domain Business Rules)
│   │   ├── model/                      # Entidades de domínio
│   │   ├── events/                     # Eventos de domínio
│   │   ├── exceptions/                 # Exceções de domínio
│   │   └── vo/                         # Objetos de valor
│   └── interfaceadapters/              # Camada de adaptação (Interface Adapters)
│       ├── bff/                        # Camada de interface web (BFF - Backend for Frontend)
│       │   └── controller/web/api      # Controllers REST (BFF)
│       ├── presenter/web/api           # Saídas dos casos de uso (Presenter -> ViewModel)
│       ├── dto/                        # DTOs intermediários
│       │   └── mappers/                # Mapeadores DTO <-> Domínio
│       └── gateways/                   # Interfaces de acesso a recursos externos (ex: repos)
└── infrastructure/                     # Camada de infraestrutura (Frameworks e Drivers)
    ├── common/                         # Fontes genéricas, utilitários
    │   └── source                      # DataSource do módulo
    ├── in/                             # Camada de entrada
    │   ├── event/listener/             # Listeners de eventos internos/externos
    │   └── web/api/controller/         # REST controllers (controllers exposto ao mundo externo)
    │       └── dto/                    # DTOs de entrada/saída (web layer)
    ├── out/                            # Camada de saída para sistemas externos
    │   └── persistence/                # Persistência de dados
    │       └── postgres/               # Implementação específica para PostgreSQL
    │           ├── entity/             # Entidades JPA
    │           ├── mapper/             # Mapper Entity <-> Domain
    │           └── repository/         # Implementações de repositórios
    └── config/                         # Configurações específicas do módulo
```

</details>

<h2 id="apis">🌐 APIs</h2>

<details>
<summary>Expandir para mais detalhes</summary>

O sistema expõe duas interfaces principais de API:

1. **API de Auto-Atendimento**: Para clientes se identificarem, visualizarem produtos e fazerem pedidos
2. **API de Gestão**: Para administradores gerenciarem produtos, categorias e pedidos

### Endpoints Principais

#### Usuários

```
POST /api/users                         # Cadastrar usuário
GET /api/users/{id}                     # Obter usuário por id
GET /api/users                          # Listar usuários
```

#### Catálogo

```
GET  /api/catalogs                          # Listar todos os catálogos
GET  /api/catalogs/{id}                     # Listar catálogo por ID
POST /api/catalogs                          # Criar catálogo
GET /api/catalogs/{id}/categories           # Listar categorias de um catálogo
POST /api/catalogs/{id}/categories          # Criar categoria no catálogo
GET /api/catalogs/{id}/products             # Listar produtos de uma categoria
POST /api/catalogs/{id}/products            # Criar produto na categoria
GET /api/catalogs/{id}/products/{productId} # Obter produto por ID
```

#### Pedidos

```
POST  /api/orders                        # Criar pedido
PATCH /api/orders/{id}/status            # Atualizar status do pedido
GET   /api/orders/active                 # Listar pedidos ativos
```

#### Pagamentos

```
GET  /api/payments/{orderId}/status     # Obter status do pagamento
GET  /api/payments/{orderId}/qrCode     # Obter informações do QRCode de pagamento
POST /api/payments/webhook              # Webhook de notificação de pagamento
```

Para documentação completa e interativa, consulte o Swagger/OpenAPI disponível em:
<http://localhost/swagger-ui.html>

</details>

<h2 id="banco-de-dados">💾 Banco de Dados</h2>

<details>
<summary>Expandir para mais detalhes</summary>

### Modelo Relacional

O sistema utiliza PostgreSQL como banco de dados principal, com o seguinte esquema:

![Diagrama Entidade e Relacionamento](docs/diagrams/DER.svg)

### Gerenciamento de Migrações

O projeto utiliza `Liquibase` para gerenciar migrações de banco de dados, organizadas por módulo:

```
src/main/resources/db/changelog/
├── db.changelog-master.yaml          # Arquivo principal
├── modules/                          # Migrations separadas por módulo
│   ├── order/
│   │   ├── 01-order-tables.sql
│   │   ├── 02-order-indexes.sql
│   │   └── 03-order-seed.sql
│   ├── user/
│   ├── catalog/
│   └── payment/
└── shared/
    └── 00-init-schema.sql
```

As migrações são aplicadas automaticamente durante a inicialização da aplicação, mas também podem ser executadas
manualmente:

```bash
./food db:up     # Aplicar migrações
./food db:reset  # Resetar e recriar o banco de dados
```

### Acesso ao Banco de Dados

Para acessar o banco de dados durante o desenvolvimento, utilize o Adminer disponível em:
<http://localhost:8081>

Credenciais:

- Sistema: PostgreSQL
- Servidor: db
- Usuário: postgres
- Senha: postgres
- Banco: fastfood

</details>

<h2 id="resolucao-de-problemas">🔍 Resolução de Problemas</h2>

<details>
<summary>Expandir para mais detalhes</summary>

### Problemas com o Banco de Dados

Se você encontrar erros ao tentar resetar o banco de dados, como:

```
ERROR: database "fastfood" is being accessed by other users
DETAIL: There are X other sessions using the database.
```

Tente estes passos:

```bash
# 1. Parar todos os serviços
./food stop:all

# 2. Limpar recursos Docker não utilizados
./food clean --force

# 3. Iniciar apenas a infraestrutura
./food start:infra

# 4. Tentar o reset novamente
./food db:reset --force
```

### Erros do Liquibase

Se você encontrar erros de validação do Liquibase como:

```
Validation Failed:
     changesets check sum
     changesets had duplicate identifiers
```

Existem duas abordagens:

1. **Limpar completamente o banco de dados:**

```bash
# Parar todos os serviços
./food stop:all

# Limpar recursos
./food clean --force

# Iniciar tudo novamente
./food start:all --build
```

2. **Atualizar a tabela DATABASECHANGELOG (para desenvolvedores):**

```sql
-- Conecte-se ao banco via Adminer e execute:
DELETE FROM DATABASECHANGELOG
WHERE filename = 'db/changelog/modules/product/03-product-seed.sql';

-- Aplique as migrações novamente
./food db:up
```

### Problemas com o Docker

Se o Docker travar ou apresentar problemas:

```bash
# Reinicie o Docker Desktop
# Em seguida, reinicie a infraestrutura
./food restart:all --build
```

### Porta em Uso

Se alguma porta estiver em uso (como 8080, 8081, 8082, 8083, 5432, 6379):

1. Identifique o processo usando a porta:

   ```bash
   lsof -i :<número-da-porta>
   ```

2. Encerre o processo ou altere a porta no arquivo `docker/docker-compose.yml`

</details>

<h2 id="contribuicao-e-licenca">🙏 Contribuição e Licença</h2>

### Guia de Contribuição

Para contribuir com o projeto, siga estas etapas:

#### Branches

- A branch principal de desenvolvimento é a `main`
- Para novas funcionalidades, crie uma branch a partir da `main` seguindo o padrão:
  - `feature/nome-da-funcionalidade`
- Para correções de bugs, use o padrão:
  - `fix/descricao-do-bug`
- Para documentação:
  - `docs/descricao-da-documentacao`
- Para melhorias de performance ou refatoração:
  - `refactor/descricao-da-mudanca`

#### Commits

Siga a convenção [Conventional Commits](https://www.conventionalcommits.org/):

```
<tipo>[escopo opcional]: <descrição>

[corpo opcional]

[rodapé(s) opcional(is)]
```

Exemplos:

- `feat(order): adiciona endpoint para cancelamento de pedido`
- `fix(customer): corrige validação de CPF`
- `docs: atualiza README com novas instruções`
- `refactor(product): melhora desempenho na listagem de produtos`

#### Pull Requests

1. Certifique-se que sua branch está atualizada com a `main`
2. Faça um pull request para a branch `main`
3. Descreva as alterações realizadas de forma clara
4. Vincule issues relacionadas
5. Aguarde a revisão dos mantenedores

---

### Contribuidores

Este projeto é mantido por:

- [Caio Souza](https://github.com/caiuzu)
- [Guilherme Cesar](https://github.com/QuatroQuatros)
- [Marcelo Maga](https://github.com/marcelo-maga)
- [Pedro Ferrarezzo](https://github.com/pedroferrarezzo)

---

### Licença

Este projeto está licenciado sob a licença MIT.

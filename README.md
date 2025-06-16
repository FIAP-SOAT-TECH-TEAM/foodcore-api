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

API de gerenciamento de pedidos para restaurantes fast-food, desenvolvida como parte do curso de Arquitetura de Software da FIAP (Tech Challenge).

<div align="center">
  <a href="#visao-geral">Visão Geral</a> •
  <a href="#arquitetura">Arquitetura</a> •
  <a href="#tecnologias">Tecnologias</a> •
  <a href="#diagramas">Diagramas</a> •
  <a href="#eventstorming">Event Storming</a> •
  <a href="#taskboard">Task Board</a> •
  <a href="#dicionario">Dicionário de linguagem ubíqua</a>
  <a href="#instalacao-e-uso">Instalação e Uso</a> •
  <a href="#estrutura-do-projeto">Estrutura do Projeto</a> • <br/>
  <a href="#apis">APIs</a> •
  <a href="#banco-de-dados">Banco de Dados</a> •
  <a href="#resolucao-de-problemas">Resolução de Problemas</a> •
  <a href="#contribuicao-e-licenca">Contribuição e Licença</a>
</div>

<h2 id="visao-geral">📋 Visão Geral</h2>

O sistema implementa um serviço de auto-atendimento para uma lanchonete de fast-food, permitindo que os clientes façam pedidos e acompanhem o status do seu pedido sem a necessidade de interação com um atendente. A aplicação também inclui um painel administrativo para gerenciamento de produtos, clientes e acompanhamento de pedidos.

### Principais recursos

- **Auto-atendimento**: Interface para clientes realizarem pedidos
- **Identificação de cliente**: Por CPF ou cadastro com nome e e-mail
- **Pagamento integrado**: Via QRCode do Mercado Pago
- **Acompanhamento de pedido**: Status em tempo real (Recebido, Em preparação, Pronto, Finalizado)
- **Painel administrativo**: Gerenciamento de produtos, categorias e pedidos

<h2 id="arquitetura">🏗️ Arquitetura</h2>

<details>
<summary>Expandir para mais detalhes</summary>

O projeto segue uma arquitetura modular baseada em **Domain-Driven Design (DDD)** com **Spring Modulith** e **Arquitetura Hexagonal**, facilitando a manutenção e promovendo desacoplamento.

### Arquitetura Hexagonal (Ports & Adapters)

```mermaid
graph TD
    subgraph "Arquitetura Hexagonal"
        DOMAIN[Domínio]

        subgraph "Portas de Entrada"
            API_Port["API (Porta)"]
            Webhook_Port["Webhook (Porta)"]
            Event_Port["Eventos (Porta)"]
        end

        subgraph "Portas de Saída"
            DB_Port["Banco de Dados (Porta)"]
            Acquirer_Port["Adquirente (Porta)"]
            EventBus_Port["Eventos (Porta)"]
        end

        DOMAIN --- API_Port
        DOMAIN --- Webhook_Port
        DOMAIN --- Event_Port
        DOMAIN --- DB_Port
        DOMAIN --- Acquirer_Port
        DOMAIN --- EventBus_Port

        API_Port --- API_Adapter["/api REST Controller"]
        Webhook_Port --- Webhook_Adapter["Webhook Controller"]
        Event_Port --- Event_Adapter["Event Listener"]

        DB_Port --- DB_Adapter["JPA Repository"]
        Acquirer_Port --- MercadoPago_Adapter["MercadoPago Client"]
        EventBus_Port --- EventBus_Adapter["ApplicationEventPublisher"]
    end
```

### Monolito Modular (Spring Modulith)

A aplicação é estruturada como um monolito modular usando Spring Modulith, com contextos limitados (bounded contexts) bem definidos para cada domínio de negócio:

```mermaid
graph TD
    subgraph "Monolito Modular"
        USER[Módulo Usuário]
        ORDER[Módulo Pedido]
        CATÁLOGO[Módulo Catálago]
        PAYMENT[Módulo Pagamento]
        SHARED[Componentes Compartilhados]

        USER --> CATÁLOGO
        USER --> ORDER
        ORDER --> CATÁLOGO
        ORDER --> PAYMENT
        PAYMENT --> ORDER

        SHARED --> ORDER
        SHARED --> USER
        SHARED --> CATÁLOGO
        SHARED --> PAYMENT
    end
```

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

```mermaid
classDiagram
    class Order {
        -Long id
        -User user
        -String orderNumber
        -OrderStatus status
        -BigDecimal totalAmount
        -List~OrderItem~ items
        -LocalDateTime createdAt
        -LocalDateTime updatedAt
        +addItem(OrderItem)
        +removeItem(OrderItem)
        +calculateTotalAmount()
        +updateStatus(OrderStatus)
    }

    class OrderItem {
        -Long id
        -Product product
        -Integer quantity
        -BigDecimal unitPrice
        -BigDecimal subtotal
        -String observations
        +calculateSubtotal()
    }

    class Catalog {
        -Long id
        -String name
    }

    class Category {
        -Long id
        -Catalog catalog
        -String name
        -String description
        -String imageUrl
        -Integer displayOrder
        -Boolean active
    }

    class Product {
        -Long id
        -Category category
        -String name
        -String description
        -BigDecimal price
        -String imageUrl
        -Integer displayOrder
        -Boolean active
    }

    class User {
        -Long id
        -String name
        -String username
        -String email
        -String password
        -String document
        -Boolean active
        -Boolean guest
        -Role role
        -LocalDateTime lastLogin
        -LocalDateTime createdAt
        -LocalDateTime updatedAt
    }

    class Role{
        <<enumeration>>
        ADMIN
        USER
        GUEST
    }

    class OrderStatus {
        <<enumeration>>
        RECEIVED
        PREPARING
        READY
        COMPLETED
        CANCELLED
    }

    Order "1" *-- "many" OrderItem
    Order "many" -- "1" User
    OrderItem "many" -- "1" Product
    Catalog "many" -- "1" Category
    Category "many" -- "1" Product
    Product -- Category
    Order -- OrderStatus
    User -- Role
```

### DER (Diagrama Entidade-Relacionamento)

```mermaid
erDiagram
    USERS ||--o{ ORDERS : places
    USERS ||--o{ PAYMENTS : makes
    ROLES ||--o{ USERS : places
    ORDERS ||--o{ ORDER_ITEMS : contains
    PRODUCTS ||--o{ ORDER_ITEMS : includes
    PRODUCTS ||--|| STOCK : stored_in
    CATALOG ||--o{ CATEGORIES : has
    CATEGORIES ||--o{ PRODUCTS : categorizes
    ORDERS ||--o{ PAYMENTS : has
    USERS {
        int id PK "ID único do usuário"
        string name "Nome do usuário"
        string username "Nome de usuário"
        string email "e-mail do usuário"
        string password "Hash da senha do usuário"
        string document "Documento do usuário"
        boolean active "Indica se o usuário está ativo"
        boolean guest "Indica se o usuário é convidado"
        int role_id "ID da role do usuário"
        timestamp last_login "Data do último login"
        timestamp created_at "Data de criação do registro"
        timestamp updated_at "Data da última atualização do registro"
    }

    ROLES {
        int id PK "ID único da Role"
        string name "Nome único da role"
        string description "Descrição das permissões do role"
        timestamp created_at "Data de criação do registro"
        timestamp updated_at "Data da última atualização do registro"
    }

    ORDERS {
        int id PK "ID único da order"
        int user_id FK "ID do usuário que criou o pedido"
        varchar order_number "hash aleatoria para identificar o pedido"
        varchar status "status do pedido"
        decimal amount "preço do pedido"
        timestamp created_at "Informações de auditoria"
        timestamp updated_at "Informações de auditoria"
    }

    ORDER_ITEMS {
        int id PK "ID único da order_item"
        int order_id FK "ID do pedido"
        int product_id FK "ID do produto"
        string name "nome do item"
        int quantity "quantidade do item"
        decimal unit_price "preço unitário"
        text observations "oberservações do usuário"
        timestamp created_at "Informações de auditoria"
        timestamp updated_at "Informações de auditoria"
    }

    CATALOG{
        int id PK "ID único da catálogo"
        string name "Nome do catálogo"
        timestamp created_at "Informações de auditoria"
        timestamp updated_at "Informações de auditoria"
    }

    CATEGORIES{
        int id PK "ID único da categoria"
        int catalog_id FK
        string name "Nome da categoria"
        string description "Descrição da categoria"
        string image_url "URL da imagem da categoria"
        int display_order "Ordem de exibição da categoria"
        boolean active "Indica se a categoria está ativa ou não"
        timestamp created_at "Informações de auditoria"
        timestamp updated_at "Informações de auditoria"
    }

    PRODUCTS {
        bigint id PK "ID único do produto"
        bigint category_id FK "ID da categoria do produto"
        varchar name "nome do produto"
        varchar description "descrição do produto"
        decimal price "preço do produto"
        varchar image_url "URL da foto do produto"
        int display_order "ordem de exibição do produto"
        boolean active "status do produto 'ativo ou inativo'"
        timestamp created_at "Informações de auditoria"
        timestamp updated_at "Informações de auditoria"
    }

    STOCK {
        bigint id PK "ID único do stock"
        bigint product_id FK "ID do produto"
        int quantity "quantidade disponivel"
        timestamp created_at "Informações de auditoria"
        timestamp updated_at "Informações de auditoria"
    }

    PAYMENTS {
        int id PK "ID único do pagamento"
        int user_id FK "ID do usuário que criou o pagamento"
        int order_id FK "ID do pedido pago"
        varchar payment_type "tipo de pagamento"
        timestamp expires_in "data de expiração do pagamento"
        varchar status "status do pagamento 'pago, cancelado, pendente'"
        timestamp paid_at "data do pagamento"
        varchar tid "id do pagamento na adquirente"
        decimal amount "valor do pagamento"
        varchar qr_code "código do qr_code do pagamento"
        text observations "Observações do usuário para o pagamento"
        timestamp created_at "Informações de auditoria"
        timestamp updated_at "Informações de auditoria"
    }
```

```mermaid
flowchart TD
    %% Eventos de Domínio
    E1[CustomerIdentified] --> E2[OrderCreated]
    E2 --> E3[ItemAdded]
    E3 --> E4[OrderConfirmed]
    E4 --> E5[PaymentRequested]
    E5 --> E6[QRCodeGenerated]
    E6 --> E7[PaymentReceived]
    E7 --> E8[OrderReceived]

    %% Comandos
    C1[IdentifyCustomer] --> E1
    C2[CreateOrder] --> E2
    C3[AddItem] --> E3
    C4[ConfirmOrder] --> E4
    C5[RequestPayment] --> E5
    C6[GenerateQRCode] --> E6
    C7[ConfirmPayment] --> E7
    C8[ReceiveOrder] --> E8

    %% Atores
    A1[Customer] --> C1
    A1 --> C2
    A1 --> C3
    A1 --> C4
    A1 --> C5
    A2[PaymentSystem] --> C7
    A3[Attendant] --> C8
```

```mermaid
flowchart TD
    %% Eventos de Domínio
    E1[OrderReceived] --> E2[PreparationStarted]
    E2 --> E3[OrderReady]
    E3 --> E4[CustomerNotified]
    E4 --> E5[OrderDelivered]
    E5 --> E6[OrderFinished]

    %% Comandos
    C1[StartPreparation] --> E2
    C2[MarkAsReady] --> E3
    C3[NotifyCustomer] --> E4
    C4[DeliverOrder] --> E5
    C5[FinishOrder] --> E6

    %% Atores
    A1[Cook] --> C1
    A1 --> C2
    A2[System] --> C3
    A3[Attendant] --> C4
    A3 --> C5

```

### Fluxo de Realização do Pedido e Pagamento (Event Storming)

```mermaid
flowchart TD
    %% Eventos de Domínio
    E1[CustomerIdentified] --> E2[OrderCreated]
    E2 --> E3[ItemAdded]
    E3 --> E4[OrderConfirmed]
    E4 --> E5[PaymentRequested]
    E5 --> E6[QRCodeGenerated]
    E6 --> E7[PaymentReceived]
    E7 --> E8[OrderReceived]

    %% Comandos
    C1[IdentifyCustomer] --> E1
    C2[CreateOrder] --> E2
    C3[AddItem] --> E3
    C4[ConfirmOrder] --> E4
    C5[RequestPayment] --> E5
    C6[GenerateQRCode] --> E6
    C7[ConfirmPayment] --> E7
    C8[ReceiveOrder] --> E8

    %% Atores
    A1[Customer] --> C1
    A1 --> C2
    A1 --> C3
    A1 --> C4
    A1 --> C5
    A2[PaymentSystem] --> C7
    A3[Attendant] --> C8
```

### Fluxo de Preparação e Entrega do Pedido (Event Storming)

```mermaid
flowchart TD
    %% Eventos de Domínio
    E1[OrderReceived] --> E2[PreparationStarted]
    E2 --> E3[OrderReady]
    E3 --> E4[CustomerNotified]
    E4 --> E5[OrderDelivered]
    E5 --> E6[OrderFinished]

    %% Comandos
    C1[StartPreparation] --> E2
    C2[MarkAsReady] --> E3
    C3[NotifyCustomer] --> E4
    C4[DeliverOrder] --> E5
    C5[FinishOrder] --> E6

    %% Atores
    A1[Cook] --> C1
    A1 --> C2
    A2[System] --> C3
    A3[Attendant] --> C4
    A3 --> C5
```

</details>

<h2 id="eventstorming"> 💡Event Storming</h2>
<details>
<summary>Expandir para mais detalhes</summary>

### Event Storming Miro
- https://miro.com/app/board/uXjVIAFD_zg=/?share_link_id=933422566141
  
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
  Instituição financeira responsável por processar transações de pagamento do sistema. No nosso caso, a adquirente é representada pela integração com o [Mercado Pago](https://www.mercadopago.com.br).

- **Authentication (Autenticação)**
  Processo de validação da identidade de um usuário por meio de login.

- **Authorization (Autorização)**
  Controle de acesso baseado em permissões e papéis (roles). Exemplo: apenas administradores podem listar todos os usuários.

- **Catalog (Catálogo de Produtos)**
  Conjunto organizado dos produtos disponíveis para seleção e montagem de pedidos.

- **Category (Categoria)**
  Classificação dos produtos por tipo (ex.: lanches, bebidas, sobremesas).

- **Combo**
  Conjunto personalizado por um cliente, composto por: lanche, acompanhamento, bebida e sobremesa.

- **Customer (Cliente)**
  Pessoa que realiza um pedido no sistema. Pode se identificar com CPF, cadastrar nome/e-mail ou seguir como convidado (guest).

- **Guest (Convidado)**
  Cliente que realiza um pedido sem se identificar ou criar conta. Atua como usuário temporário.

- **Mercado Pago Integration (Integração com Mercado Pago)**
  Serviço externo utilizado para processar pagamentos eletrônicos dos pedidos.

- **Order (Pedido)**
  Conjunto de itens selecionados por um cliente para consumo. Pode incluir um ou mais combos.

- **Order Item (Item do Pedido)**
  Produto específico dentro de um pedido. Pode ser parte de um combo ou avulso.

- **Payment (Pagamento)**
  Etapa posterior à finalização do pedido. Utiliza integração com o Mercado Pago para processar as transações financeiras.

- **Expiração (Pagamento)**
  Tempo de expiração para pagamento de QrCode gerado pelo adquirente. Por padrão, 30 minutos, após esgotar o tempo o pedido relacionado é cancelado.

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
- JDK 21+
- Gradle 8.0+

### Script Centralizador `food`

O projeto utiliza um script centralizador `food` para gerenciar todas as operações:

```bash
./food [comando] [opções]
```

#### Comandos Principais

| Comando | Descrição |
|---------|-----------|
| `start:all` | Inicia toda a infraestrutura e a aplicação |
| `start:infra` | Inicia apenas a infraestrutura (banco) |
| `start:app` | Inicia apenas a aplicação |
| `stop:all` | Para todos os serviços |
| `stop:infra` | Para apenas a infraestrutura |
| `stop:app` | Para apenas a aplicação |
| `restart:all` | Reinicia todos os serviços |
| `restart:app` | Reinicia apenas a aplicação |
| `db:up` | Aplica migrações do banco de dados |
| `db:reset` | Reseta o banco de dados |
| `logs` | Exibe logs dos containers |
| `logs:app` | Exibe logs apenas da aplicação |
| `logs:db` | Exibe logs apenas do banco de dados |
| `status` | Exibe status dos containers |
| `clean` | Remove containers, imagens e volumes não utilizados |
| `help` | Exibe a mensagem de ajuda |

#### Opções

- `--build`, `-b`: Reconstrói as imagens antes de iniciar
- `--force`, `-f`: Força a execução sem confirmação

### Iniciando o Ambiente do Zero

```bash
# Clone o repositório
git clone https://github.com/soat-fiap/food-core-api.git
cd food-core-api

# Tornar o script principal executável
chmod +x food

# Iniciar infraestrutura (banco, adminer)
./food start:infra

# Resetar e configurar o banco de dados
./food db:reset

# Iniciar a aplicação
./food start:app --build

# Ou iniciar tudo de uma vez
./food start:all --build
```

### Acessando a Aplicação

- **API**: <http://localhost/api>
- **Swagger/OpenAPI**: <http://localhost/api/swagger-ui.html>
- **Adminer (gerenciador de banco de dados)**: <http://localhost:8081>
  - Sistema: PostgreSQL
  - Servidor: db
  - Usuário: postgres
  - Senha: postgres
  - Banco: fastfood

### Ambientes e Dados de Seed

O projeto suporta diferentes ambientes com diferentes conjuntos de dados:

- **Produção (perfil: prod)**: Apenas dados essenciais
- **Desenvolvimento (perfil: dev/local)**: Dados essenciais + dados adicionais para testes

Para executar a aplicação em modo de desenvolvimento:

```bash
# Usando variável de ambiente SPRING_PROFILES_ACTIVE
SPRING_PROFILES_ACTIVE=dev ./gradlew bootRun

# Ou usando parâmetro na linha de comando
./gradlew bootRun --args='--spring.profiles.active=local'
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
│   │   │   ├── catalog/                        # Módulo Catálogo
│   │   │   │   ├── application/                # Portas e serviços de aplicação
│   │   │   │   ├── domain/                     # Modelos de domínio e regras de negócio
│   │   │   │   ├── mapper/                     # Mappers entre domínio e DTOs
│   │   │   │   └── infrastructure/             # Implementações de adaptadores
│   │   │   │
│   │   │   ├── order/                          # Módulo Pedidos
│   │   │   ├── payment/                        # Módulo Pagamento
│   │   │   ├── user/                           # Módulo Usuários
│   │   │   └── shared/                         # Componentes compartilhados
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
├── scripts/                                    # Scripts de gerenciamento
│
├── docs/                                       # Documentação
│
├── food                                        # Script centralizador
└── README.md                                   # Este arquivo
```

### Organização Modular (DDD / Arquitetura Hexagonal)

Cada módulo segue a mesma estrutura, implementando a arquitetura hexagonal:

```
módulo/
├── application/                          # Camada de aplicação
│   ├── ports/                            # Portas
│   │   ├── in/                           # Portas de entrada
│   │   │   └── ModuloUseCase.java
│   │   └── out/                          # Portas de saída
│   │       └── ModuloRepository.java
│   └── services/                         # Serviços de aplicação
│       └── ModuloService.java
├── domain/                               # Domínio
│   ├── model/                            # Entidades e objetos de valor
│   │   ├── Entidade.java
│   │   └── ObjetoValor.java
│   ├── events/                           # Eventos de domínio
│   │   └── EntidadeCreatedEvent.java
│   └── exception/                        # Exceções de domínio
├── mapper/                               # Mappers entre domínio e DTOs
│   └── ModuloDtoMapper.java
└── infrastructure/                       # Infraestrutura
    ├── adapters/                         # Adaptadores
    │   ├── in/                           # Adaptadores de entrada
    │   │   ├── controller/               # Controllers REST
    │   │   │   └── ModuloController.java
    │   │   └── dto/                      # DTOs para comunicação externa
    │   │       ├── request/              # DTOs de requisição
    │   │       └── response/             # DTOs de resposta
    │   └── out/                          # Adaptadores de saída
    │       ├── persistence/              # Persistência de dados
    │       │   ├── entity/               # Entidades JPA
    │       │   ├── mapper/               # Mappers entre entidades e domínio
    │       │   └── repository/           # Repositórios Spring Data
    │       └── integration/              # Integrações com serviços externos
    └── config/                           # Configurações específicas do módulo
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
<http://localhost:8083/swagger-ui.html>

</details>

<h2 id="banco-de-dados">💾 Banco de Dados</h2>

<details>
<summary>Expandir para mais detalhes</summary>

### Modelo Relacional

O sistema utiliza PostgreSQL como banco de dados principal, com o seguinte esquema:

```mermaid
erDiagram
    USERS ||--o{ ORDERS : places
    USERS ||--o{ PAYMENTS : makes
    ROLES ||--o{ USERS : places
    ORDERS ||--o{ ORDER_ITEMS : contains
    PRODUCTS ||--o{ ORDER_ITEMS : includes
    PRODUCTS ||--|| STOCK : stored_in
    CATALOG ||--o{ CATEGORIES : has
    CATEGORIES ||--o{ PRODUCTS : categorizes
    ORDERS ||--o{ PAYMENTS : has

    USERS {
        id BIGINT PK
        name VARCHAR(200)
        username VARCHAR(200)
        email VARCHAR(200)
        password VARCHAR(200)
        document VARCHAR(11)
        active BOOLEAN
        guest BOOLEAN
        role_id BIGINT FK
        last_login TIMESTAMP
        created_at TIMESTAMP
        updated_at TIMESTAMP
    }

    ROLES {
        id BIGINT PK
        name VARCHAR(200)
        description VARCHAR(200)
        created_at TIMESTAMP
        updated_at TIMESTAMP
    }

    ORDERS {
        id BIGINT PK
        user_id BIGINT FK
        order_number VARCHAR(200)
        status VARCHAR(200)
        amount DECIMAL(10_2)
        created_at TIMESTAMP
        updated_at TIMESTAMP
    }

    ORDER_ITEMS {
        id BIGINT PK
        order_id BIGINT FK
        product_id BIGINT FK
        name VARCHAR(200)
        quantity INT
        unit_price DECIMAL(10_2)
        created_at TIMESTAMP
        updated_at TIMESTAMP
    }

    CATALOG{
        id BIGINT PK
        name VARCHAR(200)
        created_at TIMESTAMP
        updated_at TIMESTAMP
    }

    CATEGORIES{
        id BIGINT PK
        catalog_id BIGINT FK
        name VARCHAR(200)
        description VARCHAR(300)
        image_url VARCHAR(300)
        display_order INT
        active BOOLEAN
        created_at TIMESTAMP
        updated_at TIMESTAMP
    }

    PRODUCTS {
        id BIGINT PK
        catagory_id BIGINT FK
        name VARCHAR(200)
        description VARCHAR(300)
        price DECIMAL(10_2)
        image_url VARCHAR(300)
        display_order INT
        active BOOLEAN
        created_at TIMESTAMP
        updated_at TIMESTAMP
    }

    STOCK {
        id BIGINT PK
        product_id BIGINT FK
        quantity INT
        created_at TIMESTAMP
        updated_at TIMESTAMP
    }

    PAYMENTS {
        id BIGINT PK
        user_id BIGINT FK
        order_id BIGINT FK
        payment_type VARCHAR(100)
        expires_in TIMESTAMP
        status VARCHAR(100)
        paid_at TIMESTAMP
        tid VARCHAR(300)
        amount DECIMAL(10_2)
        tid qr_code(300)
        observations TEXT
        created_at TIMESTAMP
        updated_at TIMESTAMP
    }
```

### Gerenciamento de Migrações

O projeto utiliza Liquibase para gerenciar migrações de banco de dados, organizadas por módulo:

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

As migrações são aplicadas automaticamente durante a inicialização da aplicação, mas também podem ser executadas manualmente:

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

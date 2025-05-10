# SonarQube Docker

Este diretório contém a configuração Docker para o SonarQube, usado para análise estática de código no projeto Food Core API.

## Configuração

O arquivo `docker-compose.yml` configura:

- SonarQube LTS
- Banco de dados PostgreSQL para o SonarQube
- Redes e volumes necessários

## Uso

Para gerenciar o SonarQube, use o script principal do projeto:

```bash
# Iniciar o SonarQube
./food start:sonar

# Parar o SonarQube
./food stop:sonar

# Reiniciar o SonarQube
./food restart:sonar

# Ver logs do SonarQube
./food logs:sonar

# Executar análise
./food sonar:analyze
```

## Acesso

- URL: <http://localhost:9001>
- Usuário padrão: admin
- Senha inicial: admin (será solicitado alteração no primeiro acesso)

## Configuração do Projeto

O arquivo `build.gradle` na raiz do projeto está configurado para enviar relatórios de análise para o SonarQube local.

Para mais detalhes sobre a integração com SonarQube, consulte a documentação em [docs/sonarqube.md](/docs/sonarqube.md).

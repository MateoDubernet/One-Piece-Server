# One Piece Server

## Projet lié
Cette application est utilisé conjointement avec le projet client disponible ici :
https://github.com/MateoDubernet/One-Piece-Client

---

## Prérequis

- **Java 11** (Amazon Corretto 11 recommandé)
- **IntelliJ IDEA** (ou tout autre IDE compatible Spring Boot)

---

## Installation & Lancement
### 1. Cloner le projet
```bash
    git clone <url-du-repo>
    cd <nom-du-dossier>
```
Ouvrir le projet dans IntelliJ

### 2. Configuration du projet
 1. **Configurer Java 11 (Corretto) dans IntelliJ**
     - Aller dans **File → Project Structure → Project**
     - Choisir **Project SDK → Corretto 11**
     - S'assurer que le **Project language level** est au moins `11`.
 
 2. **Configurer la connexion à PostgreSQL**
     - Dans `src/main/resources/application.properties`:
 ```properties
 spring.datasource.url=jdbc:postgresql://localhost:5432/tondb
 spring.datasource.username=tonuser
 spring.datasource.password=tonpassword
 spring.jpa.hibernate.ddl-auto=update
 spring.jpa.show-sql=true
 ```

### 3. Lancer l’application côté serveur


### 4. Lancer l’application côté client
Lien client: https://github.com/MateoDubernet/One-Piece-Client

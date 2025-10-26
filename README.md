# One Piece Server

## Contexte

### Description
Il s'agit d'un projet réaliser durant mon alternance dans le but de me former aux frameworks Angular et Spring.\
Ceci est la partie serveur du projet qui utilise le framework Spring et le langage Java, il gére la récupération et sauvegarde des données en base et communique avec le client via une API REST.

### Projet lié
Cette application est utilisé conjointement avec le projet client disponible ici :
https://github.com/MateoDubernet/One-Piece-Client

---

## Prérequis

- **Java 11** (Amazon Corretto 11 recommandé)
- **IntelliJ IDEA** (ou tout autre IDE compatible Spring Boot)
- **PostgreSQL** pour la base de données

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


2. **Ajouter une configuration pour le projet**
    - Aller dans **Current File → Edit configurations...**
    - Ajouter une configuration **Application**
    - Dans **Build & run -> Main class** sélectionner : **com.example.demo.DemoApplication**


3. **Configurer la connexion à la base de données**
   - Dans `src/main/resources/application.properties` remplacer les valeurs de connexions par celles adapter
   - Créer la base de données **one_piece** avec postgreSQL

### 3. Lancer l’application côté serveur
Appuyer sur le bouton play de IntellIj

### 4. Lancer l’application côté client
Lien client: https://github.com/MateoDubernet/One-Piece-Client





# Bati-Cuisine
Description
Bati-Cuisine est une application Java destinée aux professionnels de la construction et de la rénovation de cuisines. Elle permet de calculer le coût total des travaux en tenant compte des matériaux, de la main-d'œuvre, des taxes, et de la marge bénéficiaire. L'application offre également des fonctionnalités pour la gestion des clients, la création de devis personnalisés, et une vue d'ensemble sur les aspects financiers et logistiques des projets de rénovation.

## Fonctionnalités
1. **Gestion des Projets**

  - Ajouter un client au projet.
  - Ajouter et gérer des composants (matériaux, main-d'œuvre).
  - Associer un devis au projet pour estimer les coûts.
  - Suivi du statut du projet.
    Gestion des Composants

2. **Gestion des Composants**
    - Gestion des matériaux (coûts, quantité, transport, qualité).
    - Gestion de la main-d'œuvre (taux horaire, heures travaillées, productivité).
3. **Gestion des Clients**
   - Enregistrement des informations de base.
   - Différenciation entre clients professionnels et particuliers.
   - Application de remises spécifiques.
4. **Création de Devis**
   - Génération d'un devis avec estimation des coûts.
   - Gestion des dates d’émission et de validité.
   - Enregistrement de l'acceptation du devis par le client.

5. **Calcul des Coûts**
    - Calcul du coût total en incluant les matériaux, la main-d'œuvre, et les taxes.
    - Application d'une marge bénéficiaire.


6. **Affichage des Détails et Résultats**
    - Affichage détaillé du projet, des clients, et des devis.
    - Génération d'un récapitulatif détaillé des coûts.
    - 
## Prérequis

- **Java 8**: Assurez-vous d'avoir Java 8 installé.
- **PostgreSQL**: La base de données est utilisée pour stocker les informations.
- **JDBC**: Pour la connexion à la base de données.


## Installation

1. **Clonez le dépôt**

git clone https://github.com/Sahtani/Bati-Cuisine.git

2. **Configurez la base de données PostgreSQL**

   Exécutez les scripts SQL fournis pour créer et initialiser la base de données.

   `psql -U postgres -f scripts/init_db.sql`
3. **Compilez et exécutez l'application**

   Naviguez jusqu'au répertoire du projet et compilez l'application :

   ```bash
   cd bati-cuisine
   mvn clean install
## Utilisation
1. **Lancer l'application**

Lorsque l'application démarre, un menu principal s'affiche avec les options suivantes :

* Créer un nouveau projet
* Afficher les projets existants
* Calculer le coût d'un projet
* Quitter
2. **Créer un projet**

Suivez les instructions à l'écran pour ajouter un client, entrer les détails du projet, ajouter des matériaux et de la main-d'œuvre, et générer un devis.

3. **Afficher les projets**

Consultez les projets existants et leurs détails.

4. **Calculer le coût**



Estimez le coût total d'un projet en incluant les matériaux, la main-d'œuvre, les taxes, et la marge bénéficiaire.

# Architecture
Modèle: Contient les entités principales comme Projet, Client, Composant, et Devis.
DAO (Data Access Object): Gestion des opérations de base de données.
Service: Logique métier pour la gestion des projets, des clients, des composants, et des devis.
UI (Interface Utilisateur): Interface console pour l'interaction avec l'utilisateur.
Patterns: Utilisation des patterns Singleton et Repository.
Diagramme de Classes
Le diagramme de classes UML est disponible ici.

Dépôt Git
Le code source est disponible sur GitHub.

JIRA
Suivez l'avancement du projet sur JIRA.

Fichiers de Base de Données
config/data.sql: Script SQL pour la création et l'initialisation de la base de données.
# Diagramme de Classes
Le diagramme de classes UML est disponible ici.

# JIRA
Suivez l'avancement du projet sur JIRA.
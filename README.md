

# Kotlin Database App

## Démo
https://github.com/user-attachments/assets/77457dda-9c9c-4e4f-9921-cf4e1618126f

## Description

Cette application Android est un exemple d’utilisation de la base de données locale Room pour gérer des utilisateurs, avec une interface utilisateur comprenant une RecyclerView et un formulaire pour ajouter des utilisateurs. Elle permet également d’afficher les préférences de l’utilisateur, y compris la possibilité de passer en mode clair ou sombre via un `SettingsFragment`.

## Fonctionnalités

- **Ajout d'utilisateurs** : Ajouter des utilisateurs à l'aide d'un formulaire.
- **Affichage des utilisateurs** : Voir la liste des utilisateurs dans une RecyclerView.
- **Persistance des données** : Stocker les utilisateurs dans une base de données locale Room.
- **Préférences utilisateur** : Gérer les préférences via un `SettingsFragment`, incluant le choix du thème (clair, sombre, ou système par défaut).
- **Initialisation des données** : Charger des utilisateurs depuis un fichier JSON situé dans `res/raw` au démarrage de l'application.

## Structure du Projet

- `MainActivity` : Gère la RecyclerView et le formulaire d'ajout d'utilisateurs.
- `User` : Classe de données représentant un utilisateur avec des champs comme `id`, `name`, et `email`.
- `UserAdapter` : Adapteur pour afficher les utilisateurs dans une RecyclerView.
- `UserDao` : Interface pour les opérations sur la base de données Room (insertion, suppression, etc.).
- `UserRepository` : Fournit une couche intermédiaire entre le ViewModel et le DAO.
- `UserViewModel` : Gère les opérations logiques liées aux utilisateurs en lien avec l'interface utilisateur.
- `SettingsFragment` : Permet de gérer les préférences utilisateur.

## Prérequis

- Android Studio
- Kotlin
- API minimum : 24
- Utilisation de la bibliothèque Room pour la base de données locale

## Installation

1. Clonez ce dépôt :
   ```bash
   git clone <url-du-repo>
   ```
2. Ouvrez le projet dans Android Studio.
3. Exécutez l'application sur un émulateur ou un appareil physique.

## Fonctionnalités Détaillées

### Base de Données

L'application utilise Room comme couche d'abstraction sur SQLite. La base de données est initialisée avec des utilisateurs issus d'un fichier JSON situé dans `res/raw/users.json`. Les tables sont vidées à chaque démarrage.

### Gestion des Préférences

Un `SettingsFragment` permet de modifier les préférences utilisateur, notamment le thème de l'application (clair, sombre, ou système par défaut). Le changement de thème est appliqué dynamiquement.

### RecyclerView

Les utilisateurs sont affichés dans une `RecyclerView`, mise à jour dynamiquement lorsqu'un utilisateur est ajouté ou supprimé.

### Formulaire d'Ajout d'Utilisateurs

Un formulaire en bas de l'écran permet d'ajouter un nouvel utilisateur avec un nom et une adresse e-mail. Les champs sont validés avant l'insertion.

## Développement Futur

- Ajout d'une fonctionnalité pour modifier un utilisateur existant.
- Amélioration de l'interface utilisateur pour une meilleure ergonomie.
- Intégration d'une API REST pour synchroniser les utilisateurs avec un backend distant.

## Contributeurs

- [Votre Nom](mailto\:votremail@example.com)

## Licence

Ce projet est sous licence MIT. Vous pouvez en savoir plus dans le fichier `LICENSE`.
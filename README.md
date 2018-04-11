# Archetype Angular 3-Tiers

## En D�veloppement

Il faut lancer deux terminaux, un pour le frontend et l'autre pour le backend.

Pour cela, utiliser le script � la racine de l'archetype `OpenConsole.cmd`, apr�s l'avoir compl�t� avec les chemins vers vos outils.

Pour le backend :

Se placer dans `archetype-angular-3tiers\yourapplication_SERV_WAR` et lancer la commande `mvn spring-boot:run`

Cette commande va builder le backend et lancer l'application dans le serveur Tomcat embarqu� dans Spring-Boot.

Pour le frontend :

Se placer dans `archetype-angular-3tiers\yourapplication_PRES_WAR` et lancer la commande `npm start`

Cette commande va builder le frontend et lancer le serveur de d�veloppement de Node.

L'application est accessible � l'adresse : `http://localhost:4200/`.

## Build

Pour builder l'application sous forme d'EAR, lancer la commande habituelle pour un projet Maven `mvn clean package` ou `mvn clean install` � la racine du projet. # CSERVICES

Projet de Romain DELAITRE et Oriane PLANEL

Colt Express

Le projet que nous avons réalisé avait pour but de construire une version électronique et un peu simplifiée du jeu Colt Express. Les règles du jeu sont les suivantes : Le jeu se déroule à bord d’un train, composé d’une locomotive et d’un certain nombre de wagons. Les joueurs incarnent des bandits qui ont sauté à bord pour détrousser les passagers. Objectif : récupérer le plus de butin possible, chacun pour soi.
Nous avons pu traiter les différentes parties du projet, à savoir tout d’abord créer un modèle réduit du jeu, puis mettre à jour l’affichage du train afin que le modèle soit animé, ensuite rajouter les butins composés de sacs d'argent et de pierres précieuses, ainsi le Marshall dans le modèle. Une fois tous les éléments du jeu implémentés, nous avons ajouté les fonctionnalités relatives aux personnages, et fait en sorte de pouvoir jouer à plusieurs.
Ce qui comprend donc bien sur les mouvements des personnages et les possibilités de tir et de collecter du butin ou encore un affichage des action sur la fenêtre du jeu.
Pour améliorer l’interface utilisateur, nous avons décidé de rajouter des bruitages pour certaines actions (tirs, train) ainsi qu’une musique de fond.
Même si nous sommes parvenus à construire le jeu, nous avons trouvé que le projet était difficile de part sa grande part de liberté, en effet il était facile de se perdre dans toutes les possibilités d’implémenter un même composé.

L’implémentation de bruitage dans le jeu est inspirée de code que nous avons trouvé sur un site web (Stackoverflow), que nous avons adapté au projet.

Pour ce qui est de l'architecture du programme, nous avons une class Board qui contrôle tout le jeu.
Les class d'affichage tels que MenuPanel ou GamePanel se contentes d'afficher les informations de Board et de lui transmettre les actions utilisateurs.
Les autres classes sont des objets comme pour les element à ramasser (Collectibles) dont les classes filles sont MoneyBag et Gem.
Ou encore la classe des personnages, Character qui a deux classes filles auc rôles très différents: Marshal et Bandit.
La classe Board contient donc les objects du programme.

git: https://github.com/FirePower-Git/ColtExpress.git

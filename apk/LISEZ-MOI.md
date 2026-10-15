# Ny Aina Med's — application Android (APK)

Le workflow `.github/workflows/apk.yml` transforme votre `index.html` (à la racine du dépôt) en application Android (Capacitor), la signe et la publie dans l'onglet **Releases**. Votre site web (GitHub Pages) n'est pas modifié.

## Construire l'APK
- Automatique : à chaque modification de `index.html` ou du dossier `apk/` sur la branche `main`.
- Manuel : Actions > « Construire l'APK Android » > **Run workflow**.
- Résultat (environ 5 à 8 min) : Releases > `apk-latest` > `NyAinaMeds.apk`.

## Installer sur le téléphone
Télécharger `NyAinaMeds.apk`, l'ouvrir, autoriser « Installer des applications inconnues » pour le navigateur si Android le demande. Les mises à jour s'installent par-dessus et conservent les données (favoris, poids, thème).

## Impression et export PDF
Les boutons « Imprimer » et « Export PDF » ouvrent la fenêtre d'impression Android : choisir **Enregistrer au format PDF** (liste des imprimantes en haut), puis Enregistrer.

## Personnaliser
- Icône : remplacer `apk/icon.png` (carré, 1024 x 1024, fond uni de préférence : la couleur du coin haut-gauche devient le fond de l'icône).
- Nom de l'application, identifiant : `apk/capacitor.config.json` (changer `appId` oblige aussi à adapter `package mg.nyaina.urgences` dans les deux fichiers .java).
- Ne jamais changer `ny-aina.keystore` après la première installation : Android refuserait les mises à jour.

## Sécurité
La clé de signature est dans le dépôt (public) : adaptée à une diffusion directe de l'APK, pas à Google Play. Pour Google Play, il faudrait une clé privée stockée dans les « Secrets » GitHub.

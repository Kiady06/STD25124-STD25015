# STD25124 + STD 25015

## Comment ne pas mettre son mdp postgres :
* ajouter une dependance dotenv
* mettre son mdp dans le dotenv
* injecter les variables dotenv dans le code en utilisant ${NOM_VARIABLE_DOTENV}
* mettre le .env dans gitignore
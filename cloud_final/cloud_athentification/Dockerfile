# Utiliser l'image officielle PHP 8.2 avec CLI (Command Line Interface)
FROM php:8.2.12-cli

# Mettre à jour les paquets et installer les dépendances nécessaires pour PostgreSQL et Symfony
RUN apt-get update && apt-get install -y \
    zip \                                 
    git \                                
    unzip \                              
    libpq-dev \
    && docker-php-ext-install \
    pdo_pgsql \
    && apt-get clean
# Installer Composer (gestionnaire de dépendances PHP)
RUN curl -sS https://getcomposer.org/installer | php -- --install-dir=/usr/local/bin --filename=composer

# Définir le répertoire de travail
WORKDIR /var/www/html

# Copier les fichiers de l'application Symfony dans le conteneur
COPY . .

# Installer les dépendances de Composer depuis le fichier composer.json
RUN composer install --no-interaction --optimize-autoloader --working-dir=/var/www/html/cloudProject


# Donner les droits d'exécution aux fichiers nécessaires (s'assurer que le serveur Apache ou PHP puisse accéder aux fichiers)
RUN chown -R www-data:www-data /var/www/html/cloudProject/var /var/www/html/cloudProject/public

# Exposer le port pour le serveur Symfony (par défaut 8000)
EXPOSE 8000

# Commande par défaut pour exécuter le serveur Symfony
CMD ["php", "-S", "0.0.0.0:8000", "-t", "cloudProject/public"]

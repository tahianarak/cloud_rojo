<?php

namespace App\Service\Tentative;

use Doctrine\DBAL\Connection;
use function PHPUnit\Framework\throwException;

class UtilisateurTentative
{
    private Connection $connection;
    private $tentative;
    public function __construct(Connection $connection)
    {
        $this->connection = $connection;
        $this->tentative = $_ENV['TENTATIVE_LOGIN'];;
    }

    //Nombre tentative restant
    public function getTentative(string $login): ?int
    {
        $queryBuilder = $this->connection->createQueryBuilder();
        $result = $queryBuilder
            ->select('tentative_restant') // Sélectionne uniquement la colonne nécessaire
            ->from('utilisateur')
            ->where('email = :login')
            ->setParameter('login', $login)
            ->executeQuery()
            ->fetchAssociative(); // Récupère une seule ligne sous forme de tableau associatif
        return $result ? (int) $result['tentative_restant'] : null;
    }

    //utilisateur by id
    public function getUtilisateurById(int $idUser) : ?array
    {
        $queryBuilder = $this->connection->createQueryBuilder();

        $result =  $queryBuilder
            ->select('*')
            ->from("utilisateur")
            ->where("id_utilisateur = :idUser")
            ->setParameter('idUser', $idUser)
            ->executeQuery()
            ->fetchAssociative();
        return $result;
    }

    //utilisateur by mail
    public function getUtilisateurByMail(string $mail) : ?array
    {
        $queryBuilder = $this->connection->createQueryBuilder();

        $result =  $queryBuilder
            ->select('*')
            ->from("utilisateur")
            ->where("email = :mail")
            ->setParameter('mail', $mail)
            ->executeQuery()
            ->fetchAssociative();
        return $result;
    }

    //dicrease tentative by mail of user
    public function dicreaseTentative(string $login) : void
    {
        $queryBuilder = $this->connection->createQueryBuilder();

        $queryBuilder
            ->update('utilisateur')
            ->set('tentative_restant', 'tentative_restant - 1')
            ->where('email = :login')
            ->setParameter('login', $login);

        $queryBuilder->executeStatement();
    }

    //dicrease tentative by id of user
    public function dicreaseTentativeById(string $id) : void
    {
        $queryBuilder = $this->connection->createQueryBuilder();

        $queryBuilder
            ->update('utilisateur')
            ->set('tentative_restant', 'tentative_restant - 1')
            ->where('id_utilisateur = :id')
            ->setParameter('id', $id);
        $queryBuilder->executeStatement();
    }

    //unlock account (set tentative = 3)
    public function unclockAccount(int $id): void
    {
        $queryBuilder = $this->connection->createQueryBuilder();

        $queryBuilder
            ->update('utilisateur')
            ->set('tentative_restant', $this->tentative)
            ->where('id_utilisateur = :id') // Utilisation de :id
            ->setParameter('id', $id); // Correspondance exacte avec :id
        $queryBuilder->executeStatement();
    }

    //inserer dans la table validation_unlock
    public function insertInUnlock(int $idUser, int $pin): void
    {
        $queryBuilder = $this->connection->createQueryBuilder();

        $queryBuilder
            ->insert('validation_unlock') // Table dans laquelle on insère
            ->values([
                'pin' => ':pin',
                'id_utilisateur' => ':id_utilisateur',
            ])
            ->setParameter('pin', $pin)
            ->setParameter('id_utilisateur', $idUser);  // Assurez-vous que `id_utilisateur` est bien défini ici
        $queryBuilder->executeStatement();
    }
    public function get_unlcok_user (int $idUser,int $pin) : ?array
    {
        $queryBuilder = $this->connection->createQueryBuilder();
        $result = $queryBuilder->select('*')
            ->from("validation_unlock")
            ->where("id_utilisateur = :idUser AND pin = :pin")
            ->setParameter('idUser', $idUser)
            ->setParameter('pin', $pin)
            ->executeQuery()
            ->fetchAssociative();
        if (!$result || $result == null)
        {
            throw new \Exception("Validation Expired");
        }
        return $result;
    }

    public function deleteInUnlock(int $idUser, string $pin): void
    {
        $queryBuilder = $this->connection->createQueryBuilder();

        $queryBuilder
            ->delete('validation_unlock') // Spécifie la table à partir de laquelle on supprime
            ->where('id_utilisateur = :id_utilisateur')
            ->andWhere('pin = :pin')
            ->setParameter('id_utilisateur', $idUser)
            ->setParameter('pin', $pin);

        $queryBuilder->executeStatement();
    }

}
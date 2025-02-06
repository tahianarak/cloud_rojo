<?php

namespace App\Service;
use Doctrine\DBAL\Connection;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Session\SessionInterface;


class InscriptionService
{
    private $connection;
    private $tentative;
    private AuthentificationService $authentificationService;
    public function __construct(Connection $connection,AuthentificationService $authentificationService)
    {
        $this->connection = $connection;
        $this->authentificationService = $authentificationService;
        $this->tentative = $_ENV['TENTATIVE_LOGIN'];
    }

    public function formatResponse($data, $error): array
    {
        return [
            'status' => $error ? 'error' : 'success',
            'data' => $data,
            'error' => $error,
        ] ; 
    }
    
    public function generatePin() { 
        $pin = mt_rand(1000, 9999);
        return $pin;
    }

    public function getDateNow(): string
    { 
        return (new \DateTime())->format('Y-m-d H:i:s');
    }
    
    public function insertValidationInscriptionTemp(array $data): array
    {
        try { 
            $sql = "
            INSERT INTO validation_inscription 
                (nom, date_inscription, pin, date_naissance, email, mdp) 
                VALUES 
                (:nom, :date_inscription, :pin, :date_naissance, :email, MD5(:mdp))
            ";
            
            $stmt = $this->connection->prepare($sql);
            $stmt->executeQuery([
                'nom' => $data['nom'],
                'date_inscription' => (new \DateTime($data['date_inscription']))->format('Y-m-d H:i:s'),
                'pin' => $data['pin'],
                'date_naissance' => (new \DateTime($data['date_naissance']))->format('Y-m-d H:i:s'),
                'email' => $data['email'],
                'mdp' => $data['mdp'],
            ]);

            return $this->formatResponse(
                ['message' => 'Insertion réussie'],
                null
            );
        } catch (\Exception $e) {
            return $this->formatResponse(
                null ,
                'Une erreur est survenue : ' . $e->getMessage() 
            );
        } 
    }


    public function insertValidationInscription(array $data): array
    {
        try { 
            $sql = "
            INSERT INTO utilisateur 
                (nom, date_ens, date_naissance, email, mdp, tentative_restant,is_admin) 
            VALUES 
            (:nom, :date_ens, :date_naissance, :email, :mdp , :tentative_restant,0)
            ";
            
            $stmt = $this->connection->prepare($sql);
            $stmt->executeQuery([
                'nom' => $data['nom'],
                'date_ens' => $data['date_inscription'],
                'date_naissance' => $data['date_naissance'],
                'email' => $data['email'],
                'mdp' => $data['mdp'],
                'tentative_restant' => $this->tentative
            ]);

            return $this->formatResponse(
                ['message' => 'Insertion réussie avec validation pin'],
                null
            );
        } catch (\Exception $e) {
            return $this->formatResponse(
                null ,
                'Une erreur est survenue : ' . $e->getMessage() 
            );
        } 
    }

    public function getValidationByPin( int $pin ) : array  
    { 
        $qb = $this->connection->createQueryBuilder();
        $qb->select('*') 
            ->from('validation_inscription') 
            ->where('pin = :pinUser') 
            ->setParameter('pinUser', $pin); 
        $result = $qb->executeQuery()->fetchAssociative();
        if ($result === false) {
            return [];
        }
        return $result;
    }

    public function deleteValidationByPin(int $pin): bool
    {
        $qb = $this->connection->createQueryBuilder();
        $qb->delete('validation_inscription')  
        ->where('pin = :pinUser')          
        ->setParameter('pinUser', $pin); 
        return $qb->executeQuery()->rowCount() > 0;
    }



    public function calculateDurationInSeconds(string $startDate, string $endDate): int
    {
        $startDateTime = new \DateTime($startDate);
        $endDateTime = new \DateTime($endDate);
        return $endDateTime->getTimestamp() - $startDateTime->getTimestamp();
    }


    // public function 
    

    public function checkValidationTime( int $pin , int $timer  ) : array  
    { 
        $validationTemp = $this->getValidationByPin( $pin ) ;
        #check si le pin du lien est correct 
        if ( $validationTemp != null ) { 
            $durationValidity = $this->calculateDurationInSeconds( $validationTemp['date_inscription']  , $this->getDateNow() ) ; 
     
            
            if ( $durationValidity < $timer ) {
                $response = $this->insertValidationInscription( $validationTemp ) ;
                $this->deleteValidationByPin($pin) ;
                $token = $this->authentificationService->generateToken($validationTemp['email']);
                $user = $this->authentificationService->getUserByToken( $token ) ; 
                $response['token'] = $token;
                $response['id_user']= $user['id_utilisateur'] ;
                $response['status'] = 'success'; 
                return $response; 
            } else {
                $this->deleteValidationByPin($pin) ; 
                return $this->formatResponse(
                    null,
                    'Le lien est invalide car la durée de vie du lien a expiré' 
                );
            }
        } else { 
            return $this->formatResponse( 
                null,
                'Le lien est invalide car le pin est invalide'
            );  
        }
    }
    public function updateUser($idUser,$nom,$date_naissance,$mdp)  :void
    {
        $queryBuilder = $this->connection->createQueryBuilder();
        $queryBuilder->update('utilisateur')
            ->set('nom', ':nom')
            ->set('date_naissance', ':date_naissance')
            ->set('mdp', 'MD5(:mdp)')
            ->where('id_utilisateur = :idUser')
            ->setParameter('idUser', $idUser)
            ->setParameter('nom', $nom)
            ->setParameter('mdp', $mdp)
            ->setParameter('date_naissance', $date_naissance)
            ->executeStatement();
    }


}

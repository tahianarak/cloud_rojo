<?php 

namespace App\Service;

use App\Entity\GenerateLetter;
use App\Service\Tentative\UtilisateurTentative;
use Doctrine\DBAL\Connection;
use App\Entity\CodePinGenerator;
use App\Entity\TokenGenerator;
use App\Service\MailService;
use Symfony\Component\Validator\Constraints\DateTime;

class AuthentificationService
{
    private $connection;
    private MailService $mailer;
    private $tempsRestant;
    private $pinExpiration;
    private $tokenExpiration;
    private $tentative;
    private UtilisateurTentative $utilisateurTentative;

    private GenerateLetter $generateLetter;
    public function __construct(Connection $connection, MailService $mailer,UtilisateurTentative $utilisateurTentative)
    {
        $this->connection = $connection;
        $this->mailer = $mailer;
        $this->pinExpiration=90;
        $this->tokenExpiration=$_ENV['SESSION_DURABILITY'];
        $this->tentative=$_ENV['TENTATIVE_LOGIN'];;
        $this->utilisateurTentative = $utilisateurTentative;
        $this->generateLetter = new GenerateLetter();
    }
    public function verifyValidityOfToken(string $token)
    {
        $queryBuilder = $this->connection->createQueryBuilder();
        $result = $queryBuilder
            ->select('id_session', 'token', 'temps_restant', 'id_utilisateur')
            ->from('session_utilisateur') 
            ->where('token = :token')
            ->setParameter('token', $token)
            ->executeQuery()
            ->fetchAssociative();
        if (!$result) {
            throw new \Exception('Token non valide ou inexistant.');
        }
        $dateNow = new \DateTime();
        //dump($dateNow);
        $tempsRestant = new \DateTime($result['temps_restant']);
        $diffInSeconds = $dateNow->getTimestamp() - $tempsRestant->getTimestamp();

        if ($diffInSeconds > $this->tokenExpiration) {
            $this->deleteExpiredToken($result['id_session']);
            $this->updateTentativeRestantByEmail2($result['email'],  $this->tentative);
            throw new \Exception('Token expiré.');
        }
        return 'Token valide';
    }


    private function deleteExpiredToken(int $sessionId)
    {
        $queryBuilder = $this->connection->createQueryBuilder();
        $queryBuilder
            ->delete('session_utilisateur')
            ->where('id_session = :id_session')
            ->setParameter('id_session', $sessionId)
            ->executeQuery();
    }


    public function generateToken($email)
    {
        $tokenGenerator=new TokenGenerator();
        $token=$tokenGenerator->generateRandomToken(80);
        $this->deleteAllPinFor($email);
        $this->updateTentativeRestantTo0($email);
        $this->insertSession($email,$token);
        return $token;
    }
    public function verifyPin(string $email, string $pin)
    {
        $queryBuilder = $this->connection->createQueryBuilder();
        $result = $queryBuilder
            ->select('*')
            ->from('validation_authentification')
            ->innerJoin('validation_authentification', 'utilisateur', 'u', 'validation_authentification.id_utilisateur = u.id_utilisateur')
            ->where('u.email = :email')
            ->andWhere('validation_authentification.pin = :pin')
            ->setParameter('email', $email)
            ->setParameter('pin', $pin)
            ->executeQuery()
            ->fetchAssociative();

        if ($result === false) {
            $this->updateTentativeRestantByEmail($email, 1);
            throw new \Exception('PIN ou email incorrect.');
        }
        $dateAuth = new \DateTime($result['date_auth']);
        $dateNow = new \DateTime();

        $diff = $dateNow->getTimestamp() - $dateAuth->getTimestamp();
        if ($diff > $this->pinExpiration) {
            $this->deleteExpiredPin($result['id_valid_auth'],$pin);
            throw new \Exception('PIN expiré.');
        }
        $tokenGenerator=new TokenGenerator();
        $token=$tokenGenerator->generateRandomToken(80);
        $this->deleteAllPinFor($email);
        $this->updateTentativeRestantTo0($email);
        $this->insertSession($email,$token);
        $tab = array();
        $tab['token']=$token;
        $tab['id_user']=$result["id_utilisateur"];
        return $tab;
    }


    public function insertSession(string $email, string $token)
    {
        $result=$this->getUserByEmail($email);

        $userId = $result['id_utilisateur'];

        $dateAuth=new \DateTime();
        $tempsRestant =$dateAuth->format('Y-m-d H:i:s'); 
    
        $queryBuilder = $this->connection->createQueryBuilder();
        $queryBuilder
            ->insert(' session_utilisateur') 
            ->values([
                'token' => ':token',
                'temps_restant' => ':temps_restant',
                'id_utilisateur' => ':id_utilisateur'
            ])
            ->setParameter('token', $token) 
            ->setParameter('temps_restant', $tempsRestant)
            ->setParameter('id_utilisateur', $userId); 
        $queryBuilder->execute();
    }
    
    public function getUserByEmail($email)
    {
        $queryBuilder = $this->connection->createQueryBuilder();
        $result = $queryBuilder
            ->select('id_utilisateur') // Nous récupérons seulement l'id_utilisateur
            ->from('utilisateur') // Table utilisateur
            ->where('email = :email') // Condition d'email
            ->setParameter('email', $email) // Paramètre de l'email
            ->executeQuery()
            ->fetchAssociative();
    
        if (!$result) {
            throw new \Exception("Utilisateur non trouvé pour l'email : " . $email);
        }
        return $result;
    }

    public function getUserByToken($token)
    {
        $queryBuilder = $this->connection->createQueryBuilder();
        $result = $queryBuilder
            ->select('id_utilisateur') // Nous récupérons seulement l'id_utilisateur
            ->from('session_utilisateur') // Table utilisateur
            ->where('token = :token') // Condition d'email
            ->setParameter('token', $token) // Paramètre de l'email
            ->executeQuery()
            ->fetchAssociative();
    
        return $result;
    }



    public function deleteExpiredPin(int $idValidAuth,$pin)
    {
        $queryBuilder = $this->connection->createQueryBuilder();
        $queryBuilder
            ->delete('validation_authentification')
            ->where("id_valid_auth = :id and pin= :pin")
            ->setParameter('id', $idValidAuth)
            ->setParameter('pin', $pin)
            ->executeQuery();
    }
    public function deleteAllPinFor($email)
    {
        $user= $this->getUserByEmail($email);
        $queryBuilder = $this->connection->createQueryBuilder();
        $queryBuilder
            ->delete('validation_authentification')
            ->where("id_utilisateur= :id_user")
            ->setParameter('id_user', $user['id_utilisateur'])
            ->executeQuery();
    }

    public function verifyLogin(string $email, string $password)
    {
       
        $queryBuilder = $this->connection->createQueryBuilder();

        $result = $queryBuilder
            ->select('*')
            ->from('utilisateur') 
            ->where('email = :email AND mdp = MD5(:mdp)')
            ->setParameter('email', $email) 
            ->setParameter('mdp', $password)
            ->executeQuery() 
            ->fetchAssociative();
        if ($this->utilisateurTentative->getTentative($email)==0)
        {

            $user = $this->utilisateurTentative->getUtilisateurByMail($email);

            //inserer dans la table de unlock
            $random = rand(1000, 9999);
            $this->utilisateurTentative->insertInUnlock($user['id_utilisateur'],$random);
            //send Mail
            $this->mailer->sendEmail($user['email'],"YOUR ACCOUNT IS BLOCKED",$this->generateLetter->getLetterOfUnlock($random,$user['id_utilisateur']));
            throw new \Exception("Account blocked.");
        }
        if ($result === false) {
            $this->updateTentativeRestantByEmail($email, 1); // Mise à jour des tentatives restantes en cas d'échec
            throw new \Exception('Erreur de login');
        }

        // Générer et envoyer un code PIN
        $codePiner = new CodePinGenerator();
        $pin= $codePiner->generateCodePin();
        $this->insertValidAuth($result['id_utilisateur'],$pin);
        $this->mailer->sendEmail(
            $email,
            "Validation d'authentification Rojo Cloud",
            "Voici votre code PIN <h1>" .$pin. "</h1>"
        );
    }

    public function insertValidAuth(int $userId, string $pin)
    {
        $dateAuth = new \DateTime();
        $formattedDate = $dateAuth->format('Y-m-d H:i:s'); 

        $queryBuilder = $this->connection->createQueryBuilder();

        $queryBuilder
            ->insert("validation_authentification")
            ->values([
                'date_auth' => ':date_auth', 
                'pin' => ':pin',
                'id_utilisateur' => ':id_utilisateur' 
            ])
            ->setParameter('date_auth', $formattedDate) 
            ->setParameter('pin', $pin) 
            ->setParameter('id_utilisateur', $userId); 

        $queryBuilder->execute();
    }

    public function updateTentativeRestantByEmail(string $email, int $tentativeToTake)
    {
        // Utilisation de QueryBuilder pour l'UPDATE
        $queryBuilder = $this->connection->createQueryBuilder();

        $queryBuilder
            ->update('utilisateur') // Nom de la table
            ->set('tentative_restant', 'tentative_restant - :tentativeToTake') // Mise à jour de tentative_restant
            ->where('email = :email') // Condition
            ->setParameter('tentativeToTake', $tentativeToTake) // Paramètre
            ->setParameter('email', $email); // Paramètre

        // Exécution de la requête
        $queryBuilder->execute();
    }

    public function updateTentativeRestantByEmail2(string $email, int $tentativeToTake)
    {
        // Utilisation de QueryBuilder pour l'UPDATE
        $queryBuilder = $this->connection->createQueryBuilder();

        $queryBuilder
            ->update('utilisateur') // Nom de la table
            ->set('tentative_restant', ':tentativeToTake') // Mise à jour de tentative_restant
            ->where('email = :email') // Condition
            ->setParameter('tentativeToTake', $tentativeToTake) // Paramètre
            ->setParameter('email', $email); // Paramètre
        
        
            
        // Exécution de la requête
        $queryBuilder->execute();
    }


    public function updateTentativeRestantTo0(string $email)
    {
        // Utilisation de QueryBuilder pour l'UPDATE
        $queryBuilder = $this->connection->createQueryBuilder();

        $queryBuilder
            ->update('utilisateur') // Nom de la table
            ->set('tentative_restant', '0') // Mise à jour de tentative_restant
            ->where('email = :email') // Condition
            ->setParameter('email', $email); // Paramètre

        // Exécution de la requête
        $queryBuilder->execute();
    }

    
}
?>

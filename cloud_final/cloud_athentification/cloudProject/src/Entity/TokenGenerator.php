<?php 

namespace App\Entity;

class TokenGenerator
{
    public function __construct()
    {}
    public function generateRandomToken(int $length = 80): string
    {
        if ($length % 2 !== 0) {
            throw new \Exception('La longueur du token doit être un nombre pair.');
        }
        try {
            $bytes = random_bytes($length / 2); 
            return bin2hex($bytes); 
        } catch (\Exception $e) {
            throw new \Exception("Impossible de générer un token sécurisé : " . $e->getMessage());
        }
    }
}


?>
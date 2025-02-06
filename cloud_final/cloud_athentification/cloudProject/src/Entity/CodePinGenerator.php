<?php
namespace App\Entity;

class CodePinGenerator
{
    public function generateCodePin(): string
    {
        $characters = '0123456789';
        $codePin = '';
        for ($i = 0; $i < 4; $i++) {
            $codePin .= $characters[random_int(0, strlen($characters) - 1)];
        }

        return $codePin;
    }
}

?>

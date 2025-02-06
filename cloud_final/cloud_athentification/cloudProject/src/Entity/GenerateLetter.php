<?php

namespace App\Entity;

class GenerateLetter
{
    public function getCss() : string
    {
        return "body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
            color: #333;
        }
        .container {
            width: 100%;
            max-width: 600px;
            margin: 0 auto;
            background-color: #ffffff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        .header {
            background-color: #4CAF50;
            color: white;
            text-align: center;
            padding: 20px;
            border-radius: 8px;
        }
        .content {
            padding: 20px;
        }
        .footer {
            text-align: center;
            font-size: 12px;
            color: #888;
            padding: 10px;
            background-color: #f1f1f1;
            margin-top: 20px;
            border-radius: 0 0 8px 8px;
        }
        a {
            color: #4CAF50;
            text-decoration: none;
        }";
    }
    public function getLetterOfPin($pin): string
    {
        $toReturn = '<!DOCTYPE html>
            <html lang="fr">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>My Authenticator</title>
            <style>'.
                $this->getCss()
            .'</style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>Lettre Authenticator</h1>
                    </div>
                    <div class="content">
                        <p>Votre PIN est '.$pin.'</p>
                    </div>
                    <div class="footer">
                    <p>Vous recevez cet email car vous êtes inscrit à My Authenticator.</p>
                        <p><a href="#">About us</a></p>
                    </div>
                </div>
            </body>
            </html>';

        return $toReturn;
    }
    public function getLetterOfUnlock($pin, $idUser): string
    {
        $toReturn = '<!DOCTYPE html>
        <html lang="fr">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>My Authenticator</title>
        <style>'.
            $this->getCss()
            .'</style>
        </head>
        <body>
            <div class="container">
                <div class="header">
                    <h1>Lettre Authenticator</h1>
                </div>
                <div class="content">
                    <p><a href="http://localhost:8000/api/unlockAccount/'.$pin.'/'.$idUser.'">Unlock your account </a></p>
                </div>
                <div class="footer">
                    <p>Vous recevez cet email car vous êtes inscrit à My Authenticator.</p>
                    <p><a href="#">About us</a></p>
                </div>
            </div>
        </body>
        </html>';

        return $toReturn;
    }

}
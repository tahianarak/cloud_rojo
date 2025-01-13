<?php

namespace App\Controller\Tentative;

use App\Entity\GenerateLetter;
use App\Entity\Util\AnswerRest;
use App\Service\MailService;
use App\Service\Tentative\UtilisateurTentative;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use OpenApi\Attributes as OA;

class TentativeController extends AbstractController
{
    private UtilisateurTentative $utilisateurTentative;
    private MailService $mailService;
    private AnswerRest $answerRest;

    public function __construct(UtilisateurTentative $utilisateurTentative, MailService $mailService)
    {
        $this->utilisateurTentative = $utilisateurTentative;
        $this->mailService = $mailService;
        $this->answerRest = new AnswerRest();
    }

    #[Route('/api/blockAccount/{idUser}', methods: ['GET'])]
    #[OA\Get(
        path: "/api/blockAccount/{idUser}",
        summary: "Bloque un compte utilisateur",
        description: "Bloque le compte d'un utilisateur et lui envoie un code de déverrouillage par email.",
        tags: ['Tentative']
    )]
    #[OA\Parameter(
        name: "idUser",
        in: "path",
        description: "L'ID de l'utilisateur à bloquer.",
        required: true,
        schema: new OA\Schema(type: "integer")
    )]
    #[OA\Response(
        response: 200,
        description: "Compte bloqué avec succès.",
        content: new OA\JsonContent(
            type: "object",
            properties: [
                new OA\Property(property: "status", type: "string", example: "success"),
                new OA\Property(property: "message", type: "string", example: "Compte bloqué!"),
                new OA\Property(property: "data", type: "null")
            ]
        )
    )]
    #[OA\Response(
        response: 500,
        description: "Erreur serveur interne.",
        content: new OA\JsonContent(
            type: "object",
            properties: [
                new OA\Property(property: "status", type: "string", example: "error"),
                new OA\Property(property: "message", type: "string", example: null),
                new OA\Property(property: "error", type: "string", example: "Message d'erreur.")
            ]
        )
    )]
    public function blockAccount(int $idUser)
    {
        try {
            $generateLetter = new GenerateLetter();
            $user = $this->utilisateurTentative->getUtilisateurById($idUser);

            // Insérer dans la table de déblocage
            $random = rand(1000, 9999);
            $this->utilisateurTentative->insertInUnlock($idUser, $random);

            // Envoi de l'email
            $this->mailService->sendEmail($user['email'], "VOTRE COMPTE EST BLOQUÉ", $generateLetter->getLetterOfUnlock($random, $user['id_utilisateur']));

            return $this->json($this->answerRest->sendResponse('success', "Compte bloqué!", null, 200));
        } catch (\Exception $exception) {
            return $this->json($this->answerRest->sendResponse('error', null, $exception->getMessage(), 500));
        }
    }

    #[Route('/api/unlockAccount/{pin}/{idUser}', methods: ['GET'])]
    #[OA\Get(
        path: "/api/unlockAccount/{pin}/{idUser}",
        summary: "Débloque un compte utilisateur",
        description: "Débloque le compte d'un utilisateur si le code PIN correct est fourni.",
        tags: ['Tentative']
    )]
    #[OA\Parameter(
        name: "pin",
        in: "path",
        description: "Le code PIN de déverrouillage.",
        required: true,
        schema: new OA\Schema(type: "string")
    )]
    #[OA\Parameter(
        name: "idUser",
        in: "path",
        description: "L'ID de l'utilisateur à débloquer.",
        required: true,
        schema: new OA\Schema(type: "integer")
    )]
    #[OA\Response(
        response: 200,
        description: "Compte débloqué avec succès.",
        content: new OA\JsonContent(
            type: "object",
            properties: [
                new OA\Property(property: "status", type: "string", example: "success"),
                new OA\Property(property: "message", type: "string", example: "Compte débloqué!"),
                new OA\Property(property: "data", type: "null")
            ]
        )
    )]
    #[OA\Response(
        response: 500,
        description: "Erreur serveur interne.",
        content: new OA\JsonContent(
            type: "object",
            properties: [
                new OA\Property(property: "status", type: "string", example: "error"),
                new OA\Property(property: "message", type: "string", example: null),
                new OA\Property(property: "error", type: "string", example: "Message d'erreur.")
            ]
        )
    )]
    public function unlockAccount(int $pin, int $idUser)
    {
        try {
            $unlock_validation = $this->utilisateurTentative->get_unlcok_user($idUser, $pin);

            $this->utilisateurTentative->unclockAccount($idUser);
            $this->utilisateurTentative->deleteInUnlock($idUser, $pin);

            return $this->json($this->answerRest->sendResponse('success', "Compte débloqué!", null, 200));
        } catch (\Exception $exception) {
            return $this->json($this->answerRest->sendResponse('error', null, $exception->getMessage(), 500));
        }
    }
}


<?php

namespace App\Controller;

use App\Entity\Util\AnswerRest;
use App\Service\InscriptionService;
use App\Service\MailService;
use Doctrine\DBAL\Connection;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Symfony\Component\Routing\Annotation\Route;
use OpenApi\Attributes as OA; 

class InscriptionController extends AbstractController
{
    private InscriptionService $inscriptionService;
    private MailService $mailService;
    private Connection $connection;
    private AnswerRest $answerRest;
    private $timer = 90;

    public function __construct(InscriptionService $inscriptionService, MailService $mailService, Connection $connection)
    {
        $this->inscriptionService = $inscriptionService;
        $this->mailService = $mailService;
        $this->connection = $connection;
        $this->answerRest = new AnswerRest();
    }

    #[Route('/api/valideInscription', methods: ['POST'])]
    #[OA\Post(
        path: "/api/valideInscription",
        summary: "Valider l'inscription",
        description: "Crée une validation temporaire pour l'inscription de l'utilisateur et envoie un email avec le lien de validation",
        tags: ['Inscription']
    )]
    #[OA\RequestBody(
        required: true,
        content: new OA\JsonContent(
            type: "object",
            properties: [
                new OA\Property(property: "email", type: "string", description: "Email de l'utilisateur"),
                new OA\Property(property: "nom", type: "string", description: "Nom de l'utilisateur"),
                new OA\Property(property: "date_naissance", type: "string", description: "Date de naissance de l'utilisateur"),
                new OA\Property(property: "mdp", type: "string", description: "Mot de passe de l'utilisateur")
            ]
        )
    )]
    #[OA\Response(
        response: 200,
        description: "Email de validation d'inscription envoyé"
    )]
    #[OA\Response(
        response: 400,
        description: "Paramètres manquants"
    )]
    public function createTempValidation(Request $request, SessionInterface $session): Response
    {
        $data = json_decode($request->getContent(), true);
        $pin = $this->inscriptionService->generatePin();
        $email = $data['email'];

        $response = $this->inscriptionService->insertValidationInscriptionTemp([
            'nom' => $data['nom'],
            'date_inscription' => $this->inscriptionService->getDateNow(),
            'pin' => $pin,
            'date_naissance' => $data['date_naissance'],
            'email' => $email,
            'mdp' => $data['mdp'], 
        ]);
        if ($response['status'] == 'success') {
            $href = 'http://127.0.0.1:8080/api/inscriptionEmail?pin=' . $pin;
            if($data['app']!=null)
            {
                $href=$data['app'].'?pin='. $pin;
            }
            $htmlContent = $this->renderView('validation.html.twig', [
                'hrefValidation' => $href,
            ]);
            $this->mailService->sendEmail(
                $email,
                'Validation Inscription',
                $htmlContent
            );
        }
        return new JsonResponse($response);
    }


    #[Route('/api/inscriptionEmail', methods: ['GET'])]
    #[OA\Get(
        path: "/api/inscriptionEmail",
        summary: "Valider le PIN d'inscription",
        description: "Vérifie si le PIN de validation est toujours valide",
        tags: ['Inscription']
    )]
    #[OA\Parameter(
        name: "pin",
        in: "query",
        description: "PIN de validation",
        required: true,
        schema: new OA\Schema(type: "string")
    )]
    #[OA\Response(
        response: 200,
        description: "Résultat de la validation du PIN"
    )]
    public function validationInscription(Request $request, SessionInterface $session): Response
    {
        $pin = $request->query->get('pin');
        $response = $this->inscriptionService->checkValidationTime($pin, $this->timer);
        return new JsonResponse($response);
    }
    #[Route('/api/updateUser', methods: ['POST'])]
    #[OA\Post(
        path: "/api/updateUser",
        summary: "Mettre à jour les informations de l'utilisateur",
        description: "Met à jour les informations de l'utilisateur telles que le nom, la date de naissance et le mot de passe",
        tags: ['Inscription']
    )]
    #[OA\RequestBody(
        required: true,
        content: new OA\JsonContent(
            type: "object",
            properties: [
                new OA\Property(property: "idUser", type: "integer", description: "ID de l'utilisateur"),
                new OA\Property(property: "nom", type: "string", description: "Nom de l'utilisateur"),
                new OA\Property(property: "date_naissance", type: "string", description: "Date de naissance de l'utilisateur"),
                new OA\Property(property: "mdp", type: "string", description: "Mot de passe de l'utilisateur")
            ]
        )
    )]
    #[OA\Response(
        response: 200,
        description: "Utilisateur mis à jour avec succès"
    )]
    #[OA\Response(
        response: 500,
        description: "Erreur interne du serveur"
    )]
    public function updateUser(Request $request)
    {
        try {
            $data = json_decode($request->getContent(), true);
            $idUser = $data['idUser'];
            $nom = $data['nom'];
            $date_naissance = $data['date_naissance'];
            $mdp = $data['mdp'];

            $this->inscriptionService->updateUser($idUser, $nom, $date_naissance, $mdp);
            return $this->json($this->answerRest->sendResponse('success', 'Mise à jour réussie', null, 200));
        } catch (\Exception $e) {
            return $this->json($this->answerRest->sendResponse('error', null, $e->getMessage(), 500));
        }
    }
}

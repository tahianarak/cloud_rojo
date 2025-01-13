<?php

namespace App\Controller;

use App\Service\AuthentificationService;
use App\Entity\RestAnswer;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\Security\Core\Exception\BadCredentialsException;
use OpenApi\Attributes as OA;

class AuthentificationApi extends AbstractController
{
    private AuthentificationService $authService;
    private RestAnswer $rest;

    public function __construct(AuthentificationService $AuthService)
    {
        $this->authService = $AuthService;
        $this->rest = new RestAnswer();
    }

    #[OA\Post(
        path: "/api/verify/token",
        summary: "Vérifier la validité du token",
        description: "Vérifie si un token JWT est valide.",
        tags: ['Authentification']
    )]
    #[OA\RequestBody(
        required: true,
        content: new OA\JsonContent(
            type: "object",
            properties: [
                new OA\Property(property: "token", type: "string", description: "Token JWT à vérifier")
            ]
        )
    )]
    #[OA\Response(
        response: 200,
        description: "Token valide.",
        content: new OA\JsonContent(
            type: "object",
            properties: [
                new OA\Property(property: "status", type: "string", example: "success"),
                new OA\Property(property: "data", type: "string", example: "Token vérifié avec succès.")
            ]
        )
    )]
    #[OA\Response(
        response: 400,
        description: "Token requis.",
        content: new OA\JsonContent(
            type: "object",
            properties: [
                new OA\Property(property: "error", type: "string", example: "token requis")
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
                new OA\Property(property: "message", type: "string", example: "Message d'erreur.")
            ]
        )
    )]
    #[Route('/api/verify/token', methods: ['POST'])]
    public function verifyToken(Request $request)
    {
        $data = json_decode($request->getContent(), true);

        if (empty($data['token'])) {
            return new JsonResponse([
                'error' => 'token requis'
            ], JsonResponse::HTTP_BAD_REQUEST);
        }
        $token = $data['token'];
        try {
            $data = $this->authService->verifyValidityOfToken($token);
            return $this->json($this->rest->sendResponse('success', $data, null, 200));
        } catch (\Exception $e) {
            return $this->json($this->rest->sendResponse('error', null, $e->getMessage(), 500));
        }
    }

    #[Route('/api/verify/pin', methods: ['POST'])]
    #[OA\Post(
        path: "/api/verify/pin",
        summary: "Vérifier l'authentification par PIN",
        description: "Vérifie un PIN pour l'authentification utilisateur.",
        tags: ['Authentification']
    )]
    #[OA\RequestBody(
        required: true,
        content: new OA\JsonContent(
            type: "object",
            properties: [
                new OA\Property(property: "email", type: "string", description: "Email de l'utilisateur"),
                new OA\Property(property: "pin", type: "string", description: "PIN de l'utilisateur")
            ]
        )
    )]
    #[OA\Response(
        response: 200,
        description: "PIN valide.",
        content: new OA\JsonContent(
            type: "object",
            properties: [
                new OA\Property(property: "status", type: "string", example: "success"),
                new OA\Property(property: "token", type: "string", example: "nouveau_token")
            ]
        )
    )]
    #[OA\Response(
        response: 400,
        description: "Email et PIN requis.",
        content: new OA\JsonContent(
            type: "object",
            properties: [
                new OA\Property(property: "error", type: "string", example: "Email et pin requis.")
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
                new OA\Property(property: "message", type: "string", example: "Message d'erreur.")
            ]
        )
    )]
    public function verifySecond(Request $request)
    {
        $data = json_decode($request->getContent(), true);
    
        if (empty($data['email']) || empty($data['pin'])) {
            return new JsonResponse([
                'error' => 'Email et pin requis.'
            ], JsonResponse::HTTP_BAD_REQUEST);
        }
        $email = $data['email'];
        $pin = $data['pin'];
        try {
            $data = $this->authService->verifyPin($email, $pin);
            return new JsonResponse([
                'status' => 'success',
                'token' => $data['token'],
                'id_user'=>$data['id_user'],
                'error' => null
            ]);
        } catch (\Exception $e) {
            return $this->json($this->rest->sendResponse('error', null, $e->getMessage(), 500));
        }
    }
    

    #[Route('/api/verify/login', methods: ['POST'])]
#[OA\Post(
    path: "/api/verify/login",
    summary: "Vérifier l'authentification par email et mot de passe",
    description: "Vérifie l'authentification d'un utilisateur avec email et mot de passe.",
    tags: ['Authentification']
)]
#[OA\RequestBody(
    required: true,
    content: new OA\JsonContent(
        type: "object",
        properties: [
            new OA\Property(property: "email", type: "string", description: "Email de l'utilisateur"),
            new OA\Property(property: "mdp", type: "string", description: "Mot de passe de l'utilisateur")
        ]
    )
)]
#[OA\Response(
    response: 200,
    description: "Authentification réussie.",
    content: new OA\JsonContent(
        type: "object",
        properties: [
            new OA\Property(property: "status", type: "string", example: "success"),
            new OA\Property(property: "message", type: "string", example: "Opération réussie.")
        ]
    )
)]
#[OA\Response(
    response: 400,
    description: "Email et mot de passe requis.",
    content: new OA\JsonContent(
        type: "object",
        properties: [
            new OA\Property(property: "error", type: "string", example: "Email et mot de passe requis.")
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
            new OA\Property(property: "message", type: "string", example: "Message d'erreur.")
        ]
    )
)]
public function verify(Request $request)
{
    $data = json_decode($request->getContent(), true);

    if (empty($data['email']) || empty($data['mdp'])) {
        return new JsonResponse([
            'error' => 'Email et mot de passe requis.'
        ], JsonResponse::HTTP_BAD_REQUEST);
    }
    $email = $data['email'];
    $mdp = $data['mdp'];
    try {
        $this->authService->verifyLogin($email, $mdp);
        return $this->json($this->rest->sendResponse('success', 'Opération réussie', null, 200));
    } catch (\Exception $e) {
        return $this->json($this->rest->sendResponse('error', null, $e->getMessage(), 500));
    }
}
}
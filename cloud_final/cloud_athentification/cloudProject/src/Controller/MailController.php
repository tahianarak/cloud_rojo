<?php

namespace App\Controller;

use App\Entity\GenerateLetter;
use App\Service\MailService;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use OpenApi\Annotations as OA;

class MailController extends AbstractController
{
    private MailService $mailService;

    public function __construct(MailService $mailService)
    {
        $this->mailService = $mailService;
    }

    /**
     * @OA\Get(
     *     path="/sendEmail",
     *     summary="Envoyer un e-mail",
     *     description="Envoie un e-mail avec le contenu d'une lettre générée",
     *     @OA\Response(
     *         response=200,
     *         description="E-mail envoyé avec succès"
     *     ),
     *     @OA\Response(
     *         response=500,
     *         description="Erreur interne du serveur"
     *     )
     * )
     */
    #[Route('/sendEmail', methods: ['GET'])]
    public function sendEmail(): Response
    {
        $generateLetter = new GenerateLetter();
        $this->mailService->sendEmail(
            'RandriaVals2303@gmail.com',
            'PIN 2563',
            $generateLetter->getLetterOfUnlock()
        );

        return new Response('E-mail envoyé avec succès !');
    }
}

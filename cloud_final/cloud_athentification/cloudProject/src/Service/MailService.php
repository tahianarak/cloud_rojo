<?php

namespace App\Service;

use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\Mime\Email;

class MailService
{
    private MailerInterface $mailer;

    public function __construct(MailerInterface $mailer)
    {
        $this->mailer = $mailer;
    }

    public function sendEmail(string $to, string $subject, string $content): void
    {
        $email = (new Email())
            ->from('authentificator@gmail.com') // L'adresse de l'expéditeur
            ->to($to) // Destinataire
            ->subject($subject) // Objet du mail
            ->text($content) // Contenu en texte brut
            ->html("<p>$content</p>"); // Contenu en HTML

        $this->mailer->send($email);
    }
}

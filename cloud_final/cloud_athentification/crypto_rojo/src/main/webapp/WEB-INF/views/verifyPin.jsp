<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Vérification du PIN</title>
</head>
<body>
    <h2>Vérification du PIN</h2>
    <form action="verifyPin" method="POST">
        <label for="pin">Entrez votre PIN :</label>
        <input type="text" id="pin" name="pin" required /><br><br>
        <button type="submit">Valider</button>
    </form>
</body>
</html>

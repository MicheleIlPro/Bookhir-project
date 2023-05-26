<!DOCTYPE html>
<html lang="it">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="LoadingStyle.css">
    <link rel="stylesheet" href="../IndexPage/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.0.2/css/bootstrap.min.css">

    <title>Loading</title>
</head>

<body>
    <?php
    include '../connection.php';
    $emailLogin = $_POST['email'];
    $passwordLogin = $_POST['password'];
    $emailLogin = filter_var($emailLogin, FILTER_SANITIZE_STRING);
    $passwordLogin = filter_var($passwordLogin, FILTER_SANITIZE_STRING);


    // Esegui la query SQL
    $sql = "SELECT * FROM utenti WHERE email = '{$emailLogin}' AND password = '{$passwordLogin}'";

    $result = mysqli_query($conn, $sql);

    // Verifica se sono stati trovati risultati
    if (mysqli_num_rows($result) > 0) {

        $exist = true;

    } else {
        $exist = false;
    }

    // Chiudi la connessione al database
    mysqli_close($conn);
    ?>
    <?php if ($exist): ?>
        <div class="box">
            <div class="container">
                <div id="load">
                    <div>I</div>
                    <div>D</div>
                    <div>N</div>
                    <div>E</div>
                    <div>T</div>
                    <div>T</div>
                    <div>A</div>
                </div>
            </div>
        </div>
    <?php else: ?>
        <script src="../seconPage/script.js"></script>
        <div class="container">
            <div class="row">
                <div class="col-md-6 offset-md-3 mt-5">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">Non Sei registrato.</h5>
                            <p class="card-text">A breve sarai Reindirizzato alla pagina del login. Ci Scusiamo per l'attesa</p>
                            <p class="card-text">A presto!</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    <?php endif; ?>

    <?php
    if ($exist) {
        $redirectUrl = "../index.php?email={$emailLogin}";
    } else {
        $redirectUrl = "../login.html";
    }

    header("refresh: 5; url={$redirectUrl}");
    exit;
    ?>


    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.0.2/js/bootstrap.bundle.min.js"></script>
</body>

</html>
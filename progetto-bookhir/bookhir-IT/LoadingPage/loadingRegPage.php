<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="LoadingStyle.css">
    <title>Loading</title>
</head>

<body>
    <?php

    include '../connection.php';
    $emailReg = $_POST['emailReg'];
    $passwordReg = $_POST['passwordReg'];
    $nameReg = $_POST['nameReg'];
    $cognomeReg = $_POST['cognomeReg'];

    $emailReg = filter_var($emailReg, FILTER_SANITIZE_STRING);
    $passwordReg = filter_var($passwordReg, FILTER_SANITIZE_STRING);
    $nameReg = filter_var($nameReg, FILTER_SANITIZE_STRING);
    $cognomeReg = filter_var($cognomeReg, FILTER_SANITIZE_STRING);

    $sqlCheck = "SELECT COUNT(*) as count FROM utenti WHERE email = '{$emailReg}'";
    $resultCheck = mysqli_query($conn, $sqlCheck);
    $rowCheck = mysqli_fetch_assoc($resultCheck);
    $count = $rowCheck['count'];

    if ($count > 0) {
        // L'email esiste già nel database 
        $exist = false;
        $emailno = true;
    } else {
        // L'email non esiste nel database, quindi puoi inserirla
        $sql = "INSERT INTO utenti (email, nome, cognome, password, admin) VALUES ('{$emailReg}', '{$nameReg}', '{$cognomeReg}', '{$passwordReg}', false)";
        $result = mysqli_query($conn, $sql);

        // Verifica se sono stati trovati risultati
        if ($result === true) {
            // Itera sui risultati della query
    
            $exist = true;
            // echo "Benvenuto Fratello {$row["nome"]}";
    
        } else {
            $exist = false;
        }

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
        <?php

        header("refresh: 5; url=../index.php?email={$emailReg}"); // the redirect goes here
        exit;
        ?>
    <?php else: ?>

        <p>Hai sbagliato credenziali</p>
        <?php

        header("refresh: 5; url=../login.html"); // the redirect goes here
        exit;
        ?>
    <?php endif ?>

    <?php if ($emalino): ?>
        <p>L'email che hai inserito esiste gia'</p>
        <?php

        header("refresh: 5; url=../login.html"); // the redirect goes here
        exit;
        ?>
    <?php endif ?>

</body>

</html>
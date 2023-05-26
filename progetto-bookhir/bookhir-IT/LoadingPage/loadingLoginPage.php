
<!DOCTYPE html>
<html lang="en">
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
       
            $exist=true;
        
    } else {
        $exist=false;
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
   
    header("refresh: 5; url=../index.php?email={$emailLogin}"); // the redirect goes here
    exit;
    ?> 
<?php else: ?>
    <script src="../seconPage/script.js"></script>
    <div class="container">
        <div class="row">
            <div class="col-md-6 offset-md-3 mt-5">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Thank You</h5>
                        <p class="card-text">Thank you for visiting our library website. We hope you had a great experience.</p>
                        <p class="card-text">See you again!</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
       
    <?php
   
        header("refresh: 5; url=../login.html"); // the redirect goes here
        exit;
   ?>
<?php endif ?>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.0.2/js/bootstrap.bundle.min.js"></script>
</body>
</html>

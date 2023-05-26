<?php
include '../connection.php';
$email = $_GET['email'] ?? "";
$sql2 = "SELECT nome, cognome FROM utenti WHERE email LIKE '{$email}';";
$result2 = mysqli_query($conn, $sql2);
$sql3 = "SELECT id FROM tessere WHERE utenteMail LIKE '{$email}';";
$result3 = mysqli_query($conn, $sql3);

$sql1 = "SELECT * FROM tessere WHERE utenteMail LIKE '{$email}';";
$result1 = mysqli_query($conn, $sql1);

$sql4 = "SELECT titoloLibro, data_noleggio, data_scadenza FROM noleggi WHERE emailUtente = '$email'; ";
$result4 = mysqli_query($conn, $sql4);


error_reporting(E_ALL);
ini_set('display_errors', 1);

?>

<!DOCTYPE html>
<html>

<head>
    <style>
        /* Adjust the footer style as needed */
        .footer {
            background-color: #f8f9fa;
            padding: 20px;
            text-align: center;
            margin-top: 50px;

        }
    </style>
    <title>User Page</title>
    <link rel="stylesheet" href="../IndexPage/style.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
        integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>

<body>
    <nav class="navbar navbar-expand-lg navbar-light bg-light container-fluid">
        <img class="logo d-inline-block align-top" src="../assets/logo_libro.png" alt="Logo">
        <div class="container">
            <a class="navbar-brand" href="index.php">Bookhir</a>
        </div>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
            aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
            <ul class="navbar-nav">
                <div class="centered d-flex flex-row align-items-center">

                    <?php if (mysqli_num_rows($result2) > 0): ?>
                        <li class="nav-item p-1">
                            <a class="nav-link">
                                Welcome
                                <?php
                                // Iterate through the results and output the data
                                $user = mysqli_fetch_assoc($result2);
                                echo $user["nome"];
                                ?>
                            </a>
                        </li>
                    <?php else: ?>
                        <li class="nav-item p-1">
                            <a class="nav-link">Not Logged</a>
                        </li>
                    <?php endif ?>
                </div>
                <div class="d-flex flex-row align-items-center">
                    <?php if (mysqli_num_rows($result2) > 0): ?>
                        <li class="nav-item p-1">
                            <?php echo "<a class='nav-link' href='/progetto-bookhir/bookhir-IT/secondPage/rent.php?email={$email}''>Rent</a>" ?>
                        </li>
                        <li class="nav-item p-1">
                            <a class="nav-link" href="/progetto-bookhir/bookhir-IT/secondPage/user.php">User</a>
                        </li>
                        <li class="nav-item ">
                            <a class="nav-link" href="">About</a>
                        </li>
                        <li class="nav-item p-1">
                            <a class="nav-link" href="../secondPage/logout.html">Logout</a>
                        </li>

                    <?php else: ?>
                        <li class="nav-item ">
                            <a class="nav-link" href="../secondPage/about.html">About</a>
                        </li>
                        <li class="nav-item p-1">
                            <a class="nav-link" href="../login.html">Login</a>
                        </li>
                </ul>
            <?php endif ?>
    </nav>
    <?php if (mysqli_num_rows($result2) > 0): ?>
        <?php if (!(mysqli_num_rows($result3) > 0)): ?>
            <div class="container ">
                <h5 class="mt-3">Please create your card membership to use our website services</h5>
                <h2 class="mt-4 mb-3">Card registration:</h2>
                <div class="custom-box">
                    <div class="d-flex flex-row align-items-center container ">
                        <h5 class="mr-3">Click for</h5>
                        <button type="submit" class="btn btn-primary">
                            <?php echo "<a href='create.php?email={$email}' class='btn btn-primary'>" ?>Create a card</a>
                        </button>

                    </div>
                </div>
            <?php endif ?>
            <?php if (mysqli_num_rows($result3) > 0): ?>
                <h2 class="mt-4">Card Details:</h2>
                <table class="table table-bordered mt-4">
                    <thead>
                        <tr>
                            <th>Card Number</th>
                            <th>Expiration Date</th>
                            <th>Creation Date</th>
                        </tr>
                    </thead>
                    <tbody>
                        <?php
                        echo "<tr>";
                        while ($row = mysqli_fetch_assoc($result1)) {
                            echo "<td> {$row["id"]} </td>";
                            echo "<td> {$row["data_emissione"]}</td>";
                            echo "<td> {$row["data_scadenza"]}</td>";
                        }
                        echo "</tr>";
                        ?>
                    </tbody>
                </table>

                <h2 class="mt-4">Rented Books:</h2>
                <table class="table table-bordered mt-4">
                    <thead>
                        <tr>
                            <th>Title</th>
                            <th>Expiring Data </th>
                        </tr>
                    </thead>
                    <tbody>
                        <?php while ($row1 = mysqli_fetch_assoc($result4)): ?>
                            <tr>
                                <td>
                                    <?php echo $row1["titoloLibro"]; ?>
                                </td>
                                <td>
                                    <?php echo $row1["data_scadenza"]; ?>
                                </td>
                            </tr>
                        <?php endwhile; ?>
                    </tbody>
                </table>
            </div>

        <?php endif ?>
    <?php endif ?>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <footer class="footer">
        <div class="container">
            <span class="text-muted"> © 2021 Copyright Bookhir</span>
        </div>
    </footer>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</body>

</html>
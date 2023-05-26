<?php
$userName = "Jebreel";
$cardNumber = "1234 5678 9012 3456";
$expirationDate = "05/2025";
$creationDate = "01/2023";

// Rented books
$rentedBooks = array(
    array("title" => "Book 1", "author" => "Author 1"),
    array("title" => "Book 2", "author" => "Author 2"),
    array("title" => "Book 3", "author" => "Author 3")
);
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
    <link rel="stylesheet" href="style.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
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
            <li class="nav-item p-1">
                <a class="nav-link" href="">Welcome <?php echo $userName; ?>!</a>
              </li>
              <li class="nav-item p-1">
                <a class="nav-link" href="">Not Loggged</a>
              </li>
              </div>
              <div class="d-flex flex-row align-items-center">
              <li class="nav-item p-1">
                <a class="nav-link" href="">Rent</a>
              </li>
              <li class="nav-item p-1">
                <a class="nav-link" href="user.php">User</a>
              </li>
              <li class="nav-item ">
                <a class="nav-link" href="">About</a>
              </li>
              <li class="nav-item p-1">
                <a class="nav-link" href="">Login</a>
              </li>
              <li class="nav-item p-1">
                <a class="nav-link" href="">Logout</a>
              </li>
              <div>
          </div>
      </nav>
    <div class="container">
        <h5 class="mt-3">Please create your card membership to use our website services</h5>
        <h2 class="mt-4 mb-3">Card registration:</h2>
        <div class="custom-box">
    <div class="d-flex flex-row align-items-center container ">
    <h5 class="mr-3">Click for</h5>
    <button type="submit" class="btn btn-primary">Create a card</button>
    </div>
    </div>
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
                <tr>
                    <td><?php echo $cardNumber; ?></td>
                    <td><?php echo $expirationDate; ?></td>
                    <td><?php echo $creationDate; ?></td>
                </tr>
            </tbody>
        </table>

        <h2 class="mt-4">Rented Books:</h2>
        <table class="table table-bordered mt-4">
            <thead>
                <tr>
                    <th>Title</th>
                    <th>Author</th>
                </tr>
            </thead>
            <tbody>
                <?php foreach ($rentedBooks as $book): ?>
                    <tr>
                        <td><?php echo $book['title']; ?></td>
                        <td><?php echo $book['author']; ?></td>
                    </tr>
                <?php endforeach; ?>
            </tbody>
        </table>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <footer class="footer">
        <div class="container">
            <span class="text-muted"> © 2021 Copyright  Bookhir</span>
        </div>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</body>
</html>

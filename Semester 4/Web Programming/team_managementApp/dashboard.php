
<?php
require 'conectare.php';
session_start();
if(!isset($_SESSION['name']))
    return header("location:index.php");

?>

<!DOCTYPE html>
<html>
<head>
    <?php include 'layouts/includes.php'; ?>
</head>
<body>
<div class="container text-center">
    <h1>Hi, <?php echo $_SESSION['name'];?>!</h1>
    <div class="row">
        <a href="../exam-players/check-teams.php" type="button" class="btn btn-primary mb-2">Check All Teams</a>
        <a href="../exam-players/check-my-teams.php" type="button" class="btn btn-warning mb-2">Check My Teams</a>
        <a href="../exam-players/add-players.php" type="button" class="btn btn-success mb-2">Add players to teams</a>
        <a href="../exam-players/backend/logout.php" type="button" class="btn btn-danger mb-2">Log out</a>
    </div>
</div>
</body>
</html>

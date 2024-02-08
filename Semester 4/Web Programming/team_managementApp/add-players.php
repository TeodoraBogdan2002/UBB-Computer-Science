<?php
require 'conectare.php';

?>

<!DOCTYPE html>
<html>
<head>
    <?php include 'layouts/includes.php'; ?>
    <script type="text/javascript" src="resources/script.js"></script>

</head>
<body>
<div class="container" style="margin-top: 100px;">

    <div class="row">
    <form method="POST" action="backend/add-players.php">
        <div class="col-6">
            <div id="initial">
            </div>
            <div class="form-inputs mb-5">
                <input type="text" class="col-6" name="team" placeholder="Enter Team"><input type="text" class="col-6" name="player" placeholder="Enter Player">
            </div>
            <button type="button" onclick="duplicateInputs()" class="btn btn-primary">Add more</button>
            <button type="submit" class="btn btn-success">Submit</button>
        </div>
        </form>
    </div>
    <a href="/" type="button" class="btn btn-danger mt-2">Back</a>
</div>
</body>
</html>

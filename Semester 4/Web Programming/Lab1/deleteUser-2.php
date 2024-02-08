<!DOCTYPE html>
<html>
    <head>
        <title>User Management DU</title>
        <link rel="stylesheet" href="style.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    </head>

    <body>
        <nav>
            <a href="browseUser.php">Browse User</a>
            <a href="addUser.php">Add User</a>
            <a href="updateUser.php">Update User</a>
            <a href="#">Delete User</a>
        </nav>

        <div id="bu">
            <form method = "post" action="<?php echo $_SERVER['PHP_SELF'];?>">
                <label for="idDel">User to delete: </label>
                <input type="text" name="idDel">    

                <input type="button" value="Delete">
            </form>
        </div>

        <?php

            $con = new mysqli("localhost", "root", "", "usermanagement");

            if (!$con){
                die('Could not connect: ' . mysqli_error());
            }

            if ($_SERVER["REQUEST_METHOD"] == "POST"){
                $nameToDelete = mysql_real_escape_string($_POST["idDel"]);

                if ($nameToDelete){
                    $sql = "DELETE FROM user WHERE (username = '$nameToDelete' AND password = '$passToDelete')";
                    $con -> query($sql);
                }
    
                mysqli_close($con);
            } 
        ?>

    </body>
</html>
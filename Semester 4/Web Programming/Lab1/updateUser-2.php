<!DOCTYPE html>
<html>
    <head>
        <title>User Management UU</title>
        <link rel="stylesheet" href="style.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    </head>

    <body>
        <nav>
            <a href="browseUser.php">Browse User</a>
            <a href="addUser.php">Add User</a>
            <a href="#">Update User</a>
            <a href="deleteUser.php">Delete User</a>
        </nav>

        <form class="form" id="update-car" method="post" action="<?php echo $_SERVER['PHP_SELF'];?>">
        
            <label for="username">Username: </label>
            <input name="username" type="text">
            
            <br>
            <br>

            <label for="password">Password: </label>
            <input password="password" type="text">

            <br>
            <br>

            <label for="full-name">Full name: </label>
            <input name="full-name" type="text">

            <br>
            <br>

            <label for="age">Age: </label>
            <input name="age" type="number">

            <br>
            <br>

            <label for="role">Role: </label>
            <input name="role" type="text">
            
            <br>
            <br>

            <label for="email">Email: </label>
            <input name="email" type="text">
    
            <br><br><br><br>

            <input type="button" value="Update user">
        </form>

        <?php

            $con = new mysqli("localhost", "root", "", "usermanagement");

            if (!$con){
                die('Could not connect: ' . mysqli_error());
            }

            if ($_SERVER["REQUEST_METHOD"] == "POST"){
                $username = mysql_real_escape_string($_POST["username"]);
                $password = mysql_real_escape_string($_POST["password"]);
                $fullname = mysql_real_escape_string($_POST["full-name"]);
                $age = mysql_real_escape_string($_POST["age"]);
                $role = mysql_real_escape_string($_POST["role"]);
                $email = mysql_real_escape_string($_POST["email"]);

                $sql = "UPDATE user SET username = '$username', password ='$password', full-name='$fullname'
                , age = '$age', role = '$role', email = '$email'";

                $con -> query(sql);
            }

            mysqli_close($con);
        ?>
    </body>
</html>
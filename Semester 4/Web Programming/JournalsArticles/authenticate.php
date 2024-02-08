<?php 

require_once('connection.php');
session_start();

    if(isset($_POST['Login']))
    {
       if(empty($_POST['Username']))
       {
            header("location:index.php?Empty= Please fill in the username. ");
       }
       else
       {
            $query = "SELECT * FROM Articles WHERE user = '" . $_POST['Username'] . "'";
            $result=mysqli_query($con,$query);
 
            if(mysqli_fetch_assoc($result))
            {
                $_SESSION['User']=$_POST['Username'];
                header("location:main.php");
            }
            else
            {
                header("location:index.php?Invalid= Your username or password are incorrect. ");
            }
       }
    }

    else
    {
        echo 'Not working';
    }
 
?>
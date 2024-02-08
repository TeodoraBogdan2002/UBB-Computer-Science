<?php
session_start();
$errors = 0;
$fields = array("username");
if (isset($_POST['loginButton'])) {
    foreach ($fields as $key => $field) {
        if (!isset($_POST[$field]) && empty($_POST[$field])) {
            echo "please enter username";
            $errors++;
        }
    }
}
echo $_POST['username'];
if ($errors <= 0) {
//    $password = $_POST['password'];
    $_SESSION['username'] = $_POST['username'];
//    if ($controller->checkValidPassword($username, $password)) {
//        $_SESSION['username'] = $username;
    header("Location: userPage.php");
//    }else {
//        header("Location: index.html");
//        echo '<p>Invalid username and password</p>';
//    }
}
?>
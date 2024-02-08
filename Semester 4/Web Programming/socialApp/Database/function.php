<?php
/**
 * Created by PhpStorm.
 * User: potra
 * Date: 26.06.2018
 * Time: 21:52
 * @param $ID
 * @param $Password
 * @return bool
 */

include("config.php");

function userOk($ID, $Password) {
    $sql = "SELECT ID FROM person WHERE ID = '$ID' and SecretNumber = '$Password'";
    $result = mysqli_query($GLOBALS['DB_CONNECTION'], $sql);
    $count = mysqli_num_rows($result);
    if($count == 1) {
        return true;
    }else {
        return false;
    }
}

function get1stLevelFriends($ID) {
    $sql = "CALL 1stLevelFriends('$ID')";
    $result = mysqli_query($GLOBALS['DB_CONNECTION'], $sql);

    $response = array();
    while ($row = $result->fetch_assoc()) {
        array_push($response, (array($row['ID'])));
    }
    return $response;
}

function get2ndLevelFriends($ID) {
    $sql = "CALL 2ndLevelFriends('$ID')";
    $result = mysqli_query($GLOBALS['DB_CONNECTION'], $sql);

    $response = array();
    while ($row = $result->fetch_assoc()) {
        array_push($response, (array($row['ID'])));
    }
    return $response;
}

function makeFriend($ID1, $ID2) {
    $sql = "INSERT INTO friend (ID1, ID2) VALUES ('$ID1', '$ID2')";
    mysqli_query($GLOBALS['DB_CONNECTION'], $sql);
}


function removeFriend($ID1, $ID2) {
    $sql = "DELETE FROM friend WHERE (ID1 = '$ID1' and ID2 = '$ID2') OR (ID1 = '$ID2' and ID2 = '$ID1')";
    mysqli_query($GLOBALS['DB_CONNECTION'], $sql);
}
<?php
session_start();
if (!isset($_SESSION['username'])) {
    header('Location: index.html');
}
$txt = sprintf("The user is: %s", $_SESSION['username']);
echo $txt;
?>
<html>
<head>
    <title>Ex06_2020</title>
    <link rel="stylesheet" href="style.css">
    <script src="jquery-2.0.3.js"></script>
    <script src="userScript.js" type="text/javascript"></script>
</head>
<body>
<table class="table">
    <thead>
    <tr>
        <th>ID</th>
        <th>OwnerId</th>
        <th>Name</th>
        <th>Description</th>
        <th>Subscribers</th>
    </tr>
    </thead>
    <tbody></tbody>
</table>

<div class="field">
    <input type="text" id="ownerName">
    <button id="getOwnerNameButton">Find</button>
</div>

<div class="field">
    <input type="text" id="channelId">
    <button id="subscribe">SUBSCRIBE La canalul meu de youtube</button>
</div>
<table class="table">
    <thead>
    <tr>
        <th>Name</th>
        <th>Description</th>
    </tr>
    </thead>
    <tbody></tbody>
</table>
</body>
</html>

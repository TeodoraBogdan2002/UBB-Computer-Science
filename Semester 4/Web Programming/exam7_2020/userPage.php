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
        <th>URL</th>
        <th>Number of documents</th>
    </tr>
    </thead>
    <tbody></tbody>
</table>

<input type="number" id="documentId">
<input type="text" id="k1">
<input type="text" id="k2">
<input type="text" id="k3">
<input type="text" id="k4">
<input type="text" id="k5">

<button id="setKey">Update</button>

<input type="text" id="keywords">
<button id="getWithKey">Find</button>

<table class="table">
    <thead>
    <tr>
        <th>ID</th>
        <th>WebsiteId</th>
        <th>Name</th>
        <th>k1</th>
        <th>k2</th>
        <th>k3</th>
        <th>k4</th>
        <th>k5</th>
    </tr>
    </thead>
    <tbody></tbody>
</table>


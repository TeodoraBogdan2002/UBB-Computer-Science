<?php

header('Cache-Control: no-cache, must-revalidate');
header('Expires: Mon, 01 Jan 1996 00:00:00 GMT');
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Credentials');
header('Access-Control-Allow-Methods', 'GET,PUT,POST,DELETE');
header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
require_once 'DatabaseUtils.php';
class Controller {
    private DatabaseUtils $databaseUtils;

    /**
     * Controller constructor.
     */
    public function __construct() {
        $this->databaseUtils = new DatabaseUtils();
    }

    public function run() {
        if (isset($_GET['action']) && !empty($_GET['action'])) {
            switch ($_GET['action']) {
                case 'getForOwner':
                    session_start();
                    $owner = $_GET['ownerName'];
                    $this->getForOwner($owner);
                    break;
                case 'getSubscribesForUser':
                    session_start();
                    $user = $_SESSION["username"];
                    $this->getSubscribesForUser($user);
                    break;
                case 'subscribeToChannel':
                    session_start();
                    $user = $_SESSION["username"];
                    $channelId = $_GET["channelId"];
                    $this->subscribeToChannel($user, $channelId);
                    break;
            }
        }
    }

    private function getForOwner($owner) {
        echo json_encode($this->databaseUtils->selectChannelsByPerson($owner));
    }

    private function getSubscribesForUser($user) {
        echo json_encode($this->databaseUtils->getSubscribedChannelsForUser($user));
    }

    private function subscribeToChannel($user, $channelId) {
        $this->databaseUtils->subscribeToChannel($channelId, $user);
    }
}

$controller = new Controller();
$controller->run();
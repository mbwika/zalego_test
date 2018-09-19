<?php
 
require_once 'include/db_functions.php';
 
// json response array
$response = array("error" => FALSE);
 
if (isset($_POST['firstname']) && isset($_POST['lastname']) && isset($_POST['username']) && isset($_POST['password'])) {
 
    // receiving the post params
	$firstname = $_POST['firstname'];
	$lastname = $_POST['lastname'];
    $username = $_POST['username'];
    $password = $_POST["password"];
 
    // check if user already exists with the same username
    if(usernameExists($username)){
		// username already exists
        $response["error"] = TRUE;
        $response["error_msg"] = "Username already exists with " . $username;
        echo json_encode($response);
	}else {
        // create a new user
        $user = storeUser($firstname, $lastname, $username, $password);
        if ($user) {
            // user stored successfully
            $response["error"] = FALSE;
			$response["user"]["firstname"] = $user["firstname"];
            $response["user"]["lastname"] = $user["lastname"];
            $response["user"]["username"] = $user["username"];
            $response["user"]["password"] = $user["password"];
            echo json_encode($response);
        } else {
            // user failed to store
            $response["error"] = TRUE;
            $response["error_msg"] = "Unknown error occurred!";
            echo json_encode($response);
        }
    }
} else {
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameters missing!";
    echo json_encode($response);
}
?>
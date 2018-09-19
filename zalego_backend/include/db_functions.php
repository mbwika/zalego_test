<?php require_once("include/db_connection.php");?>
<?php

	function storeUser($firstname, $lastname, $username, $password){
		global $connection;
		
		$query = "INSERT INTO users(";
		$query .= "firstname, lastname, username, password) ";
		$query .= "VALUES('{$firstname}', '{$lastname}', '{$username}', '{$password}')";

		$result = mysqli_query($connection, $query);

		if($result){
			$user = "SELECT * FROM users WHERE username = '{$username}' AND password = '{$password}'";
			$res = mysqli_query($connection, $user);

			while ($user = mysqli_fetch_assoc($res)){
				return $user;
			}
		}else{
				return false;
			}

	}


	function getUserByUsernameAndPassword($username, $password){
		global $connection;
		$query = "SELECT * from users where username = '{$username}' and password = '{$password}'";
	
		$user = mysqli_query($connection, $query);
		
		if($user){
			while ($res = mysqli_fetch_assoc($user)){
				return $res;
			}
		}
		else{
			return false;
		}
	}


	function usernameExists($username){
		global $connection;
		$query = "SELECT username from users where username = '{$username}'";

		$result = mysqli_query($connection, $query);

		if(mysqli_num_rows($result) > 0){
			return true;
		}else{
			return false;
		}
	}
//CREATE TABLE `zalego_db`.`users` ( `id` INT(10) NOT NULL AUTO_INCREMENT , `firstname` VARCHAR(100) NULL , `lastname` VARCHAR(100) NULL , `username` VARCHAR(100) NOT NULL , `password` VARCHAR(100) NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB;
?>
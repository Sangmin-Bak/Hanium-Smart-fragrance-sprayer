<?php
	$con = mysqli_connect("127.0.0.1","root","","spray");
//	$con = mysqli_connect("127.0.0.1","root","","php1");

	$LedStatus = $_POST['LedStatus'];

	mysqli_query($con, "INSERT INTO motor(stat) VALUES ('$LedStatus')");
//	mysqli_query($con, "INSERT INTO Person(user_name) VALUES ('$LedStatus')");

//	$mysqli = new mysqli($_POST['LedStatus']);

//	extract($_POST);

	echo $LedStatus;

//	echo json_encode($LedStatus);
?>
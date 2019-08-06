<?php
	$con = mysqli_connect("127.0.0.1","root","1q2w3e4r!@","android_set");
//	$con = mysqli_connect("127.0.0.1","root","","php1");

	$hour = $_POST['hour'];
	$minute = $_POST['minute'];

	mysqli_query($con, "INSERT INTO time_set(hour, minute) VALUES ('$hour', '$minute')");
//	$mysqli = new mysqli($_POST['LedStatus']);

//	extract($_POST);



//	echo json_encode($LedStatus);
?>

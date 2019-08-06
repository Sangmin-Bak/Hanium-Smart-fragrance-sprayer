<?php
	$con = mysqli_connect("127.0.0.1","root","1q2w3e4r!@","android_set");
//	$con = mysqli_connect("127.0.0.1","root","","php1");

	$first = $_POST['first'];
	$second = $_POST['second'];
	$third = $_POST['third'];


	mysqli_query($con, "INSERT INTO fragrance_set(first, second, third) VALUES ('$first', '$second', '$third')");
//	mysqli_query($con, "INSERT INTO setting(switch, first, second, third, hour, minute) VALUES ('$switch', '$first', '$second', '$third', '$hour', '$minute')");
//	mysqli_query($con, "INSERT INTO Person(user_name) VALUES ('$LedStatus')");
	echo $first;
	echo $second;
	echo $third;
?>

<?php
	$con = mysqli_connect("127.0.0.1","root","1q2w3e4r!@","android_set");

//	$con = mysqli_connect("127.0.0.1","root","","php1");

	$city = $_POST['city'];
	
	mysqli_query($con, "DELETE FROM area");
	// 기존에 저장되어 있던 데이터 삭제
	
	mysqli_query($con, "INSERT INTO area(city) VALUES ('$city')");

	echo $city
?>

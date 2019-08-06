<?php
	$con = mysqli_connect("127.0.0.1","root","1q2w3e4r!@","android_set");
//	$con = mysqli_connect("127.0.0.1","root","","php1");

	$weather = $_POST['weather'];
	mysqli_query($con, "DELETE FROM weather_information");
	// 기존에 저장되어 있던 데이터 삭제
	
	mysqli_query($con, "INSERT INTO weather_information(weather) VALUES ('$weather')");
	// 새로운 데이터 삽입
?>

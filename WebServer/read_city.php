<?php
	$con = mysqli_connect("127.0.0.1","root","1q2w3e4r!@","android_set");

	$result = mysqli_query($con, "SELECT * FROM area ORDER BY city DESC limit 1");

	$row = mysqli_fetch_array($result);

	echo $row[0];	// 제어신호를 출력하여 아두이노가 읽을 수 있도록 함

//	mysqli_close($con);
//	echo json_encode($LedStatus);
?>

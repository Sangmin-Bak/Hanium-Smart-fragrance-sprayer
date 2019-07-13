<?php
	$con = mysqli_connect("127.0.0.1","root","","spray");

	$result = mysqli_query($con, "SELECT * FROM motor");

	$row = mysqli_fetch_array($result);

	echo $row[0];	// 제어신호를 출력하여 아두이노가 읽을 수 있도록 함

	mysqli_close($con);
//	echo json_encode($LedStatus);
?>
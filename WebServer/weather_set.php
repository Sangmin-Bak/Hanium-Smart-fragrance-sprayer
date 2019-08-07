<?php
	$con = mysqli_connect("127.0.0.1","root","1q2w3e4r!@","android_set");

	mysqli_query($con, "DELETE FROM test"); // 기존 데이터 삭제

	$fp = fopen("weather_save.txt", "r"); // 그롤링한 날씨정보가 담긴 파일을 열음
	$fr = fread($fp, filesize("weather_save.txt")); // 파일의 크기만큼 읽음, 이래야 DB에 제대로 저장됨
	mysqli_query($con, "INSERT INTO test(wt) VALUES ('$fr')");
	// 새로운 데이터 삽입
	fclose($fp);
?>

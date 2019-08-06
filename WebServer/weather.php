<!DOCTYPE html>
<html>
<head>
<title>weather</title>
</head>
<body>
	<h1></h1>
</form>
</body>
</html>

<?php
	$con = mysqli_connect("127.0.0.1","root","1q2w3e4r!@","android_set");
	
	$result = mysqli_query($con, "SELECT * FROM weather_information");
	$row = mysqli_fetch_array($result); //DB에 저장된 날씨 상황

	$fragrance_set = mysqli_query($con, "SELECT * FROM fragrance_set");
	$fragrance = mysqli_fetch_array($fragrance_set); // 저장된 향기 목록	

	$time_set = mysqli_query($con, "SELECT * FROM time_set");
	$time = mysqli_fetch_array($time_set); // 사용자가 설정한 시간

	$fp = fopen("weather_save.txt", "r"); // 크롤링한 날씨 데이터 파일을 열고 내용을 저장
	
	if($row[0] == "맑음") {
	//	echo "<img src='sun.jpg' border=0 width=100 height=70 align='left' /> ";
	}
	else if($row[0] == "흐림") {
	//	echo "<img src='cloud.jpg' border=0 width=100 height=70 align='left' /> ";
	}
	else if($row[0] == "비옴") {
	//	echo "<img src='rain.jpg' border=0 width=100 height=70 align='left' /> ";
	}
	
//	$fp = fopen("weather_save.txt", "r");
//	$result = fread($fp, filesize("weather_save.txt"));
//	echo nl2br($result);
	while( !feof($fp) ) {
		echo fgets($fp);
		echo '<br>';
	}
	
	fclose($fp);
	
//	mysqli_query($con, "INSERT INTO test(wt) VALUES ('$result')");
	
//	$r = mysqli_query($con, "SELECT * FROM test");
//	$row = mysqli_fetch_array($r);	// 기상정보를 저장한 테이블을 읽어옴

	//	echo $row[0];	// 읽어온 기상정보를 출력

	echo "설정한 향기<br> ";
	echo $fragrance[0]; echo ', ';
	echo $fragrance[1]; echo ', ';
	echo $fragrance[2]; echo '<br>';

	echo "다음 분사 시간 : ";
	echo $time[0]; echo "시 "; echo $time[1]; echo "분 <br>";
?>


<?php
//	$con = mysqli_connect("127.0.0.1","root","1q2w3e4r!@","android_set");

	$location = fopen("file_location.txt", "r");
	//echo "<center>"</center>
	echo "<center>".fgets($location)."</center>"; echo '<br>';
	fclose($location);
	
	$file_weather = fopen("file_weather.txt", "r");
	$file_weather2 = fopen("file_weather.txt", "r");
	$weather = fread($file_weather, 6);
	$weather2 = fread($file_weather2, 12);

	echo "<center>";
	$temp = fopen("file_temp.txt", "r");
	echo fgets($temp); echo "℃ <br>";
	fclose($temp);
	
	if($weather == "맑음") {
		//echo "<img src='sun.png' border=0 width=120 height=120 align='center'/> <br>";
	}
	else if($weather == "흐림")  {
		//echo "<img src='cloudy.png' border=0 width=120 height=120 align='center'/> <br>";
	}
	else if($weather == "비옴") {
		//echo "<img src='rain.png' border=0 width=120 height=120 align='center'/> <br>";
	}
	else if($weather2 == "구름많음") {
		//echo "<img src='cloud.png' border=0 width=120 height=120 align='center'/> <br>";
	}	

	$total_temp = fopen("file_total_temp.txt", "r");
	echo fgets($total_temp); echo " ";
	fclose($total_temp);	

//	header('Content-Type : application/json; charset=utf-8');

	$json_weather = json_encode($weather, JSON_UNESCAPED_UNICODE);
	$json_weather2 = json_encode($weather2, JSON_UNESCAPED_UNICODE);

	if($weather2 == "구름많음") {
		echo "weather2 : "; echo $json_weather2; echo '<br>';
	}
	else {
		echo "weather : "; echo $json_weather; echo '<br>';
	}
	fclose($weather); fclose($weather2);
	
?>


<?php

/*?php*
 * Created by PhpStorm.
 * User: Manish
 * Date: 11/1/2016
 * Time: 6:55 PM
 */
require_once 'dbDetails.php';
 
//connecting to the db
$con = mysqli_connect(DB_HOST,DB_USERNAME,DB_PASSWORD,DB_NAME) or die("Unable to connect");
 
//sql query
$sql = "SELECT * FROM `notices` WHERE NOT School='Science And Applied Technology' AND NOT School='Education' AND NOT School='hds' AND NOT School='Business' ORDER BY id DESC";
$sat= "SELECT * FROM `notices` WHERE School='Science And Applied Technology' ORDER BY id DESC";
$education= "SELECT * FROM `notices` WHERE School='Education'ORDER BY id DESC ";
$hds= "SELECT * FROM `notices` WHERE School='hds'ORDER BY id DESC";
$business= "SELECT * FROM `notices` WHERE School='Business' ORDER BY id DESC";
 
//getting result on execution the sql query
$result = mysqli_query($con,$sql);
 $results= mysqli_query($con,$sat);
 $edu= mysqli_query($con,$education);
 $hd= mysqli_query($con,$hds);
 $bus= mysqli_query($con,$business);
//response array
$response = array();
 
$response['error'] = false;
 
$response['message'] = "Notices Downloaded successfully.";
 
$response['notices'] = array();
$response['sat'] = array(); 
$response['education'] = array();
$response['hds'] = array();  
$response['business'] = array(); 
//traversing through all the rows
 
while($row =mysqli_fetch_array($result)){
    $temp = array();
    $temp['id'] = $row['id'];
    $temp['noticeURL'] = $row['noticeURL'];
    $temp['noticeNAME'] = $row['noticeNAME'];
	 $temp['author'] = $row['author'];
	  $temp['department'] = $row['department'];
	  $temp['school'] = $row['school'];
	 $temp['TimeStamp'] = $row['TimeStamp'];  
    array_push($response['notices'],$temp);
	 
}
while($row =mysqli_fetch_array($results)){
    $temp = array();
    $temp['id'] = $row['id'];
    $temp['noticeURL'] = $row['noticeURL'];
    $temp['noticeNAME'] = $row['noticeNAME'];
	 $temp['author'] = $row['author'];
	  $temp['department'] = $row['department'];
	   $temp['school'] = $row['school'];
	 $temp['TimeStamp'] = $row['TimeStamp'];  
  
	 array_push($response['sat'],$temp);
}
while($row =mysqli_fetch_array($edu)){
    $temp = array();
    $temp['id'] = $row['id'];
    $temp['noticeURL'] = $row['noticeURL'];
    $temp['noticeNAME'] = $row['noticeNAME'];
	 $temp['author'] = $row['author'];
	  $temp['department'] = $row['department'];
	   $temp['school'] = $row['school'];
	 $temp['TimeStamp'] = $row['TimeStamp'];  
  
	 array_push($response['education'],$temp);
}
while($row =mysqli_fetch_array($hd)){
    $temp = array();
    $temp['id'] = $row['id'];
    $temp['noticeURL'] = $row['noticeURL'];
    $temp['noticeNAME'] = $row['noticeNAME'];
	 $temp['author'] = $row['author'];
	  $temp['department'] = $row['department'];
	   $temp['school'] = $row['school'];
	 $temp['TimeStamp'] = $row['TimeStamp'];  
  
	 array_push($response['hds'],$temp);
}
while($row =mysqli_fetch_array($bus)){
    $temp = array();
    $temp['id'] = $row['id'];
    $temp['noticeURL'] = $row['noticeURL'];
    $temp['noticeNAME'] = $row['noticeNAME'];
	 $temp['author'] = $row['author'];
	  $temp['department'] = $row['department'];
	   $temp['school'] = $row['school'];
	 $temp['TimeStamp'] = $row['TimeStamp'];  
  
	 array_push($response['business'],$temp);
}
echo json_encode($response);
?>
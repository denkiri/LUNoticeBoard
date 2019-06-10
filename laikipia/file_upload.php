<?php
 
ServerConfig();

$noticesFolder = 'noticesFolder/';
 
$ServerURL = 'http://192.168.43.237:80/laikipia/'.$noticesFolder;
 
if($_SERVER['REQUEST_METHOD']=='POST'){
 
    if(isset($_POST['name']) and isset($_FILES['pdf']['name'])){

	$con = mysqli_connect(HostName,HostUser,HostPass,DatabaseName);
		
        $PdfName = $_POST['name'];
		$noticeauthor = $_POST['author'];
		$department = $_POST['department'];
		$school =  $_POST['school'];
        $PdfInfo = pathinfo($_FILES['pdf']['name']);
 
        $PdfFileExtension = $PdfInfo['extension'];
 
        $PdfFileURL = $ServerURL . GenerateFileNameUsingID() . '.' . $PdfFileExtension;
 
        $PdfFileFinalPath = $noticesFolder . GenerateFileNameUsingID() . '.'. $PdfFileExtension;
 
        try{
			
            move_uploaded_file($_FILES['pdf']['tmp_name'],$PdfFileFinalPath);
		
			 $InsertTableSQLQuery = "INSERT INTO notices (noticeURL, noticeNAME, author, department,school,TimeStamp ) VALUES ('$PdfFileURL', '$PdfName','$noticeauthor','$department','$school', NOW()) ;";	
			
				
            mysqli_query($con,$InsertTableSQLQuery);

        }catch(Exception $e){} 
        mysqli_close($con);
		
    }
}

function ServerConfig(){
	
define('HostName','localhost');
define('HostUser','root');
define('HostPass','');
define('DatabaseName','laikipia');
	
}

function GenerateFileNameUsingID(){
    
	$con2 = mysqli_connect(HostName,HostUser,HostPass,DatabaseName);
	
	$GenerateFileSQL = "SELECT max(id) as id FROM notices";
	
    $Holder = mysqli_fetch_array(mysqli_query($con2,$GenerateFileSQL));

    mysqli_close($con2);
	
    if($Holder['id']==null)
	{
        return 1;
	}
    else
	{
        return ++$Holder['id'];
	}
}

?>
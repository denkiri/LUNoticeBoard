<?php
 
ServerConfig();
if($_SERVER['REQUEST_METHOD']=='POST'){
 
    if(isset($_POST['name']) and isset($_FILES['pdf']['name'])){

	$con = mysqli_connect(HostName,HostUser,HostPass,DatabaseName);
		
          $name = $_POST['name'];
 	$username = $_POST['username'];
 	$password = $_POST['password'];
 	$department= $_POST['department']; 
        try{
			
        
		
			 $InsertTableSQLQuery =	 "INSERT INTO register (name,department,username,password) VALUES ('$name','$department','$username','$password')";
			
				
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
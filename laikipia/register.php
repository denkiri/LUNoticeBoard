<?php
 
   if($_SERVER['REQUEST_METHOD']=='POST'){
  // echo $_SERVER["DOCUMENT_ROOT"];  // /home1/demonuts/public_html
//including the database connection file
       include_once("config.php");
       
        $name = $_POST['name'];
 	$username = $_POST['username'];
	$id = $_POST['id'];
	$school = $_POST['school'];
 	$password = $_POST['password'];
 	$department= $_POST['department'];
	 if($name == '' || $username == '' || $password == '' || $department == ''){
	        echo json_encode(array( "status" => "false","message" => "All fields are required!") );
	 }else{
			
	        $query= "SELECT * FROM register WHERE username='$username'";
	        $result= mysqli_query($con, $query);
		
		 
	        if(mysqli_num_rows($result) > 0){  
				
	           echo json_encode(array( "status" => "false","message" => "Username already exist!") );
	        }else{ 
		 	 $querys = "INSERT INTO register (name,school,department,username,password) VALUES ('$name','$school','$department','$username','$password')";
			$quer = mysqli_query($con, "UPDATE register SET name='$name',school='$school',department='$department',username='$username',password='".md5($password)."' WHERE id=$id");
			
			 if(mysqli_query($con,$quer)){
			    
			     $quer= "SELECT * FROM register WHERE name='$name'";
	                     $result= mysqli_query($con, $quer);
		             $emparray = array();
	                     if(mysqli_num_rows($result) > 0){  
	                     while ($row = mysqli_fetch_assoc($result)) {
                                     $emparray[] = $row;
                                   }
	                     }
			    echo json_encode(array( "status" => "false","message" => "Password and Username changed successfully" , "data" => $emparray) );
		 	 }else{
		 		 echo json_encode(array( "status" => "true","message" => "Error occured, please try again!") );
		 	}
	    }
	            mysqli_close($con);
	 }
     } else{
			echo json_encode(array( "status" => "false","message" => "Error occured, please try again!") );
	}
 
 ?>
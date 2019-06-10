<html>
<head>
	<title>Register New Secretary</title>
</head>

<body>
<?php
//including the database connection file
include_once("config.php");

if(isset($_POST['Submit'])) {	
	$name = mysqli_real_escape_string($mysqli, $_POST['name']);
	$school = mysqli_real_escape_string($mysqli, $_POST['school']);
	$department = mysqli_real_escape_string($mysqli, $_POST['department']);
	$username = mysqli_real_escape_string($mysqli, $_POST['username']);
	$password = mysqli_real_escape_string($mysqli, $_POST['password']);
	

	// checking empty fields
	if(!preg_match('/^[a-z ]+$/i', $name) || empty($department) || empty($username) || empty($password)) {
		if(!preg_match('/^[a-z ]+$/i', $name)) {
      echo "<font color='red'>Incorrect Name/Name missing</font><br/>";
        }
		if(empty($department)) {
			echo "<font color='red'>Department field is empty.</font><br/>";
		}
		
		if(empty($username)) {
			echo "<font color='red'>Username field is empty.</font><br/>";
		}
		
		if(empty($password)) {
			echo "<font color='red'>Password field is empty.</font><br/>";
		}
		
		//link to the previous page
		echo "<br/><a href='javascript:self.history.back();'>Go Back</a>";
	} else { 
	
		// if all the fields are filled (not empty) 
		$query= "SELECT * FROM register WHERE department='$department'";
		 $result= mysqli_query($mysqli, $query);
		 $user= "SELECT * FROM register WHERE username='$username'";
		 $results= mysqli_query($mysqli, $user);
		 if(mysqli_num_rows($result) > 0){  
	           echo  "Department has a Secretary!";
			  echo "<br/><a href='javascript:self.history.back();'>Go Back</a>"; 
	        }
		 
	        elseif(mysqli_num_rows($results) > 0){  
	           echo  "<br/>Username already Exist!";
			  echo "<br/><a href='javascript:self.history.back();'>Go Back</a>"; 
	        }else{ 
		//insert data to database	
		$result = mysqli_query($mysqli, "INSERT INTO register(name,school,department,username,password) VALUES('$name','$school','$department','$username','".md5($password)."')");
		
		//display success message
		echo "<font color='green'>Secretary added successfully.";
		echo "<br/><a href='home.php'>View Result</a>";
	}
}}
?>
</body>
</html>

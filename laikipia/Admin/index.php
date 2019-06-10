<?php
require('config.php');
session_start();
// If form submitted, insert values into the database.
if (isset($_POST['username'])){
        // removes backslashes
	$username = stripslashes($_REQUEST['username']);
        //escapes special characters in a string
	$username = mysqli_real_escape_string($mysqli,$username);
	$password = stripslashes($_REQUEST['password']);
	$password = mysqli_real_escape_string($mysqli,$password);
	//Checking is user existing in the database or not
        $query = "SELECT * FROM `admin` WHERE username='$username'
and password='$password'";
	$result = mysqli_query($mysqli,$query) or die(mysql_error());
	$rows = mysqli_num_rows($result);
        if($rows==1){
	    $_SESSION['username'] = $username;
            // Redirect user to home.php
	    header("Location: home.php");
         }else{
	echo '<script> alert("Wrong Username or password...")</script>';

	}
    }
?>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Login</title>
<link rel="stylesheet" href="css/style4.css" />
<style type="text/css">
 header{
       	width: 100%;
	   	background-color: #999;
	   	height: 35px;
	   	left: 0;
	   	position: fixed;
	   	color: #fff;
	   	line-height: 35px;
	   	top: 0;
	   	text-align: center;
       }
       header a{
	   	color: #fff;
	   	text-decoration: none;
	   	font-size: 18px;
	   }
	   .inptfrms{
	   	margin: 55px auto;
	   	width: 45%;
	   	background-color: red;
	   	border: 1px solid #fff;
	   	border-radius: 8px;
	   	text-align: center;
	   	color: #fff;
	   	padding: 40px;


	   }
	   input[type=text],input[type=password]{
	 
	   	border-top: none;
	   	border-left: none;
	   	border-right: none;
	   	border-bottom: 2px solid #fff;
	   	padding: 12px 22px;
	   	background-color: transparent;
	   	font-size: 19px;
	   }
	   .inptfrms table{
	   	margin: 0px auto;
	   }
	   body{
	   	width: 100%;
	   	background-color: #fff;

	   }
	   .inptfrms h3{

          font-size: 25px;
          color: #fff;
	   }
	   .addbtn{
	   	padding: 8px 16px;
	   	color: white;
	   	background-color: #777;
	   	border-radius: 4px;
	   	border: 1px solid #777;
	   	width: 55%;
	   }
	   .logoutbtn{
	   	float: right;
	   	padding: 0 70px;
	   }
*{
	padding: 0;
	margin: 0;
}
body{
	width: 100%;
	position: fixed;
	background-color: #fff;
	height: 100%;
	
	font-family: sans-serif;
}
.form{
	margin: 100px auto;
	width: 24%;
	background-color:red;
	border-radius: 5px;
	text-align: center;
	padding: 6%;
	color: #fff;
	
}
#inp{
	padding: 10px 21px;
	border: 1px solid #333;
	border-radius: 5px;
	width: 98%;
	background-color:#fff
	margin: 8px;
	font-size: 16px;
}
.btn{
	padding: 9px 20px;
	border-radius: 5px;
	border: 1px solid #444;
	background-color: #444;
	width: 65%;
	color: #fff;
}
label{
	font-size: 21px;
	float: left;
}
.form h1{
	color: beige;
}


</style>
</head>
<body>
	<header> <a href="#" class="logo" >Laikipia Universitity Notice Board App Admin page</a> </header>
	<br/><br/>
	<section class="form">

  
      <h1>Admin Log In Here</h1>
      <form action="" method="post" name="login">
      	   <div class="inputs">
      	   	    <label><b>Username</b></label><br/>
                <input type="text" name="username" placeholder="Enter Username" id="inp" required />
            </div>
            <div class="inputs">
            	<label><b>Password</b></label><br/>
                <input type="password" name="password" placeholder="Enter Password" id="inp" required />
             </div>
            <input name="submit" type="submit" class="btn" value="Login" />
      </form>
 
 </section>
 <footer></footer>

</body>
</html>
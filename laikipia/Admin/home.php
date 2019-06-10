<?php
session_start();
//including the database connection file
include_once("config.php");

//fetching data in descending order (lastest entry first)
//$result = mysql_query("SELECT * FROM users ORDER BY id DESC"); // mysql_query is deprecated
$result = mysqli_query($mysqli, "SELECT * FROM register ORDER BY id DESC"); // using mysqli_query instead
?>

<html>
<head>	
	<title>Admin page</title>
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
	   }
	   .logoutbtn{
	   	float: right;
	   	padding: 0 70px;
	   }
	   .logo{
	   	font-size: 19px;
	   }
	   .disp{
	   	margin: 55px 30px;
	   	width: 90%;
	   	background-color: #fff;
	   	border: 1px solid #fff;
	   	border-radius: 4px;


	   }
	   #newsec{
	   	text-decoration: none;
	   	font-size: 19px;
	   }
	   #upload{
	   	text-decoration: none;
	   	font-size: 19px;
		float: right;
	   	padding: 0 70px;
	   }
	   tr{
	   	border-bottom: 2px solid red;
	   }
	   .ed{
	   	text-decoration: none;
	   	color: #fff;
	   	padding: 2px 5px;
	   	border-radius: 3px;
	   	background-color: deepskyblue;
	   }
	   .del{
	   	text-decoration: none;
	   	color: #fff;
	   	padding: 2px 5px;
	   	border-radius: 3px;
	   	background-color: #800000;
	   }
	   body{
	   	width: 100%;
	   	background-color: #e1e1e1;
	   }
	   .disp table{
	   	margin: 0px auto;
	   }

	</style>
</head>

<body>
	<header>
	
            <a href="#" class="logo" >Laikipia Universitity Notice Board App Admin page</a>
             <a href="logout.php" class="logoutbtn" title="logout from this page">Logout</a>
            <br/><br/>
     </header>

<section class="disp">
	<br>
	    <a href="register.html" id="newsec"><i style="font-size:11px">click here to</i>Register New Secretary</a> 
           <a href="uploaded.php" id="upload">Uploaded Notices</a> 
<br><br><br>

	      <table width='80%' border=0>

	       <tr bgcolor='red'>
		       <td style="color:#fff; text-align:center;">Name</td>
		       <td style="color:#fff; text-align:center;">School</td>
		       <td style="color:#fff; text-align:center;">Department</td>
		       <td style="color:#fff; text-align:center;">Update</td>
	       </tr>
	       <?php 
	       //while($res = mysql_fetch_array($result)) { // mysql_fetch_array is deprecated, we need to use mysqli_fetch_array 
	       while($res = mysqli_fetch_array($result)) { 		
		       echo "<tr>";
		       echo "<td style='text-align:center'>".$res['name']."</td>";
		       echo "<td style='text-align:center'>".$res['school']."</td>";
		       echo "<td style='text-align:center'>".$res['department']."</td>";	
		       echo "<td style='text-align:center'><a  href=\"edit.php?id=$res[id]\"  class='ed' >Edit </a> | <a href=\"delete.php?id=$res[id]\" class='del' onClick=\"return confirm('Are you sure you want to delete?')\">Delete</a></td>";		
	       }
	       ?>
	       </table>
 </section>
	<footer>

	</footer>
</body>
</html>

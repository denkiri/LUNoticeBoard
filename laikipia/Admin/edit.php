<?php
// including the database connection file
include_once("config.php");

if(isset($_POST['update']))
{	

	$id = mysqli_real_escape_string($mysqli, $_POST['id']);
	
	$name = mysqli_real_escape_string($mysqli, $_POST['name']);
	$school = mysqli_real_escape_string($mysqli, $_POST['school']);
	$department = mysqli_real_escape_string($mysqli, $_POST['department']);	
	// checking empty fields
	if(empty($name) || empty($school) || empty($department)) {	
			
		if(empty($name)) {
			echo "<font color='red'>Name field is empty.</font><br/>";
		}
		
		if(empty($school)) {
			echo "<font color='red'>School field is empty.</font><br/>";
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
	}

	else {	
		//updating the table
		$result = mysqli_query($mysqli, "UPDATE register SET name='$name',school='$school',department='$department',username='$username',password='".md5($password)."' WHERE id=$id");
		
		//redirectig to the display page. In our case, it is index.php
		header("Location: home.php");
	}
}
?>
<?php
//getting id from url
$id = $_GET['id'];

//selecting data associated with this particular id
$result = mysqli_query($mysqli, "SELECT * FROM register WHERE id=$id");

while($res = mysqli_fetch_array($result))
{
	$name = $res['name'];
	$school = $res['school'];
	$department = $res['department'];
	$username = $res['username'];
	$password = $res['password'];
}
?>
<html>
<head>	
	<title>Edit Details</title>
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

	</style>
</head>

<body>
<header>
	         <a href="#" class="logo" >Laikipia Universitity Notice Board App Admin page</a>
	         <a href="home.php" class="logoutbtn" title="Exit from this page">Exit</a>
	</header>
	<br/><br/>


	
	<form name="form1" method="post" action="edit.php"class="inptfrms">
	<h3 style="">Edit secretary details here</h3>
		<table border="0">
			<tr> 
				<td style="color:#fff; font-size:18px;"><b> Name </b><span style="color:#fff;">*</span></td>
				<td><input type="text" name="name" value="<?php echo $name;?>"></td>
			</tr>
			<tr> 
				<td style="color:#fff; font-size:18px;"><b>School </b><span style="color:#fff;">*</span> </td>
				<td>
				 <select name = "school"id="inpfrms">
			    <option value="<?php echo $school;?>"><?php echo $school;?> </option>
               <option  value = "Science And Applied Technology" id="inpfrms">Science And Applied Technology</option>
			    <option  value = "School of Humanities and Development Studies" id="inpfrms">School of Humanities and Development Studies</option>
               <option value = "Education" id="inpfrms">Education</option>
               <option value = "Business" id="inpfrms" >Business</option>  
             </select>
				</td>
			</tr>
			<tr> 
				<td style="color:#fff; font-size:18px;"><b>Department</b> <span style="color:#fff;">*</span>  </td>
				<td>
				<select name = "department"id="inpfrms">
				<option value="<?php echo $department;?>"><?php echo $department;?></option>
                <option  value = "Biological And Biomedical Sciences Technology" id="inpfrms">Biological And Biomedical Sciences Technology</option>
                <option value = "Chemistry And Biochemistry" id="inpfrms">Chemistry And Biochemistry</option>
			    <option value = "Physics" id="inpfrms" >Physics</option>
                <option value = "Mathematics" id="inpfrms" >Mathematics</option>
                <option value = "Computing And Informatics" id="inpfrms">Computing And Informatics</option>
			    <option value = "Institute Of Arid Lands Management" id="inpfrms">Institute Of Arid Lands Management</option>
			    <option value = "Literary And Communication Studies" id="inpfrms">Literary And Communication Studies</option>
			    <option value = "Public Affairs And Environmental Studies" id="inpfrms">Humanities And Development Studies</option>
				<option value = "Sport Science" id="inpfrms">Sport Science</option>
				<option value = "Commerce" id="inpfrms">Commerce</option>
				<option value = "Economics" id="inpfrms">Economics</option>
				<option value = "Psychological,Counselling And Educational Foundations " id="inpfrms">Psychological,Counselling And Educational Foundations</option>
				<option value = "Carriculum And Educational Management" id="inpfrms">Carriculum And Educational Management</option>	
             </select>
				</td>
			</tr>
			<tr>
				<td><input type="hidden" name="id" value=<?php echo $_GET['id'];?>></td>
				<td><input type="submit" name="update" value="Update"></td>
			</tr>
		</table>
	</form>
</body>
</html>

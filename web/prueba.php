<?php
echo "Hola";
$servername = '192.168.0.12';
$database = 'servidor';
$username = 'alumnosphp';
$password = 'uiop098765';
$puerto = 3306;
$c = mysqli_connect($servername, $username, $password, $database, $puerto) or die ("Error de conexión: " . mysqli_connect_error());
mysqli_select_db($c,$database);
$sql="select * from operaciones";
$r=mysqli_query($c,$sql);
echo "<br>";
echo "<center><table border=1>";
      while ($arr=mysqli_fetch_array($r))
      {
echo "<tr>";
echo "<td width=100>" . $arr['Id'] . "</td>";
echo "<td width=100>" . $arr['num1'] . "</td>";
echo "<td width=100>" . $arr['num2'] . "</td>";
echo "<td width=100>" . $arr['Operacion'] . "</td>";
echo "<td width=100>" . $arr['resultado'] . "</td>";
echo "</tr>";
      }
echo "</table></center>";
?>
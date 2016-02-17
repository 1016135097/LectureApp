<?php 
/*---------------------------------ħ������-----------------------------------------*/
if (get_magic_quotes_gpc())
{
	$process = array(&$_GET, &$_POST, &$_COOKIE, &$_REQUEST);
	while (list($key, $val) = each($process))
	{
		foreach ($val as $k => $v)
		{
			unset($process[$key][$k]);
			if (is_array($v))
			{
				$process[$key][stripslashes($k)] = $v;
				$process[] = &$process[$key][stripslashes($k)];
			}
			else
			{
				$process[$key][stripslashes($k)] = stripslashes($v);
			}
		}
	}
	unset($process);
}

/*-----------------------------------------�û��鼮��Ϣ��ȡ--------------------------------------------*/

$sendId=$_POST['Id'];
//$sendId='7';

//���ݿ�����
$dsn = 'mysql:host=localhost;dbname=lectureapp';
$user = 'javamanager';
$password = 'admin123';
try
{
	$pdo = new PDO($dsn, $user, $password);
	$pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	$pdo->exec('SET NAMES "utf8"');
}
catch (PDOException $e)
{
	$error = 'Unable to connect to the database server.';
	echo $error;
	exit();
}

//��ѯ���ݿ��Ի�ȡ��Ϣ

	try {
		$sql='UPDATE lecture SET SignUpNum=SignUpNum+1 WHERE id=:sendId';
		$s = $pdo->prepare($sql);
		$s->bindValue(':sendId', $sendId);
		$s->execute();
	} catch (PDOException $e) {
		$error = 'Error Fetching!';
		echo $error;
		exit();
	}
	
	/*---------------------------------------------���������Ϣ���ͻ���---------------------------*/

		$ReturnInf=array(
			'Message'=>'Sign Up Succeed',
		);
		echo json_encode($ReturnInf,JSON_UNESCAPED_UNICODE);

?>
<?php

    if($_SERVER['REQUEST_METHOD']=='POST')
    {
        $email = $_POST['email'];
        $password = $_POST['password'];

        require_once('connect.php');

        $sql = "SELECT * FROM auth where username='$email'";

        $response = mysqli_query($conn,$sql);

        $result = array();
        $result['login'] = array();

        if(mysqli_num_rows($response) === 1)
        {
            $row = mysqli_fetch_assoc($response);

            if(password_verify($password,$rows['password'] ))
            {
                $index['name'] = $row['username'];
                array_push($result['login'],$index);

                $result['done'] = "1";
                $result['message'] = "Success";
                echo json_encode($result);
                mysqli_close($conn);
            }
            else{
                
                $result['done'] = "0";
                $result['message'] = "error";
                echo json_encode($result);
                mysqli_close($conn);
            }
        }

    }


?>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Twitter</title>
    </head>
    <style>
        *{
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: Tahoma, sans-serif;
        }
        header{
            position: fixed;
            background-color: #ffffff;
            display: flex;
            justify-content: space-between;
            align-items: center;
            box-shadow: 1px 1px rgb(0 0 0 / 10%);
            border-bottom: 2px solid #f9f9fa;
            width:100%;
            height:3rem;
            padding: 0 32px;
            z-index: 10;
        }

        .input {
            display: inline-block;
            padding: 10px 12px;
            border: 1px solid #c2c8ce;
            background-color: #fafafa;
            border-radius: 4px;
            width: 18rem;
            height:2rem;
            position: relative;
            overflow: hidden;
            font-size: 1rem;
            color: #1B1B1E;
            z-index: 1;
        }

        .logo{
            font-size: 1.5rem;
            font-weight: normal;
            color: #6f3fd8;
            text-transform: uppercase;
        }

        .headerDiv{
            display: flex;
            gap:2rem;
            align-items: center;
        }

        a{
            cursor: pointer;
        }




    </style>
    <body>
        <header>
            <div class="headerDiv">
                <a class="logo" href="/TwitterClone/feed">Chirper</a>
                <form method="POST"  action="/TwitterClone/feed">
                    <input class="input" type="text" name="username" value="" placeholder="Search"/>
                </form>  
            </div>
            <a href="/TwitterClone/login?action=logout">Log out</a>
        </header>
    </body>
</html>

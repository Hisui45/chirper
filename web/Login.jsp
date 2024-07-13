<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <style>
            *{
                margin: 0;
                padding: 0;
                box-sizing: border-box;
                font-family: Gill, Helvetica, sans-serif;
            }

            main{
                width: 100%;
                min-height: 100vh;
                display: flex;
                padding: 0.5rem 0.5rem;
            }

            section{
                display:flex;
                flex-direction: column;
                padding-left: 4rem;
                padding-top: 10rem;
            }

            aside{
                background-color: #6f3fd8;
                padding-top: 15rem;
                display:flex;
                flex-direction: column;
                margin-left: 6rem;
                width: 100%;
                height: 97.5vh;
                border-radius: 8px;
            }

            body{
                background-color: #ffffff;
                /*padding-right: 4rem;*/
            }

            fieldset{
                outline: none;
                border:none;
                margin-top: 0.5rem;
            }

            .input {
                display: inline-block;
                padding: 10px 12px;
                border: 1px solid #1B1B1E;
                border-radius: 4px;
                width: 18rem;
                position: relative;
                overflow: hidden;
                font-size: 1rem;
                color: #1B1B1E;
                z-index: 1;
                margin-top: 0.3rem;
            }

            .button {
                display: inline-block;
                padding: 10px 24px;
                border: 1px solid #1B1B1E;
                border-radius: 4px;
                width: 18rem;
                transition: all 0.2s ease-in;
                position: relative;
                overflow: hidden;
                font-size: 1rem;
                cursor: pointer;
                color: #1B1B1E;
                z-index: 1;
                text-align: center;
                margin-top: 1rem;
            }


            .button:hover {
                background-color: #74b9ff;
            }

            a{
                text-decoration: none;
                color: black;
            }


            a:hover{
                color: #74b9ff;
            }

            h1{
                font-size: 2rem;
                margin-bottom: 0.5rem;
                font-weight: normal;
                color: #1B1B1E;
            }

        </style>
    </head>
    <body>
        <main>
            <section>
                <h1>Login</h1>
                <form method="post">
                    <input type="hidden" name="action" value="login">
                    <fieldset>
                        <label for="email_address">Email Address</label><br>
                        <input class="input" type="email" id="email_address" name="email_address" value="${param.email_address}" required="true">
                    </fieldset>
                    <fieldset>
                        <label for="password">Password</label><br>
                        <input class="input" type="password" id="password" name="password" value="${param.password}" required="true">
                    </fieldset>
                    <% if (request.getAttribute("loginError") != null) {%>
                    <p style="color: red;"> <%= request.getAttribute("loginError")%></p>
                    <% }%>
                    <a href="sign-up">New user? Sign Up!</a>
                    <br>
                    <input class="button" type="submit" value="Login">
                </form>
            </section>
            <aside>

            </aside>
        </main>
    </body>
</html>

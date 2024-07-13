<%-- 
    Document   : error
    Created on : Apr 19, 2024, 4:35:38 AM
    Author     : jadel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>:(</title>
        <style>
            *{
                margin: 0;
                padding: 0;
                box-sizing: border-box;
                font-family: Gill, Helvetica, sans-serif;
            }

            main{
                padding: 4rem 2rem;
            }
            
            a{
                text-decoration: none;
            }
        </style>
    </head>
    <body>
        <%@include file="views/header.jsp" %>
        <main>
            <h1>It seems you've gotten lost.</h1>
            <p>Something went wrong :(</p>
            <p>${error}</p>
        </main>

    </body>
</html>

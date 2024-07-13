<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  
    </head>
    <style>
        .chirpCard{
           background-color: white; 
           width: 100%; 
           padding: 0.5rem 0.5rem; 
           border-radius: 8px;
           margin-bottom: 1rem;
        }
        
        .chirpName{
            font-weight: normal;
            font-size:24px;
        }
        
        .user-actions{
            display: flex;
            align-items: center;
            gap:0.5rem;
            margin-bottom: 4px;
        }
        
        .like-actions{
            display: flex;
            align-items: center;
            gap:0.5rem;
            margin-top: 4px;
        }
        
        a{
           text-decoration: none;
           color: #6f3fd8;
        }
        
        .chirpImg{
            margin: 1rem 0;
            width: 700px;
            max-width:700px;
            height:auto;
        }
        
        .button {
                display: inline-block;
                padding: 8px 8px;
                border: 1px solid #1B1B1E;
                border-radius: 4px;
                transition: all 0.2s ease-in;
                position: relative;
                overflow: hidden;
                font-size: 16px;
                cursor: pointer;
                color: #1B1B1E;
                z-index: 1;
                text-align: center;
            }


            .button:hover {
                background-color: #74b9ff;
            }
    </style>
    <body>
        <div class="chirpCard">
            <h4 class="chirpName">${param.name}</h4>
            <div class="user-actions">
                <a href="/TwitterClone/user/${param.username}">@${param.username}</a>
                <c:choose>
                    <c:when test="${param.following}">
                        <p>Following</p>
                    </c:when>
                    <c:otherwise>
                        <form>
                            <input type="hidden" name="action" value="follow">
                            <input class="button" type="submit" value="Follow"/>
                        </form>  
                    </c:otherwise>
                </c:choose>
            </div>

            <p>${param.text}</p>
            <img class="chirpImg" style="display: none;" src="/TwitterClone/GetImage?chirpId=${param.id}" alt="alt" onload="this.style.display = 'block';"/>
            <div class="time">
                <span>${param.date}</span>
                <span>${param.time}</span>
            </div>
            <div class="like-actions">
                <c:choose>
                    <c:when test="${param.liked}">
                        <form method="POST"  action="/TwitterClone/user/${param.username}">
                            <input type="hidden" name="action" value="unlike">
                            <input type="hidden" name="chirpId" value="${param.id}">
                            <input type="hidden" name="src" value="${param.src}">
                            <input class="button" type="submit" value="Remove Like"/>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <form method="POST"  action="/TwitterClone/user/${param.username}">
                            <input type="hidden" name="action" value="like">
                            <input type="hidden" name="chirpId" value="${param.id}">
                            <input type="hidden" name="src" value="${param.src}">
                            <input class="button" type="submit" value="Like"/>
                        </form>  
                    </c:otherwise>
                </c:choose>
                <p>
                    <c:choose>
                        <c:when test="${param.likeCount eq 0}">
                            No Likes
                        </c:when>
                        <c:when test="${param.likeCount eq 1}">
                            ${param.likeCount} Like
                        </c:when>
                        <c:otherwise>
                            ${param.likeCount} Likes
                        </c:otherwise>
                    </c:choose></p>
            </div>

        </div>
    </body>
    <script>
        
    </script>
</html>

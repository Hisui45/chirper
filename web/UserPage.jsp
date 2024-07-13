<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${userPage.username}</title>
        <style>
            *{
                margin: 0;
                padding: 0;
                box-sizing: border-box;
                font-family: Tahoma, sans-serif;
            }

            main{
                width: 100%;
                min-height: 100vh;
                display:grid;
                display: flex;
                padding-top: 3rem;
            }

            body{
                background-color: #ffffff;
                /*padding-right: 4rem;*/
            }

            aside{
                position: relative;
                background-color: #fdfdfb;
                padding: 16px;
                width: 350px;
                box-shadow: 1px 0px rgb(0 0 0 / 10%);
            }

            .chirpForm{
                position: fixed;
            }

            .chirpFormHeader{
                font-weight: normal;
                font-size: 24px;
            }

            textarea{
                width: calc(350px - 32px);
                height: 150px;
                resize: none;
                padding: 10px 12px;
                border: 1px solid #c2c8ce;
                background-color: #fafafa;
                border-radius: 4px;
                overflow: hidden;
                font-size: 1rem;
                color: #1B1B1E;

            }

            section{
                background-color: #f9f9f9;
                padding: 0 16px;
                flex-grow: 1;
                box-shadow: 1px 0px rgb(0 0 0 / 10%);
            }
            fieldset{
                margin-top: 0.5rem;
                outline: none;
                border:none;
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

            .chirps{
                padding-top: 3rem;
            }

            .feedHeader{
                padding-top: 0.5rem;
                width: 50%;
                position: fixed;
                height: 3rem;
                background-color: #f9f9f9;
                z-index:9;
                margin-right: 20rem;

            }

        </style>
    </head>
    <body>
        <%@include file="views/header.jsp" %>
        <main>
            <aside>  
                <form class="chirpForm" action="/TwitterClone/feed?src=userPage&username=${userPage.username}" method="POST" enctype="multipart/form-data">
                    <input type="hidden" name="action" value="chirp">
                    <h1 class="chirpFormHeader">What ya chirpin about?</h1>
                    <textarea id="chirpText" name="chirpText" required="true" ></textarea>
                    <img id="displayImg" width="272" height="auto" alt="alt" style="display: none;" onload="this.style.display = 'block';"/>
                    <input type="file" accept="image/*" id="chirpImg" name="chirpImg" onchange="loadAjax()" style="display: none;">
                    <fieldset>
                        <input class="button" type="button" value="Upload Image" onclick="document.getElementById('chirpImg').click();" />
                        <input class="button" type="submit" value="Chirp"/>
                    </fieldset>

                </form>
            </aside>
            <section class="feed">
                <div class="feedHeader">
                    <h1>Feed</h1>
                </div>
                <div class="chirps">
                    <c:forEach var="chirp" items="${userPageChirps}">
                        <jsp:include page="views/chirp.jsp">
                            <jsp:param name="id" value="${chirp.id}"/>
                            <jsp:param name="name" value="${chirp.user.name}"/>
                            <jsp:param name="username" value="${chirp.user.username}"/>
                            <jsp:param name="following" value="${chirp.following}"/>
                            <jsp:param name="liked" value="${chirp.liked}"/>
                            <jsp:param name="likeCount" value="${chirp.likeCount}"/>
                            <jsp:param name="text" value="${chirp.text}"/>
                            <jsp:param name="date" value="${chirp.date}"/>
                            <jsp:param name="time" value="${chirp.time}"/>
                            <jsp:param name="src" value="userPage"/>
                        </jsp:include>
                    </c:forEach>
                </div>
            </section>
            <aside>
                <div style='position: fixed;'>
                    <h1>${userPage.name}</h1>
                    <div class="user-actions">
                        <h2>@${userPage.username}</h2>
                        <c:choose>
                            <c:when test="${userPage.following}">
                                <form>
                                    <input type="hidden" name="action" value="unfollow">
                                    <input class="button" type="submit" value="Unfollow"/>
                                </form> 
                            </c:when>
                            <c:otherwise>
                                <form>
                                    <input type="hidden" name="action" value="follow">
                                    <input class="button" type="submit" value="Follow"/>
                                </form>  
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </aside>
        </main>
    </body>
    <script type="text/javascript">
        function loadAjax() {
            var image = document.getElementById("chirpImg");

            var formData = new FormData();
            formData.append('chirpImg', image.files[0]);


            var url = "/TwitterClone/GetImage?action=display";

            if (window.XMLHttpRequest) {
                request = new XMLHttpRequest();
            }
            try {
                request.onreadystatechange = sendInfo;
                request.responseType = "blob";
                request.open("POST", url, true);
                request.setRequestHeader('enctype', 'multipart/form-data');
                request.send(formData);

            } catch (e) {
                alert("Unable to connect server");
            }

        }

        function sendInfo() {
            if (request.readyState === 4) {
                if (request.status === 200) {
                    var blob = new Blob([request.response], {type: request.getResponseHeader('Content-Type')});
                    var displayImg = document.getElementById("displayImg");
                    displayImg.src = window.URL.createObjectURL(blob);
                }
            }
        }
    </script>
</html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <plsar:if spec="${message != ''}">
        <p class="notify">${message}</p>
    </plsar:if>

    <h1>Request a Business!</h1>
    <p>You may request your place of employment to be listed!</p>

    <form action="/signup_request/save" method="post">

        <label>Business Name</label>
        <span class="tiny">Where in the world is this place located?</span><br/>
        <input type="text" name="name" style="width:100%" placeholder="McDonalds"/>

        <label>Town/City</label>
        <span class="tiny">Where in the world is this place located?</span><br/>
        <textarea name="address" placeholder="Fairoaks, CA" style="height:70px;"></textarea>

        <div class="button-wrapper-lonely">
            <input type="submit" id="tip-button" class="button green" value="Send Request!"
                   style="width:100%;"
                   onclick="this.disabled=true;this.value='Sending request! please wait...';this.form.submit();"></div>
    </form>
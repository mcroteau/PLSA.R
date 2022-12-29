<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div style="margin-top:20px;">

    <plsar:if spec="${message != ''}">
        <p class="notify">${message}</p>
    </plsar:if>

    <p>Are you Employed and are receiving tips? Signin to view your tips and to update your
    profile.</p>

    <form action="/authenticate" method="post" >

        <div class="form-group">
            <label for="phone">Email</label>
            <input type="text" name="email" class="form-control" placeholder="" value=""  style="width:100%;">
        </div>

        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" name="password" class="form-control" id="password" style="width:100%;" value=""  placeholder="&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;">
        </div>

        <div style="text-align:right; margin:30px 0px;">
            <input type="submit" class="button retro" value="Signin!" style="width:100%;">
        </div>

    </form>

</div>


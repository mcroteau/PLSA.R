<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="signup-form-container">


    <plsar:if spec="${message != ''}">
        <p class="notify">${message}</p>
    </plsar:if>

    <p style="text-align: left; margin-top:30px;">Are you already a member?
        <a href="/signin" class="href-dotted-black">Signin!</a>
    </p>

    <h1 style="margin-top:60px;">Signup</h1>
    <p>Yes, I want to start receiving <strong>tips!</strong></p>


    <form action="/register" method="post" id="form">
        <label>Town/City</label>
        <select name="townId" id="town" style="width:100%;">
            <option>Select a Town/City</option>
            <plsar:foreach items="${towns}" var="town">
                <c:set var="selected" value=""/>
                <plsar:if spec="${town.id == townId}">
                    <c:set var="selected" value="selected"/>
                </plsar:if>
                <option value="${town.id}" ${selected}>${town.name}</option>
            </plsar:foreach>
        </select><br/>

        <plsar:if spec="${businesses.size() > 0}">
            <label>Place I Work</label>
            <select name="businessId" id="business" style="width:100%;text-align:center">
                <option>Select One</option>
                <plsar:foreach items="${businesses}" var="business">
                    <option value="${business.id}"
                            data-lat="${business.latitude}"
                            data-lon="${business.longitude}">${business.address}</option>
                </plsar:foreach>
            </select><br/>

            <label>Email</label>
            <input type="text" name="email" style="width:100%;"/><br/>

            <label>Password</label>
            <input type="password" name="password" style="width:80%;"/><br/>

        </plsar:if>

        <div class="button-wrapper" style="margin: 30px 0px;">
            <input type="submit" class="button retro" value="Sign Up!"
                   style="width:100%;"
                   onclick="this.disabled=true;this.value='Registering..';this.form.submit();">
        </div>
    </form>

</div>

<script>
    $(document).ready(function(){
        let $town = $('#town');
        let $business = $('#business');

        // $town.select2();
        // $business.select2();

        $town.change(function(){
            const id = $town.val();
            const uri = '/signup?z=' + id + "#submit";
            window.location.href = uri;
        })

        let $frm = $('#form'),
            $submit = $('#submit');

        $submit.click(function(){
            $submit.attr('disabled', 'disabled')
            $submit.val('Registering..')
            $frm.submit();
            console.log($frm);
        })
    });
</script>

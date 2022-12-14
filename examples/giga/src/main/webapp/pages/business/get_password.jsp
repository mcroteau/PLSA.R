
<%@ page import="giga.Giga" %>

${siteService.getBaseBit(Kilo.HEAD, design, business, request)}

<div class="section-wrapper">
    <div class="section">
        <plsar:if spec="${message != ''}">
            <div class="notify">${message}</div>
        </plsar:if>

        <h1>Reset Password</h1>

        <p>Enter cell phone that is registered with your account.</p>

        <form action="/${business.uri}/users/password/send" method="post">
            <fieldset>

                <label for="phone">Cell Phone</label>
                <span class="tiny">The phone associated with your account!</span>
                <input id="phone" type="text" placeholder="" name="phone" style="width:60%;">

                <div class="align-right" style="margin:20px 0px 170px; text-align:right;">
                    <input type="submit" class="button green" id="reset-password" value="Get Password"
                           onclick="this.disabled=true;this.value='Sending password to your phone...';this.form.submit();"/>
                </div>
            </fieldset>
        </form>
    </div>
</div>

${siteService.getBaseBit(Kilo.BOTTOM, design, business, request)}



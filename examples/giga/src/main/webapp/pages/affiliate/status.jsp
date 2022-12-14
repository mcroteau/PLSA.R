

<style>
    #status-wrapper{
        margin:0px 0px 170px;
    }
    .status{
        text-align: center;
        font-size: 17px;
        padding:7px 10px;
        border-radius: 3px;
    }
</style>

<div class="section-wrapper">

    <div class="section">
        <h1>${business.name} <br/>
            Business Partner
            <br/>Request Status:</h1>

        <div id="status-wrapper">

            <plsar:if spec="${!businessRequest.approved && !businessRequest.denied}">
                <p class="status retro">Pending</p>
                <p>You may be a business owner before you know it. Stay tuned. If you
                need to contact the Business owner, here is : ${user.username}</p>
            </plsar:if>

            <plsar:if spec="${businessRequest.approved}">
                <p class="status green">Congratulations! Request Approved!</p>

                <p>Congratulations! You have the approval of one of your favorite business
                    owners to continue with store setup. So, let's continue...</p>
                <form action="/affiliate/setup/${businessRequest.id}" method="post">
                    <input type="submit" value="Start Business Now!" class="button retro" onclick="this.disabled=true;this.value='Please wait...';this.form.submit();"/>
                </form>
            </plsar:if>


            <plsar:if spec="${businessRequest.denied}">
                <p class="status remove">We are sorry.</p>
                <p>We are sorry, but the business owner has decided to do
                    pass for now. You can always try again at a different time,
                    maybe things will be different.</p>
                <p>Don't get discouraged, persistence and being at your best
                    will pay off! We thank you! Best.</p>

                <p>${user.username} is ${business.name}'s contact email. </p>
            </plsar:if>
        </div>
    </div>
</div>

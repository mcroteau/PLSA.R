<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
    h1{
        margin:0px 0px 10px ;
        line-height: 1.0em;
    }
    .duration{
        font-size:17px;
        padding:23px 26px;
        text-transform: none;
        font-family: Roboto !important;
        box-shadow: none !important;
    }
    .option{
        color:#000;
        border-radius: 50px !important;
        display: inline-block;
        vertical-align: middle;
        font-size:17px;
        padding:23px 26px;
        box-shadow: none !important;
        background-color: #f0f4f5;
        border:solid 1px #C9DCDC;
        font-family: Roboto !important;
    }
    .option:hover,
    .duration:hover{
        color:#000;
    }
    .duration{
        color:#000;
        background-color: #f0f4f5;
        border:solid 1px #C9DCDC;
    }
    #make-donation-container{
        text-align: left;
    }
    #custom,
    #custom:hover{
        display: inline-block;
        box-shadow: none !important;
        text-align: center;
        text-transform: none;
        width:170px !important;
    }
    label{margin-top:4px;}
    #tip-button{
        font-weight:700;
        font-size:39px;
        width:100%;
        background-color: #0f4e16;
        border:solid 1px #0f4e16;
        background:#5FC66B;
        border:solid 1px #5FC66B;
        background:#3979E4;
        border:solid 1px #3979E4;

        text-transform: none !important;
    }
    #donation-durations{margin:20px 0px 10px;}
    .button.active{
        background:#5FC66B;
        background:#3979E4;
        border:solid 1px #3979E4;
        background:#5FC66B;
        border:solid 1px #5FC66B;
        background: #0E9855;
        border:solid 1px #0E9855;
        color:#000;
        color:#fff;
        border:none;
    }
    .cc-details{
        float:left;
        margin-right:20px;
    }
    .cc-details input[type="text"]{
        text-align: center;
    }
    #exp-month{
        width:90px;
    }
    #exp-year{
        width:120px;
    }
    #cvc{
        width:110px;
    }
</style>

    <plsar:if spec="${not empty message}">
        <p class="notify">${message}</p>
    </plsar:if>

    <plsar:if spec="${recipient.imageUri != ''}">
        <img src="${recipient.imageUri}" style="border-radius: 101px;border:solid 1px #deeaea;padding:7px;margin:20px 0px 10px;width:130px;"/>
    </plsar:if>

    <h1 style="font-size:90px">${recipient.name}!</h1>


    <plsar:if spec="${recipient.stripeAccountId == ''}">
        <p class="notify">This person has not finished the registration process.
            Be patient, they may be new.</p>
    </plsar:if>

    <plsar:if spec="${recipient.stripeAccountId != ''}">

         <form action="/tip/${recipient.id}" method="post">

             <input type="hidden" name="amount" id="amount-input" value=""/>
             <input type="hidden" name="recurring" id="recurring-input" value="false"/>
             <input type="hidden" name="recipientId" value="${recipient.id}" id="recipient-id"/>

            <div id="donation-options" class="live">
                <div id="donation-durations">
                    <a href="javascript:"  class="button active serious bubble duration" id="once">Tip Em' Once</a>
                    <a href="javascript:"  class="button serious bubble duration" data-recurring="true">Tip Em' Monthly</a>
                </div>

                <a href="javascript:" class="option button active" id="three" data-amount="3">$3</a>&nbsp;
                <a href="javascript:" class="option button" data-amount="5">$5</a>&nbsp;
                <a href="javascript:" class="option button" data-amount="10">$10</a>&nbsp;
                <span style="display: inline-block;margin-right:4px;font-size:19px;">$</span><input type="text" class="option button" id="custom" placeholder="Other Amount" style="width:150px;" data-amount="0"/>
            </div>


            <div id="make-donation-container" class="live">

                <label style="margin-top:20px;">credit card information</label>
                <span class="tiny">No spaces or special characters.</span>
                <input type="number" id="credit-card" name="creditCard" placeholder="4242424242424242" maxlength="16" style="width:100%;" value=""/>

                <br class="clear"/>
                <div class="cc-details">
                    <label>month</label>
                    <input type="number" id="exp-month"name="expMonth" placeholder="12" maxlength="2" value=""/>
                </div>

                <div class="cc-details">
                    <label>year</label>
                    <input type="number" id="exp-year" name="expYear" placeholder="2029" maxlength="4" value=""/>
                </div>

                <div class="cc-details">
                    <label>cvc</label>
                    <input type="number" id="cvc" name="cvc" placeholder="123" maxlength="3" value=""/>
                </div>

                <br class="clear"/>

                <label>Email</label>
                <span class="tiny" style="display: block">Email to use for all Tips!</span>
                <input type="text" name="email" id="email" value="" placeholder="mike@getongrazie.com" style="width:100%;"/>


                <div style="text-align: center;margin:40px 0px;">
                    <p class="tiny" style="font-size:19px;">All tips are final! Don't be cruel, to a heart thats true.</p>
                    <input type="submit" id="tip-button" class="button super green amount" value="" onclick="this.disabled=true;this.value='Sending tip! please wait...';this.form.submit();">
                </div>

            </div>

        </form>

        <script>
            $(document).ready(function() {

                let processingDonation = false,
                    recurring = false;

                let $amount = $('.amount'),
                    $amountInput = $('#amount-input'),
                    $options = $('.option'),
                    $durations = $('.duration'),
                    $donateButton = $('#donate-button'),
                    $donationOptions = $('#donation-options'),
                    $makeDonationContainer = $('#make-donation-container'),
                    $custom = $('#custom'),
                    $three = $('#three'),
                    $success = $('#success'),
                    $contributionType = $('#contribution-type'),
                    $recurringInput = $('#recurring-input'),
                    $once = $('#once');

                let $recipientId = $('#recipient-id'),
                    $creditCard = $('#credit-card'),
                    $expMonth = $('#exp-month'),
                    $expYear = $('#exp-year'),
                    $cvc = $('#cvc'),
                    $email = $('#email'),
                    $processing = $("#processing");

                $custom.focus(function(){
                    $custom.val('');
                    $custom.addClass('active');
                })

                const name ="${recipient.name}";

                $custom.mouseleave(function(){
                    if($custom.val() == ''){
                        $custom.attr('placeholder', 'Other Amount');
                    }
                    if($custom.val() != ''){
                        $amount.val('Send $' + value + ' tip');
                        $amountInput.val($custom.val())
                    }
                });

                $custom.change(function(){
                    var value = $custom.val()
                    if(!isNaN(value)){
                        $amount.val('Send $' + value + ' tip');
                        $amountInput.val(value)
                    }else{
                        alert('Please enter a valid amount');
                    }
                });

                $durations.click(function(){
                    $durations.removeClass('active')
                    $(this).addClass('active').removeClass('sky')
                    if($(this).attr('data-recurring') == 'true'){
                        $recurringInput.val(true);
                    }else{
                        $recurringInput.val(false);
                    }
                });

                $options.click(function (evnt) {
                    var $target = $(evnt.target)
                    if(!$target.hasClass('super')) {

                        $options.removeClass('active')
                        $target.toggleClass('active')
                        var amount = $target.attr('data-amount')

                        if(amount != '' &&
                            amount != 0 &&
                            !isNaN(amount)) {
                            $amount.val('Send $' + amount + ' tip');
                            $amountInput.val(amount)
                        }
                    }
                })

                $once.click();
                $three.click();

            });

        </script>

    </plsar:if>

<div style="margin-top:300px;"></div>

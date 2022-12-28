<html>
<head>
    <title>Grazie! : ${title}</title>

    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="icon" type="image/png" href="/assets/media/icon.png">
    <link rel="stylesheet" href="/assets/css/packages/grid.css">
    <link rel="stylesheet" href="/assets/css/default.css">
    <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />

    <script type="text/javascript" src="/assets/js/packages/jquery.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>

    <style>
        @import url('https://fonts.googleapis.com/css2?family=Dongle&display=swap');
    </style>

</head>
<body>
<style>

    #select2-town-container{
        color:#000;
        border:none !important;
        background:#f0f4f5;
    }
    #select2-business-container{
        color:#000;
        border:none !important;
        background:#f0f4f5;
    }
    .select2-container--default,
    .select2-selection--single{border:none !important}
    .select2-container--default{
        font-size:18px;
        padding:23px 26px;
        background: #f0f4f5;
        border:solid 1px #C9DCDC !important;
        -webkit-border-radius: 60px !important;
        -moz-border-radius: 60px !important;
        border-radius: 60px !important;
        font-family: Roboto !important;
        text-align:center;
    }
    .select2-container--open{margin-top:-1px;}
    .select2-search__field{font-size:29px;font-weight:300;}
    .select2-selection__arrow{
        margin-top:20px;
    }
    .select2-results__option{text-align:left}

    .container{box-shadow: none;}
    .logo-wrapper{text-decoration: none;}
    .logo-wrapper h1{font-family:Dongle !important;line-height: 1.0em;margin:0px;font-size:60px;}
    #tagline{z-index:3;font-size:19px;margin:-40px 0px 40px;display:block;}
</style>

<div class="container">
    <div class="row">
        <div class="col-sm-12">
            <div id="guest-wrapper" style="margin:0px 30px;">
                <a href="/home" class="logo-wrapper left-float" style="display:block;margin:0px 0px 0px;">
                    <h1 class="logo" style="color:#000">
                        Grazie
                        <span style="font-family: Dongle !important;
                                    transform: rotate(9deg);
                                    display:inline-block;
                                    font-size:90px;
                                    margin-left:-10px;">!</span></span>
                        <span class="lightf" id="tagline">
                            Because the guy at <br/>McDonald's works hard!</span>
                    </h1>
                </a>

                <plsar:guest>
                    <a href="/signup" class="href-dotted-black right-float"
                       style="margin-left:15px;margin-top:20px;display:inline-block">Signup!</a>
                    <a href="/signin" class="href-dotted-black right-float"
                            style="margin-left:15px;margin-top:20px;display:inline-block">Signin</a>
                </plsar:guest>

                <br class="clear"/>
                <plsar:authenticated>
                    <div style="margin:0px 0px 30px">
                        <a href="/snapshot" class="href-dotted-black">Snapshot</a>&nbsp;&nbsp;
                        <a href="/employment" class="href-dotted-black">Employment</a>&nbsp;&nbsp;
                        <a href="/users/edit/${sessionScope.userId}" class="href-dotted-black">Profile</a>&nbsp;&nbsp;
                    </div>
                </plsar:authenticated>

                <plsar:content/>

                <plsar:authenticated>
                    <div class="button-wrapper-lonely">
                        <a href="/signout" class="href-dotted-black">Signout</a>
                    </div>
                </plsar:authenticated>
            </div>
        </div>
    </div>
</div>
<script async src="https://www.googletagmanager.com/gtag/js?id=G-3XNSCJKFST"></script>
<script>
    window.dataLayer = window.dataLayer || [];
    function gtag(){dataLayer.push(arguments);}
    gtag('js', new Date());

    gtag('config', 'G-3XNSCJKFST');
</script>
</body>
</html>
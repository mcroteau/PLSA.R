<html>
<head>
    <title>${title}</title>

    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="icon" type="image/png" href="/assets/media/icon.png">
    <link rel="stylesheet" href="/assets/css/default.css">

    <script type="text/javascript" src="/assets/js/packages/jquery.js"></script>
    <script type="text/javascript" src="/assets/js/packages/dygraphs.js"></script>

    <link rel="stylesheet" href="/assets/js/packages/dygraph.css">

    <style>
        html{margin-bottom:170px;}
        #header-wrapper{
            padding:0px 0px;
            border-top-left-radius: 9px;
            border-top-right-radius: 9px;
        }
        #header-identity{
            z-index: 0;
            height:300px;
            top:0px;
            left:0px;
            right:0px;
            position: fixed;
            background: linear-gradient(65deg, rgba(57,120,227,1) 0%, rgba(56,119,225,1) 30%, rgba(126,217,251,1) 30%, rgba(126,217,251,1) 41%, rgba(254,245,122,1) 41%, rgba(254,245,122,1) 54%, rgba(240,231,113,1) 54%, rgba(246,241,173,1) 60%, rgba(255,255,255,1) 60%, rgba(255,255,255,1) 80%, rgba(255,129,122,1) 80%);
        }
        .container{
            top:20px;
            left:10px;
            right:10px;
            padding:0px !important;
            position: absolute;
            border-radius: 9px;
        }

    </style>
</head>
<body>

<div id="header-identity"></div>
<div class="container">

    <style>
        #logo-wrapper{text-decoration: none;float: right;display:block;text-align: center;padding-right:7px;}
        #polygon-icon{height:40px; width:40px;margin-top:7px;}
        #tagline{font-size:10px;}

        #header-wrapper{border-bottom: dotted 1px #d0dde3;}
        #header-menu{padding:0px 0px;}
        #header-menu li{float:left;}
        #header-menu li a{display:block;text-decoration: none; font-size:14px; padding:34px 20px;background-color: #fff; border-right:dotted 1px #d0dde3;text-transform: uppercase;}
        #header-menu li a:hover, #header-menu li a.active{color:#fff;background-color: #000; border-right:dotted 1px #222227;}
        #menu-href-first{border-top-left-radius: 9px;}
        .sales-activities{background-color:#fff;border-bottom: dotted 1px #d0dde3;}
    </style>

    <div id="header-wrapper">
        <div id="header-menu-wrapper">
            <ul id="header-menu">
                <li><a href="/snapshot" id="menu-href-first" class="${snapshotHref}">Snapshot</a></li>
                <li><a href="/prospects" class="${searchHref}">Search</a></li>
                <li><a href="/prospects/create" class="${createHref}">Create</a></li>
            </ul>
        </div>

        <a href="/prospects" id="logo-wrapper">
            <div class="logo-inner-wrapper">
                <svg version="1.2" baseProfile="tiny-ps" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 189 182" width="189" height="182" id="polygon-icon">
                    <style>
                        .s0 { fill: #4aadd3 }
                        .s1 { fill: #7dd8fb }
                        .s2 { fill: #e4da53 }
                        .s3 { fill: #fdf47a }
                        .s4 { fill: #1e5ec9 }
                        .s5 { fill: #3877e1 }
                        .s6 { fill: #e1534b }
                        .s7 { fill: #f77068 }
                        .s8 { fill: #0d9c57 }
                        .s9 { fill: #20b66d }
                    </style>
                    <g id="Folder 2 copy 2">
                        <path id="Shape 13 copy 10" class="s0" d="m126.97 129h-40.52l0-40.52l40.52 40.52z" />
                        <path id="Shape 13 copy 9" class="s1" d="m86.45 88.48h40.52v40.52l-40.52-40.52z" />
                        <path id="Shape 13 copy 13" class="s2" d="m126.97 89h-40.52l0-40.52l40.52 40.52z" />
                        <path id="Shape 13 copy 14" class="s3" d="m86.45 48.48h40.52v40.52l-40.52-40.52z" />
                        <path id="Shape 13 copy 17" class="s4" d="m66.97 49h-40.52l0-40.52l40.52 40.52z" />
                        <path id="Shape 13 copy 18" class="s5" d="m26.45 8.48h40.52v40.52l-40.52-40.52z" />
                        <path id="Shape 13 copy 15" class="s6" d="m167.97 109h-40.52l0-40.52l40.52 40.52z" />
                        <path id="Shape 13 copy 16" class="s7" d="m127.45 68.48h40.52v40.52l-40.52-40.52z" />
                        <path id="Shape 13 copy 11" class="s8" d="m86.97 169h-40.52l0-40.52l40.52 40.52z" />
                        <path id="Shape 13 copy 12" class="s9" d="m46.45 128.48h40.52v40.52l-40.52-40.52z" />
                    </g>
                </svg>
                <h1 class="logo" style="margin:0px; line-height:1.0em; font-size:19px;">Polygon</h1>
                <span class="lightf" id="tagline">Open Source CRM</span>
            </div>
        </a>

        <br class="clear"/>

    </div>

    <style>
        .value{font-weight: 900;font-size:32px;}
        .stat-wrapper{width:20%;float:left; padding:10px 30px;}
        .stat-wrapper{width:20%;float:left; padding:10px 30px;}
    </style>


    <div class="geb-sig-left">
        <span class="uno color"></span>
        <span class="dos color"></span>
        <span class="tres color"></span>
        <span class="quatro color"></span>
        <br class="clear"/>
    </div>
    <div class="geb-sig-left">
        <span class="cinco color"></span>
        <span class="seies color"></span>
        <span class="siete color"></span>
        <br class="clear"/>
    </div>

    <c:if test="${prospectActivities.size() > 0}">
        <div class="sales-activities">
            <c:forEach items="${prospectActivities}" var="prospectActivity">
                <a href="/prospects/${prospectActivity.prospectId}" class="sales-activity">
                    <span class="activity-date"><strong>${prospectActivity.prettyTime}</strong> : ${prospectActivity.name}</span>
                    <span class="activity-prospect">${prospectActivity.prospectName}</span>
                </a>
            </c:forEach>
            <br class="clear"/>
        </div>
    </c:if>



<%--    <div id="header-wrapper">--%>
<%--        <a href="/prospects" class="button modern">+ Search</a>--%>
<%--        <div id="welcome">Hi <a href="/users/edit/${sessionScope.userId}">${sessionScope.name}</a>!</div>--%>
<%--        <br class="clear"/>--%>
<%--    </div>--%>



    <plsar:content/>

    <br class="clear"/>

    <div class="button-wrapper inside-container" style="margin-top:60px;">
        <a href="/users" class="href-dotted-black ${usersHref}">Users</a>
    </div>

</div>
<br class="clear"/>
<div class="button-wrapper-lonely">2022 &copy; Polygon</div>

<script>
    $(document).ready(function(){
        let $activities = $('.sales-activities');
        let upwards = true;
        let interval = setInterval(function(){
            if(upwards) {
                $activities.scrollTop($activities.height());
            }
            if(!upwards) {
                $activities.scrollTop(1);
            }
            upwards = !upwards
        }, 6100);


        $.ajax({
            url : "/data",
            success: function(data){
                console.log(data);
                g = new Dygraph(
                    document.getElementById("graph"),
                    data,
                    {
                        legend: 'always',
                        titleHeight: 32,
                        ylabel: '# of Sales',
                        xlabel: 'Date',
                        strokeWidth: 5.0,
                    }
                );
            }
        })


    })
</script>

</body>
</html>
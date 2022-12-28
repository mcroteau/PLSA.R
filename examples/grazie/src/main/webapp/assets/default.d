@import url('https://fonts.googleapis.com/css2?family=Roboto+Slab:wght@100;200;300;400;500;600;700;800;900&family=Roboto:wght@100;300;400;500;700;900&display=swap');

/* http://meyerweb.com/eric/tools/css/reset/
   v2.0 | 20110126
   License: none (public domain)
*/

html, body, div, span, applet, object, iframe,
h1, h2, h3, h4, h5, h6, p, blockquote, pre,
a, abbr, acronym, address, big, cite, code,
del, dfn, em, img, ins, kbd, q, s, samp,
small, strike, strong, sub, sup, tt, var,
b, u, i, center,
dl, dt, dd, ol, ul, li,
fieldset, form, label, legend,
table, caption, tbody, tfoot, thead, tr, th, td,
article, aside, canvas, details, embed,
figure, figcaption, footer, header, hgroup,
menu, nav, output, ruby, section, summary,
time, mark, audio, video {
    margin: 0;
    padding: 0;
    border: 0;
    font-size: 100%;
    font: inherit;
    vertical-align: baseline;
    font-family: Roboto !important;
}
/* HTML5 display-role reset for older browsers */
article, aside, details, figcaption, figure,
footer, header, hgroup, menu, nav, section {
    display: block;
}
body {
    line-height: 1;
}
ol, ul {
    list-style: none;
}
blockquote, q {
    quotes: none;
}
blockquote:before, blockquote:after,
q:before, q:after {
    content: '';
    content: none;
}
table {
    border-collapse: collapse;
    border-spacing: 0;
}


html, body,
div, span, p, blockquote,
h1, h2, h3, h4,
input, select, textarea,
input::placeholder,
textarea::placeholder, th, td{
    vertical-align: middle;
    font-family: Roboto !important;
    color:#000;
}
td{
    padding:10px 0px 10px 0px;
}

p, span{
    font-family: Roboto !important;
    line-height: 1.3em;
}

/*csslint box-model:false*/
/*
Box-model set to false because we're setting a height on select elements, which
also have border and padding. This is done because some browsers don't render
the padding. We explicitly set the box-model for select elements to border-box,
so we can ignore the csslint warning.
*/

h1{
    color:#000;
    font-size:37px;
    margin:11px 0px 0px;
    line-height:1.3em;
    font-family:Roboto !important;
    font-weight:900;
}

h2{
    font-size:32px;
    font-family:Roboto !important;
    font-weight:900;
}

h3{
    color:#000;
    font-size:26px;
    font-family:Roboto !important;
    font-weight:900;
    line-height:1.17;
    margin:10px 0px;
}

p{
    margin:10px 0px;
    font-size:23px;
}

textarea:focus,
textarea.typing {
    outline:none;
}

textarea::-webkit-input-placeholder { /* Chrome/Opera/Safari */
    font-family:Roboto !important;
    font-weight: 900;
    color:#ccc;
    font-size:21px;
    text-shadow: 0px 0px #fff;
}
textarea::-moz-placeholder { /* Firefox 19+ */
    font-family:Roboto !important;
    font-weight: 900;
    color:#ccc;
    text-shadow: 0px 0px #fff;
}
textarea:-ms-input-placeholder { /* IE 10+ */
    font-family:Roboto !important;
    font-weight: 900;
    color:#ccc;
    text-shadow: 0px 0px #fff;
}
textarea:-moz-placeholder { /* Firefox 18- */
    font-family:Roboto !important;
    font-weight: 900;
    color:#ccc;
    text-shadow: 0px 0px #fff;
}


input[type="text"]::-webkit-input-placeholder,
input[type="email"]::-webkit-input-placeholder,
input[type="password"]::-webkit-input-placeholder { /* Edge */
    color: #ccc;
    font-family:Roboto !important;
    font-weight: 900;
}

input[type="text"]:-ms-input-placeholder { /* Internet Explorer 10-11 */
    color: #ccc;
    font-family:Roboto !important;
    font-weight: 900;
}

input[type="text"]::placeholder,
input[type="email"]::placeholder,
input[type="password"]::placeholder {
    color: #ccc;
    font-family:Roboto !important;
    font-weight: 900;
}

input[type="file"]{
    cursor:pointer;
    cursor:hand;
    z-index:1;
}


.button,
input[type="button"].button,
input[type="reset"].button,
input[type="submit"].button{
    cursor:pointer;
    color:#fff;
    font-size:13px;
    padding: 22px 21px;
    line-height: 1.0em;
    display:inline-block;
    background:#00b1ff;
    border:solid 1px #00b1ff;
    text-decoration:none;
    border-radius: 4px;
    -moz-border-radius: 4px;
    -webkit-border-radius: 4px;
    font-family:Roboto !important;
    outline:none !important;
    -webkit-appearance: none;
    -moz-appearance:none;
}

.button:active{
    -webkit-transition: 3s;
    -moz-transition: 3s;
    -ms-transition: 3s;
    -o-transition: 3s;
    transition: 3s;
    -webkit-box-shadow: 0px 1px 7px 0px rgba(179,179,179,0.43);
    -moz-box-shadow: 0px 1px 7px 0px rgba(179,179,179,0.43);
    box-shadow: 0px 1px 7px 0px rgba(179,179,179,0.43);
}

.button:hover{
    color:#fff;
}


.button.small,
input[type="button"].button.small,
input[type="reset"].button.small,
input[type="submit"].button.small{
    font-size:11px;
    padding:15px 23px;
}

.button.remove,
input[type="button"].remove,
input[type="reset"].remove,
input[type="submit"].remove{
    background:#e24133;
    border:solid 1px #e24133;
}

.yellow,
.button.yellow,
input[type="button"].button.yellow,
input[type="reset"].button.yellow,
input[type="submit"].button.yellow{
    color:#000;
    background:#f8f90c;
    border:solid 1px #f8f90c;
}


.button.sky,
input[type="button"].button.sky,
input[type="reset"].button.sky,
input[type="submit"].button.sky,
input[type="button"].sky{
    color:#000;
    background:#F3F3F7;
    border:solid 1px #F3F3F7;
}


.button.light,
input[type="button"].button.light,
input[type="reset"].button.light,
input[type="submit"].button.light,
input[type="button"].button.light{
    color:#000;
    background:#fff;
    border:solid 1px #e6e8ea;
}


.modern,
.button.modern,
input[type="button"].button.modern,
input[type="reset"].button.modern,
input[type="submit"].button.modern{
    background:#222227;
    border:solid 1px #222227;
}

.button.purple,
input[type="button"].button.purple,
input[type="reset"].button.purple,
input[type="submit"].button.purple{
    background:#6C4EF7;
    border:solid 1px #6C4EF7;
}

.notify{
    font-size:17px;
    margin-bottom:23px;
    padding:10px 20px;
    border:solid 1px #617078;
    background:#fff;
    border-radius: 3px;
    -moz-border-radius: 3px;
    -webkit-border-radius: 3px;
}

.tiny{
    font-size:11px;
    font-family:Roboto !important;
}

.href-dotted.tiny{
    font-size:15px;
}

.button.small,
input[type="button"].button.small,
input[type="reset"].button.small,
input[type="submit"].button.small{
    padding:15px 19px !important;
}

.button.small,
input[type="button"].button.small,
input[type="reset"].button.small,
input[type="submit"].button.small{
    font-size: 10px;
    padding: 10px 16px !important;
}

.button.tiny{
    padding: 2px 3px !important;
}

.button.bare,
input[type="button"].button.bare,
input[type="reset"].button.bare,
input[type="submit"].button.bare{
    color:#000;
    border:solid 2px #000;
    background: #fff;
    -webkit-box-shadow: 0px 3px 5px 0px rgba(179,179,179,0.43) !important;
    -moz-box-shadow: 0px 3px 5px 0px rgba(179,179,179,0.43) !important;
    box-shadow: 0px 3px 5px 0px rgba(179,179,179,0.43) !important;
}

strong{
    font-family:Roboto !important;
    font-weight: 900;
}

.left-float{
    float:left;
}

.right-float{
    float:right;
}

.tiny,
.information,
.instructions{
    font-size:15px;
    font-weight:300;
}

.tiny-tiny{
    font-size:10px;
}

label{
    font-size:23px;
    margin:20px 0px;
    line-height:1.62em;
}

.href-dotted,
.href-dotted-black{
    font-size:23px;
    font-weight: bold;
}

.href-dotted{
    text-decoration: none !important;
    border-bottom:dotted 2px #4889f6 !important;
}

.href-dotted-black{
    color: #17161b !important;
    text-decoration: none !important;
    border-bottom:dotted 2px #17161b !important;
}

.fun{
    font-weight: 900;
}

.bold{
    font-weight: 700;
}

.medium{
    font-weight: 500;
}

.regular{
    font-weight: 400;
}

.lightf{
    font-weight: 300;
}

.thin{
    font-weight: 200;
}


a{
    font-size:23px;
    color:#4889f6;
}

a:hover{
    cursor:hand;
}


input[type="text"],
input[type="date"],
input[type="email"],
input[type="number"],
input[type="password"],
input[type="text"]:hover,
input[type="date"]:hover,
input[type="email"]:hover,
input[type="number"]:hover,
input[type="password"]:hover,
input[type="text"]:focus,
input[type="date"]:focus,
input[type="email"]:focus,
input[type="number"]:focus,
input[type="password"]:focus{
    color:#000;
    font-family:Roboto !important;
    font-weight: 900;
    font-size:29px;
    background:#f8f8f8;
    line-height:1.0em !important;
    padding:12px 12px !important;
    border:solid 1px #C9DCDC !important;
    -webkit-border-radius: 3px !important;
    -moz-border-radius: 3px !important;
    border-radius: 3px !important;
}

input::placeholder{
    color:#ccc;
    font-size:29px;
}

textarea{
    color:#000;
    font-family:Roboto-Slab !important;
    font-weight: 300;
    font-size:21px;
    line-height:1.0em !important;
    padding:12px 12px !important;
    background:#fff;
    border:solid 1px #C9DCDC;
    -webkit-border-radius: 3px !important;
    -moz-border-radius: 3px !important;
    border-radius: 3px !important;
}

input:-webkit-autofill,
input:-webkit-autofill:hover,
input:-webkit-autofill:focus,
textarea:-webkit-autofill,
textarea:-webkit-autofill:hover,
textarea:-webkit-autofill:focus,
select:-webkit-autofill,
select:-webkit-autofill:hover,
select:-webkit-autofill:focus {
    border:solid 1px #C9DCDC !important;
    -webkit-text-fill-color: #000;
    -webkit-box-shadow: 0 0 0px 1000px #fff inset;
    transition: background-color 5000s ease-in-out 0s;
}

input[type="checkbox"]{
    height:30px;
    width:30px;
    background: #C9DCDC !important;
}

textarea{
    color:#000;
    font-size:27px;
    font-family: Roboto !important;
    font-weight: 900;
    width:100%;
    height:170px;
    line-height:1.0em !important;
    padding:12px 12px !important;
    background:#f0f4f5;
    border:solid 1px #e4e8e9;
    -webkit-border-radius: 8px !important;
    -moz-border-radius: 8px !important;
    border-radius: 8px !important;
}


select{
    font-size:27px;
    padding:12px 12px !important;
    background: #f0f4f5;
    border:solid 1px #C9DCDC;
    -webkit-border-radius: 8px !important;
    -moz-border-radius: 8px !important;
    border-radius: 8px !important;
    font-family: Roboto !important;
    font-weight: 900;
}

.no-effects{
    font-size:23px;
    font-weight: bold;
    color: #4889f6;
    text-decoration: none !important;
    border-bottom:dotted 2px #4889f6 !important;
    background:#fff;
    border:none;
    padding:0px;
    line-height:1.2em;
    display:inline-block;
}

.blue{
    color:#4889f6;
}

.clear{
    clear:both;
}

body{
    padding:0px !important;
    background: #fff;
}
.container{
    background: #ffffff;
    margin-left:auto;
    margin-right:auto;
    /*margin-top:10px;*/
    /*width:98%;*/
    padding:0px;
    padding-bottom:100px;
drop-shadow(0px 6px 40px black)
margin-top:20px;
}
@media screen and (min-width: 690px) {
    .inside-container{
        padding:21px 20px !important;
    }
}
@media screen and (max-width: 690px) {
    .inside-container{
        padding:21px 20px !important;
    }
}
h1.inside-header{
    margin-top:29px;
}
#header-wrapper{
    width:100%;
    border-bottom:solid 1px #e6e8ea;
}

.prospects-href{
    font-size:16px;
    font-weight: 900;
    padding:13px 17px;
    background: #fff;
    text-decoration: none;
    float:left;
    display: inline-block;
}

.create-prospect{
    color: #fff;
    background: #222227;
    border-right:solid 1px #000;
    background: #00B1FF;
    border-right:solid 1px #00B1FF;
}

.search-prospects{
    color:#fff;
    /*background: #4889f6;*/
    background: #000;
    border-right:solid 1px #e6e8ea;
}

.stats-href{
    color:#fff;
    background:#fffa00;
    background: #f3b607;
}

#welcome{
    float:right;
}

#welcome,
#welcome a{
    color:#000;
    font-size:19px;
    font-weight:300;
    padding-top:10px;
    padding-right:10px;
    text-decoration: none;
}

#welcome a{
    text-decoration: none;
    border-bottom:dotted 2px #000;
}
#new-prospect-href{
    float: right;
}
#results-h1{
    float:left;
}
#results{
    width:100%;
    font-size:24px;
}
#results a{
    font-size:29px;
}

.sales-activities{
    height:71px;
    overflow-y: scroll;
    border-bottom: solid 1px #e6e8ea;
}
.sales-activities .sales-activity{
    float:left;
    padding:15px 30px;
    text-decoration: none;
    font-size:15px;
}
.sales-activities .sales-activity span {
    display:block;
}
.upcoming-activities{

}
.upcoming-activities .sales-activity{
    text-decoration: none;
}
.divider{
    border-right:solid 1px #e6e8ea;
}
.bottom-divider{
    height:2px;
    border-top: solid 1px #e6e8ea;
}
.primary{
    min-height: 231px;
}
.akki-sig-left{
    float:left;
    width:9px;
    margin-top:15px;
}
.akki-sig-left span{
    display:block;
}
.color span{
    font-size:19px;
}
.uno{
    width:60%;
    background: #000;
    background: #4889f6;
    background: #222227;
}
.dos{
    width:25%;
    background: #e24133;
    background: #fffa00;
    background: #000;
    background: #000;
}
.tres{
    width:15%;
    background: #f3b607;
    background: #000;
    background: #000;
    background: #00b1ff;
}
.quatro{
    width:8%;
    background: #fff;
    background: #fffa00;
}
.quatro span{color:#000 !important;}

.cinco{
    background: #f3b607;
    background: #4889f6;
}
.seies{
    background: #e24133;
    background: #fffa00;
    background: #222227;
}
.siete{
    background: #000;
    background: #222227;
    background: #fffa00;
}
.akki-sig{
    height:7px;
}
.color{
    float:left;
    height:100%;
}
.akki-styles .color.text{
    padding:10px 0px 21px 0px;
    text-align: center;
    text-decoration: none;
    vertical-align: middle !important;
    display: block;
}
.akki-sig .text span{
    color: #fff;
    font-weight: 900;
    font-size: 16px;
    display: block;
    vertical-align: middle;
    margin-top:20px;
}
.plus{
    margin-top:5px !important;
    font-size:30px !important;
    padding-bottom:5px;
}
.une{
    width:15px;
    width:15%;
    background:#4889f6;
    background: #222227;
}
.deux{
    width:25px;
    width:40%;
    background:#fffa00;
    background: #000;
}
.deux span{
    color:#fff !important;
}
.trois{
    width:15px;
    width:30%;
    background:#000;
    background:#e24133;
}
.quatre{
    width:15px;
    width:15%;
    background: #fff;
    background:#f3b607;
}
.cinc{
    width:15px;
    background: #4889f6;
}
.seis{
    width:15px;
    background:#fffa00;
}
.sept{
    width:15px;
    background: #222227;
}
.clear{
    clear: both;
}
.row-container{
    display: table;
    width:100%;
}
.inside-container{
    padding:21px 49px;
}
.inside-container-row{
    padding:21px 49px;
    display:table-cell;
}
#prospect-search{
    width:100%;
    color: #4281ea;
}
#search-button{
    margin-top:23px;
}
.horizontal-form{
    float:left;
}
#prospect-back{
    display:inline-block;
    margin-bottom: 30px;
    clear:both;
}
.akki-styles{
    height:73px;
}
label{
    display:block;
    margin:20px 0px 0px;
}
input[type="text"]{
}

body.obey{}

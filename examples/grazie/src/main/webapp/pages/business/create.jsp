
<plsar:if spec="${not empty message}">
    <p class="notify">${message}</p>
</plsar:if>

<h2>Add Employer</h2>
<span class="tiny">Add an employer where you may receive tips.</span>
<form action="/employment/save" method="post">

    <input type="hidden" name="userId" value="${sessionScope.userId}"/>

    <label>Town/City</label>
    <select name="townId" id="town" style="width:100%;">
        <option>Select a Town/City</option>
        <plsar:foreach items="${towns}" var="town">
            <c:set var="selected" value=""/>
            <plsar:if spec="${town.id == townId}">
                <c:set var="selected" value="selected"/>
            </plsar:if>
            <option value="${town.id}"
                    data-lat="${town.latitude}"
                    data-lon="${town.longitude}" ${selected}>${town.name}</option>
        </plsar:foreach>
    </select><br/>

    <plsar:if spec="${businesses.size() > 0}">
        <label>Business where you Work!</label>
        <select name="businessId" id="business" style="width:100%;text-align:center">
        <option>Select One</option>
        <plsar:foreach items="${businesses}" var="busy">

            <plsar:set var="selected" value=""/>
            <plsar:if spec="${busy.id == business.id}">
                <plsar:set var="selected" value="selected"/>
            </plsar:if>

            <option value="${busy.id}"
                    data-lat="${busy.latitude}"
                    data-lon="${busy.longitude}"
                ${selected}>${busy.address}</option>
        </plsar:foreach>
        </select><br/>
    </plsar:if>

    <div class="button-wrapper-lonely">
        <input type="submit" class="button green" value="Save Employment"/>
    </div>
</form>

<script>
$(document).ready(function(){
    let $town = $('#town');
    let $business = $('#business');

    $town.change(function(){
        const id = $town.val();
        const selected = $town.find(":selected");
        const lat = selected.data('lat');
        const lon = selected.data('lon');
        const coordinates = '&lat=' + lat + '&lon=' + lon;
        const uri = '/employment/create?z=' + id + coordinates + "#business";
        window.location.href = uri;
    })

    // $business.change(function(){
    //     const town = $town.val();
    //     const business = $business.val();
    //     const selected = $business.find(":selected");
    //     const lat = selected.data('lat');
    //     const lon = selected.data('lon');
    //     const coordinates = '&lat=' + lat + '&lon=' + lon;
    //     const uri = '/discover?z=' + town + '&zq=' + business + coordinates + '#business';
    //     window.location.href = uri;
    // });

});


<%--function initMap() {--%>

<%--    let uluru = { lat: parseFloat('${lat}'), lng: parseFloat('${lon}') };--%>
<%--    // The location of Uluru--%>
<%--    // The map, centered at Uluru--%>
<%--    const map = new google.maps.Map(document.getElementById("map"), {--%>
<%--        zoom: 4,--%>
<%--        center: uluru,--%>
<%--    });--%>
<%--    // The marker, positioned at Uluru--%>
<%--    const marker = new google.maps.Marker({--%>
<%--        position: uluru,--%>
<%--        map: map,--%>
<%--    });--%>
// }
</script>

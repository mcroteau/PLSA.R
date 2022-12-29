
<h1>Person Locator!</h1>
<p>Find the person you would like to tip!</p>

<plsar:if spec="${businesses.size() > 0}">
    <label>Business where the Person Worked!</label>
    <select name="businessId" id="business" style="width:100%;text-align:center">
        <option>Select One</option>
        <plsar:foreach items="${businesses}" var="busy">

            <c:set var="selected" value=""/>
            <plsar:if spec="${busy.id == business.id}">
                <c:set var="selected" value="selected"/>
            </plsar:if>

            <option value="${busy.id}"
                    data-lat="${busy.latitude}"
                    data-lon="${busy.longitude}"
                ${selected}>${busy.address}</option>
        </plsar:foreach>
    </select><br/>

    <%--    <div id="map" style="width:100%;height:300px;margin:40px 0px 0px;"></div>--%>
</plsar:if>

<h1 id="options">${business.name}</h1>

<plsar:if spec="${people.size() > 0}">
    <p>Here are the fine people who are employed at ${business.name}</p>
    <table>
        <plsar:foreach items="${people}" var="person">
            <plsar:if spec="${person.name != ''}">
                <tr>
                    <td>
                        <plsar:if spec="${person.imageUri != ''}">
                            <img src="${person.imageUri}" style="width:110px;border-radius:60px;padding:7px;border:solid 1px #deeaea"/>
                        </plsar:if>
                        <plsar:if spec="${person.imageUri == ''}">
                            <div class="image-placeholder" style="height:60px;width:60px;color:#fff;padding:20px 0px;border-radius: 62px;background:#3979E4;text-align: center;">${person.initials}</div>
                        </plsar:if>
                    </td>
                    <td>
                        <strong>${person.name}</strong><br/>
                        <plsar:if spec="${person.userBusiness.position != '' && person.userBusiness.position != 'null'}">
                            Position : ${person.userBusiness.position}<br/>
                        </plsar:if>
                        <plsar:if spec="${person.userBusiness.dateStarted != 0}">
                            <span class="tiny">Date Started : ${person.userBusiness.dateStarted}</span>
                        </plsar:if>
                        <plsar:if spec="${person.userBusiness.years != 0}">
                            <span class="tiny">Years Worked : ${person.userBusiness.years}</span><br/>
                        </plsar:if>
                        <plsar:if spec="${person.userBusiness.years == 0}">
                            <span class="tiny">Years Worked : Just Started</span><br/>
                        </plsar:if>
                        <plsar:if spec="${person.userBusiness.partTime}">
                            <span class="tiny">Part-time</span>
                        </plsar:if>
                        <plsar:if spec="${!person.userBusiness.partTime}">
                            <span class="tiny">Full-time</span>
                        </plsar:if>
                    </td>
                    <td><a href="/${person.guid}" class="button green">Send Tip $</a></td>
                </tr>
            </plsar:if>
        </plsar:foreach>
    </table>
</plsar:if>
<plsar:if spec="${people.size() == 0}">
    <p class="notify">No one signed up at that location yet. Tell them about this,
        you may help them turn a crappy job into an awesome job!</p>
</plsar:if>


<div id="map" style="width:100%;height:300px;margin:40px 0px 0px;"></div>

<label>Town/City</label>

<select name="townId" id="town" style="width:100%;">
    <option>Select a Town/City</option>
    <plsar:foreach items="${towns}" var="town">
        <plsar:set var="selected" val=""/>
        <plsar:if spec="${town.id == townId}">
            <plsar:set var="selected" val="selected"/>
        </plsar:if>
        <option value="${town.id}"
                data-lat="${town.latitude}"
                data-lon="${town.longitude}" ${selected}>${town.name}</option>
    </plsar:foreach>
</select><br/>





<script
    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCQulbBR1VkrQsKwisM1mLEyEMRUoT2GCI&callback=initMap&libraries=&v=weekly"
    async
></script>

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
        const uri = '/discover?z=' + id + coordinates;
        window.location.href = uri;
    })

    $business.change(function(){
        const town = $town.val();
        const business = $business.val();
        const selected = $business.find(":selected");
        const lat = selected.data('lat');
        const lon = selected.data('lon');
        const coordinates = '&lat=' + lat + '&lon=' + lon;
        const uri = '/discover?z=' + town + '&zq=' + business + coordinates + '#options';
        window.location.href = uri;
    });

});


function initMap() {

    let uluru = { lat: parseFloat('${lat}'), lng: parseFloat('${lon}') };
    zoom = 10;
    console.log(uluru)
    if(isNaN(uluru.lat) && isNaN(uluru.lng)){
        uluru.lat = 42.7432082;
        uluru.lng = -89.5296815;
        zoom = 2;
    }

    // The location of Uluru
    // The map, centered at Uluru
    const map = new google.maps.Map(document.getElementById("map"), {
        zoom: zoom,
        center: uluru,
    });
    // The marker, positioned at Uluru
    const marker = new google.maps.Marker({
        position: uluru,
        map: map,
    });
}
</script>

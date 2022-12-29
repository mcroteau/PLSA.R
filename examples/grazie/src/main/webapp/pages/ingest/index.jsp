<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<plsar:if spec="${message != ''}">
    <p class="notify">${message}</p>
</plsar:if>


<h2>Data Ingest</h2>
<form action="/ingest/perform" method="post">
    <label>Place</label>
    <span class="tiny"></span>
    <textarea name="place" placeholder="McDonalds" value="" style="width: 100%;"></textarea>

    <label>Town/City Name</label>
    <span class="information"></span>
    <input type="text" name="location" placeholder="" value="" style="width: 100%;"/>

    <div class="button-wrapper">
        <input type="submit" value="Perform Ingest!" class="button green" style="width: 100%;"/>
    </div>
</form>
<plsar:foreach items="${result.results}" var="place">
    <div>
        <h2>${place.name}</h2>
    </div>
</plsar:foreach>
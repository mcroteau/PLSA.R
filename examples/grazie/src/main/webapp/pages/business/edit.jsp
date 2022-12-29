<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

        <plsar:if spec="${message != ''}">
            <p class="notify">${message}</p>
        </plsar:if>

        <h1>${business.business.name}</h1>
        <h2>Edit Details</h2>

        <form action="/businesses/update" method="post">

            <input type="hidden" name="id" value="${business.id}"/>

            <label>Position</label>
            <span class="tiny">Are you a grocery clerk, waitress, bus boy or dry cleaner?</span><br/>
            <input type="text" name="position" value="${business.position}" placeholder="Grocery Clerk" style="width:70%"/>

            <label>Start Year</label>
            <span class="tiny">Please enter a valid year.</span><br/>
            <input type="number" name="dateStarted" value="${business.dateStarted}" style="width:30%;"/>

            <label>Years Worked</label>
            <span class="tiny">How many years have you worked at this fabulous institution?</span><br/>
            <input type="number" name="years" value="${business.years}" style="width:40%;"/>

            <label>Part Time</label>
            <c:set var="checked" value=""/>
            <plsar:if spec="${business.partTime}">
                <c:set var="checked" value="checked"/>
            </plsar:if>
            <input type="checkbox" name="partTime" ${checked}/>

            <div class="button-wrapper-lonely">
                <input type="submit" class="button green serious" value="Update Details"/>
            </div>
        </form>
    </div>


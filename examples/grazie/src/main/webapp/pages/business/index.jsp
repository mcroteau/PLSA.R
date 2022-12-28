<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <h2>Employment</h2>
    <a href="/employment/create" class="button retro serious bubble">Add New Employer</a>

    <table style="width:100%">
        <tr>
            <th>Business</th>
            <th>Status</th>
        </tr>
        <plsar:foreach items="${businesses}" var="business">
            <tr>
                <td>
                        ${business.name}<br/>
                    <plsar:if spec="${business.userBusiness.partTime}">
                        Part Time.<br/>
                    </plsar:if>
                    <plsar:if spec="${!business.userBusiness.partTime}">
                        Full Time! <br/>
                    </plsar:if>
                    Years worked : ${business.userBusiness.years}
                    <a href="/businesses/edit/${business.userBusiness.id}" class="href-dotted-black">Edit Info!</a>
                </td>
                <td>
                    <plsar:if spec="${business.userBusiness.active}">
                        <form action="/businesses/disable/${business.userBusiness.id}">
                            <input type="submit" value="I No Longer Work Here" class="button remove serious bubble"/>
                        </form>
                    </plsar:if>
                    <plsar:if spec="${!business.userBusiness.active}">
                        <form action="/businesses/enable/${business.userBusiness.id}">
                            <input type="submit" value="I Work Here Again!" class="button green serious bubble"/>
                        </form>
                    </plsar:if>
                </td>
            </tr>
        </plsar:foreach>
    </table>
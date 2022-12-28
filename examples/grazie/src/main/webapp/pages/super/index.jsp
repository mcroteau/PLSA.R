<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <plsar:if spec="${not empty message}">
        <p class="notify">${message}</p>
    </plsar:if>

    <h2>location requests</h2>
    <table>
        <plsar:foreach items="${requests}" var="req">
            <tr>
                <td>${req.name}<br/>
                    ${req.address}</td>
                <td>
                    <form action="/signup_request/delete/${req.id}" method="post">
                        <input type="submit" class="button remove serious bubble" value="Acknowledged & Ingested!"/>
                    </form>
                </td>
            </tr>
        </plsar:foreach>
    </table>

    <h2 style="margin-top:20px;">users</h2>
    <table>
        <plsar:foreach items="${users}" var="user">
            <tr>
                <td>${user.guid}</td>
                <td>${user.email}</td>
            </tr>
        </plsar:foreach>
    </table>
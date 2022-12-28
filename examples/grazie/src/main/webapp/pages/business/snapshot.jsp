<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
    .value.large{font-size:102px;}
</style>

<h1 style="margin:20px 0px 0px;">Snapshot!</h1>
<p><strong>${tips.size()}</strong> Tips Received!</p>

    <plsar:if spec="${tips.size() > 0}">
        <table>
            <tr>
                <th>Email</th>
                <th>Amount</th>
                <th>Monthly</th>
            </tr>
            <plsar:foreach items="${tips}" var="tip">
                <tr>
                    <td>${tip.email}</td>
                    <td style="text-align: center;"><strong>$${tip.amount}</strong></td>
                    <td style="text-align: center;">
                        <plsar:if spec="${tip.subscriptionId != '' && tip.subscriptionId != 'null'}">
                            &check;
                        </plsar:if>
                        <plsar:if spec="${!tip.subscriptionId == '' || tip.subscriptionId == 'null'}">
                            -
                        </plsar:if>
                    </td>
                </tr>
            </plsar:foreach>
        </table>
    </plsar:if>

<plsar:if spec="${tips.size() == 0}">
    <p class="notify">No tips yet, check back though and stay to it! Good luck!</p>
</plsar:if>


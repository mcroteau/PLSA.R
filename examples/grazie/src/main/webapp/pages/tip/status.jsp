<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <h1>$${tip.amount} tipped</h1>
    <h3>${tip.recipient.name}</h3>

    <p>Successfully processed your donation!</p>
    <plsar:if spec="${tip.recipient.description != 'null' || tip.recipient.description != ''}">
        <p>${tip.recipient.name} thanks you!</p>
    </plsar:if>

    <plsar:if spec="${tip.recipient.description != 'null' && tip.recipient.description != ''}">
        <h3>Here is a word from ${tip.recipient.name}</h3>
        <p style="margin:10px 0px 10px !important">"${tip.recipient.description}"</p>
    </plsar:if>
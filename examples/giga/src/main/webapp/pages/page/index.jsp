
<%@ page import="giga.Giga" %>

${siteService.getPageBit(Kilo.HEAD, page, business, request)}

<plsar:if spec="${message != ''}">
    <p class="notify">${message}</p>
</plsar:if>

${page.content}

${siteService.getPageBit(Kilo.BOTTOM, page, business, request)}

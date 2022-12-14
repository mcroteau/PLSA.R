

<plsar:if spec="${message != ''}">
    <p class="notify">${message}</p>
</plsar:if>


<h1 class="left-float">Designs</h1>

<a href="/designs/new/${business.id}" class="button retro right-float">New Design</a>
<br class="clear"/>

<plsar:if spec="${designs.size() > 0}">
    <table>
        <tr>
            <th>Name</th>
            <th>Default</th>
            <th></th>
        </tr>
        <plsar:each items="${designs}" var="design" varStatus="idx">
            <tr>
                <td>${design.name}</td>
                <td>
                    <plsar:if spec="${design.baseDesign}">
                        &check;
                    </plsar:if>
                </td>
                <td>
                    <form action="/designs/delete/${design.id}" method="post">
                        <a href="/designs/edit/${design.id}" class="button orange">Edit</a>
                        <input type="submit" value="Delete" class="button remove"/>
                    </form>
                </td>
            </tr>
        </plsar:each>
    </table>
</plsar:if>

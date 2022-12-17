
<%@ page import="giga.Giga" %>

<style>
    #menu-wrapper{display:none;}
    #content-wrapper{width:100%;}
</style>

<a href="/snapshot/${business.id}" class="href-dotted">&larr;&nbsp;Back</a>
<br class="clear"/>

<plsar:if spec="${message != ''}">
    <p class="notify">${message}</p>
</plsar:if>

<h3 class="left-float">Item Groups</h3>
<a href="/imports/item_groups/new/${business.id}" class="button orange right-float" style="margin-top:20px;">New Item Group Import</a>
<br class="clear"/>

<plsar:if spec="${ingests.size() > 0}">
    <plsar:each items="${ingests}" var="ingest" varStatus="idx">
        <plsar:each items="${ingest.itemGroups}" var="itemGroup" varStatus="idxn">
            <h2>${itemGroup.name}</h2>
            <table>
                <tr>
                    <th>Model Number</th>
                    <th>Quantity</th>
                    <th>Weight</th>
                    <plsar:each var="groupOption" items="${itemGroup.groupOptions}" varStatus="idxi">
                        <th>${groupOption.title}</th>
                    </plsar:each>
                    <plsar:each var="pricingOption" items="${itemGroup.pricingOptions}" varStatus="idxd">
                        <th>${pricingOption.description}</th>
                    </plsar:each>
                </tr>
                <plsar:each var="groupModel" items="${itemGroup.groupModels}" varStatus="idc">
                    <tr>
                        <td>${groupModel.modelNumber}</td>
                        <td>${groupModel.quantity}</td>
                        <td>${groupModel.weight}</td>
                        <plsar:each var="optionValue" items="${groupModel.groupValues}" varStatus="idxb">
                            <td>${optionValue.value}</td>
                        </plsar:each>
                        <plsar:each var="pricingValue" items="${groupModel.pricingValues}" varStatus="idxa">
                            <td>$${pricingValue.price}</td>
                        </plsar:each>
                    </tr>
                </plsar:each>
            </table>
            <div class="button-wrapper">
                <form action="/imports/item_groups/group/delete/${business.id}/${itemGroup.id}" method="post">
                    <input type="submit" value="Delete Above Item Group" class="button remove"/>
                </form>
            </div>
        </plsar:each>
    </plsar:each>
</plsar:if>

<plsar:if spec="${ingests == null || ingests.size() == 0}">
    <p class="notify">No item group imports yet! <a href="/imports/item_groups/new/${business.id}" class="href-dotted">New Item Group Import</a></p>
</plsar:if>


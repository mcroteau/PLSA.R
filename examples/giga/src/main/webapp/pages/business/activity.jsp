
<%@ page import="giga.Giga" %>

${siteService.getBaseBit(Kilo.HEAD, design, business, request)}


    <plsar:if spec="${message != ''}">
        <p class="notify">${message}</p>
    </plsar:if>

    <h1>My Orders</h1>

    <p>A recap for all the orders placed for ${business.name}</p>

    <plsar:if spec="${sales.size() > 0}">
        <table>
            <tr>
                <th>Id</th>
                <th>Date</th>
                <th>Details</th>
            </tr>
            <plsar:each var="sale" items="${sales}" varStatus="idx">
                <tr>
                    <td>${sale.id}</td>
                    <td style="width:150px;">
                        <strong>${sale.prettyDate}</strong>
                   </td>
                    <td style="padding:0px !important">
                        <table>
                            <tr>
                                <th>Id</th>
                                <th>Item</th>
                                <th>Quantity</th>
                                <th>Price</th>
                                <th>Total</th>
                            </tr>
                            <plsar:each items="${sale.cart.cartItems}" var="cartItem">
                                <tr>
                                    <td>${cartItem.item.id}</td>
                                    <td>${cartItem.item.name}
                                        <plsar:each items="${cartItem.cartOptions}" var="cartOption">
                                            <br/>
                                            <span class="information">${cartOption.itemOption.name}  ${cartOption.optionValue.value} @ +$${siteService.getPrice(cartOption.optionValue.price)}</span>
                                        </plsar:each>
                                    </td>
                                    <td>${cartItem.quantity}</td>
                                    <td>$${siteService.getPrice(cartItem.item.price)}</td>
                                    <td>$${siteService.getPrice(cartItem.itemTotal)}</td>
                                </tr>
                            </plsar:each>
                            <tr>
                                <td colspan="4">Sub Total:</td>
                                <td>$${siteService.getPrice(sale.cart.subtotal)}</td>
                            </tr>
                            <tr>
                                <td colspan="4">Shipping:</td>
                                <td>
                                    $${siteService.getPrice(sale.cart.shipping)}
                                </td>
                            </tr>
                            <tr>
                                <td colspan="4">Total:</td>
                                <td>${siteService.getPrice(sale.cart.total)}</td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </plsar:each>
        </table>
    </plsar:if>

    <plsar:if spec="${sales.size() == 0}">
        <p class="notify">Nothing to see yet! No orders placed.</p>
    </plsar:if>



${siteService.getBaseBit(Kilo.BOTTOM, design, business, request)}
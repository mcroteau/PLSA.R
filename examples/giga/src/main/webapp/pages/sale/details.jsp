<h1>Sale Details</h1>
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
        <td>${siteService.getPrice(sale.cart.subtotal)}</td>
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

<h3>Shipping Address</h3>
<address>
    ${cart.shipName}, ${cart.shipPhone}<br/>
    ${cart.shipStreet}<br/>
    ${cart.shipStreetDos}<br/>
    ${cart.shipCity}, ${cart.shipState}<br/>
    ${cart.shipZip}<br/>
    ${cart.shipCountry}<br/>
    ${cart.shipEmail}
</address>
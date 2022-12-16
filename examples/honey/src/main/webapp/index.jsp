<h1>Honey</h1>
<p>Shopping List</p>

<form action="/save" method="post">
    <input type="text" name="description" placeholder="carrots"/>
    <input type="submit" value="Add +" class="button"/>
</form>

<ul>
    <plsar:foreach items="${items}" var="item">
        <li>${item.description}</li>
    </plsar:foreach>
</ul>

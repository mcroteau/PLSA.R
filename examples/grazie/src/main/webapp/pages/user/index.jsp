<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="inside-container">

	<plsar:if spec="${message != ''}">
		<p class="notify">${message}</p>
	</plsar:if>

	<h1>Users</h1>
	<plsar:if spec="${users.size() > 0}">
		<table class="table table-condensed">
			<thead>
				<tr>
					<th>Id</th>
					<th>Name</th>
					<th>Phone</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<plsar:foreach items="${users}" var="user" >
					<tr>
						<td>${user.id}</td>
						<td>${user.name}</td>
						<td>${user.phone}</td>
						<td><a href="/users/edit/${user.id}" title="Edit" class="button retro">Edit</a>
					</tr>
				</plsar:foreach>
			</tbody>
		</table>
	</plsar:if>
	<plsar:if spec="${users.size() == 0}">
		<p>No users created yet.</p>
	</plsar:if>
</div>
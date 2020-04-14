<#import "template.ftl" as layout />
<@layout.mainLayout title="New File">
<form action="/staff-documentation" method="post">
    <div class="form-group">
        <label for="name">Name</label>
        <input type="text" class="form-control" id="name" name="name" placeholder="Enter Name" value="${(file.name)!}">
    </div>
    <div class="form-group">
        <label for="email">User</label>
        <input type="email" class="form-control" id="email" name="email" placeholder="Enter User" value="${(file.user)!}">
    </div>
    <div class="form-group">
        <label for="city">Upload</label>
        <input type="" class="form-control" id="city" name="city" placeholder="Enter City" value="${(employee.city)!}">
    </div>
    <input type="hidden" id="action" name="action" value="${action}">
    <input type="hidden" id="id" name="id" value="${(employee.id)!}">
    <button type="submit" class="btn btn-primary">Submit</button>
</form>
</@layout.mainLayout>
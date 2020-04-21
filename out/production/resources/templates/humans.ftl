<#import "template.ftl" as layout />
<@layout.mainLayout title="New Human">
<form action="/newHuman" method="post">
    <div class="form-group">
        <label for="fname">First Name</label>
        <input type="text" class="form-control" id="fname" name="fname" placeholder="Enter First Name" value="${(human.f_name)!}">
    </div>
    <div class="form-group">
        <label for="lname">First Name</label>
        <input type="text" class="form-control" id="lname" name="lname" placeholder="Enter Last Name" value="${(human.l_name)!}">
    </div>
    <div class="form-group">
        <label for="username">User Name</label>
        <input type="text" class="form-control" id="username" name="username" placeholder="Enter User Name" value="${(human.user)!}">
    </div>
    <div class="form-group">
        <label for="pass">Password</label>
        <input type="password" class="form-control" id="pass" name="pass" placeholder="Enter Password" value="${(human.pass)!}">
    </div>
    <div class="form-group">
        <label for="groupId">Medical Group</label>
        <input type="text" class="form-control" id="groupId" name="groupId" placeholder="Enter Hospital ID" value="${(human.groupId)!}">
    </div>
    <div class="form-group">
        <label for="access">Access Level</label>
        <input type="text" class="form-control" id="access" name="access" placeholder="Enter Access Level" value="${(human.access)!}">
    </div>
    <button type="submit" class="btn btn-primary">Submit</button>
</form>
</@layout.mainLayout>
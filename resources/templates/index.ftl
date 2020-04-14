<#import "template.ftl" as layout />
<@layout.mainLayout>
<#list files as file>
<h1>Welcome ${file.userName}</h1>
<table class="table">
    <thead class="thead-dark">
    <tr>
        <th scope="col">File Names</th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>${file.name}</td>
        <td>
            <a href="/client?action=download&name=${file.name}&user=${file.user}" class="btn btn-secondary float-right mr-2" role="button">Download</a>
        </td>
    </tr>
    </#list>
    </tbody>
</table>
</@layout.mainLayout>
<#import "template.ftl" as layout />
<@layout.mainLayout>
<#list files as file>
<#assign user = file.fullName>
</#list>
<h1>Welcome ${user}</h1>
<h3>Below are your registered files, click the download button to download!</h3>
<table class="table">
    <thead class="thead-dark">
    <tr>
        <th scope="col">File Names</th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <#list files as file>
    <tr>
        <td>${file.fileName}</td>
        <td>
            <a href="/client?action=download&name=${file.fileName}&user=${file.user}" class="btn btn-secondary float-right mr-2" role="button">Download</a>
        </td>
    </tr>
    </#list>
    </tbody>
</table>
</@layout.mainLayout>
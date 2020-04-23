<#import "template.ftl" as layout />

<body>
<style type="text/css">
h1 {
    font: bold italic 3em/2em "Times New Roman", "MS Serif", "New York", serif;
    margin: 0;
    padding: 0;
    color: #00ffff;
    border-top: solid #00ffff medium;
    border-bottom: dotted #00ffff thin;
    width: 1350px;
    text-align:center;
    }

{box-sizing: border-box}
body {font-family: "Lato", sans-serif;}

/* Style the tab */
.tab {
  float: left;
  border: 1px solid #ccc;
  background-color: #f1f1f1;
  width: 30%;
  height: 300px;
}

/* Style the buttons inside the tab */
.tab button {
  display: block;
  background-color: inherit;
  color: black;
  padding: 22px 16px;
  width: 100%;
  border: none;
  outline: none;
  text-align: left;
  cursor: pointer;
  transition: 0.3s;
  font-size: 17px;
}

/* Change background color of buttons on hover */
.tab button:hover {
  background-color: #ddd;
}

/* Create an active/current "tab button" class */
.tab button.active {
  background-color: #ccc;
}

/* Style the tab content */
.tabcontent {
  float: left;
  padding: 0px 12px;
  border: 1px solid #ccc;
  width: 70%;
  border-left: none;
}
</style>

<@layout.mainLayout>
<#assign x = 0>
<#list files as file>
<#if x < 1>
<h1>${file.fullName}'s Files</h1>
</#if>
<#assign x = 1>
</#list>

    <table class="table">
        <thead class="thead-dark">
        <tr>
            <th scope="col">File Names</th>
            <th>
                <input class="form-control" type="text" placeholder="Search" aria-label="Search">
            </th>
        </tr>
        </thead>
        <tbody>
        <#list files as file>
        <tr>
            <td>${file.fileName}</td>
            <td>
                <a href="/download?action=download&name=${file.fileName}&user=${file.user}" class="btn btn-secondary float-right mr-2" role="button">Download</a>
            </td>
        </tr>
        </#list>
        </tbody>
    </table>
</body>
</@layout.mainLayout>

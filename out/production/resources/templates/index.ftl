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
  height: 300px;
}
</style>

<@layout.mainLayout>
<#assign x = 0>
<#list files as file>
<#if x < 1>
<h1>Welcome ${file.fullName}</h1>
</#if>
<#assign x = 1>
</#list>

<div class="tab">
    <button class="tablinks" onclick="openTab(event, 'Files', 'silver')" id="defaultOpen">Files</button>
    <button class="tablinks" onclick="openTab(event, 'Appointments', 'silver')">Appointments</button>
    <button class="tablinks" onclick="openTab(event, 'Home' , 'silver')">Back Home</button>
</div>

<div id="Files" class="tabcontent">
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
</div>

<div id="Appointments" class="tabcontent">
    <h3>New Appointments</h3>
    <p>Please enter the details of the appointment you would like to make:</p>

</div>

<div id="Home" class="tabcontent text-center">
    <h3 style="text-align:center;">Are You Sure You Wish To Return Home?</h3>
    <a href = "/" class="btn btn-primary btn-lg" >Continue</a>
</div>

<script>
function openTab(evt, tabName) {
  var i, tabcontent, tablinks;
  tabcontent = document.getElementsByClassName("tabcontent");
  for (i = 0; i < tabcontent.length; i++) {
    tabcontent[i].style.display = "none";
  }
  tablinks = document.getElementsByClassName("tablinks");
  for (i = 0; i < tablinks.length; i++) {
    tablinks[i].className = tablinks[i].className.replace(" active", "");
  }
  document.getElementById(tabName).style.display = "block";
  evt.currentTarget.className += " active";
}

// Get the element with id="defaultOpen" and click on it
document.getElementById("defaultOpen").click();
</script>
</body>
</@layout.mainLayout>
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
<#list staff as dev>
<#if x < 1>
<h1>Welcome ${dev.fname} ${dev.lname}</h1>
</#if>
<#assign x = 1>
</#list>

<div class="tab">
    <button class="tablinks" onclick="openTab(event, 'Clients', 'silver')" id="defaultOpen">Clients</button>
    <button class="tablinks" onclick="openTab(event, 'newUser', 'silver')">New User</button>
    <button class="tablinks" onclick="openTab(event, 'Home' , 'silver')">Back Home</button>
</div>

<div id="Clients" class="tabcontent">
    <table class="table">
        <thead class="thead-dark">
        <tr>
            <th scope="col">Users</th>
            <th>
                <input class="form-control" type="text" placeholder="Search" aria-label="Search">
            </th>
        </tr>
        </thead>
        <tbody>
        <#list humans as human>
        <tr>
            <td>${human.fname} ${human.lname}</td>
            <td>
                <a href="/editHuman?name=${human.user}" class="btn btn-secondary float-right mr-2" role="button">Edit User</a>
            </td>
        </tr>
        </#list>
        </tbody>
    </table>
</div>

<div id="newUser" class="tabcontent">
    <h3>New User</h3>
    <p>Please enter the details of the new user:</p>
    <form action="/newHuman" method="post" class="form-signin">
        <div class="form-group">
            <label for="type">Select The User Type:</label>
            <select id="type">
                <option value="doc">Doctor</option>
                <option value="human">Client</option>
            </select>
        </div>
        <div class="row">
            <div class="col-md-6 mb-3">
                <label for="firstName">First name</label>
                <input type="text" class="form-control" id="firstName" placeholder="" value="" required>
                <div class="invalid-feedback">
                    Valid first name is required.
                </div>
            </div>
            <div class="col-md-6 mb-3">
                <label for="lastName">Last name</label>
                <input type="text" class="form-control" id="lastName" placeholder="" value="" required>
                <div class="invalid-feedback">
                    Valid last name is required.
                </div>
            </div>
        </div>

        <div class="mb-3">
            <label for="username">Username</label>
            <div class="input-group">
                <input type="text" class="form-control" id="username" placeholder="Username" required>
                <div class="invalid-feedback" style="width: 100%;">
                    Your username is required.
                </div>
            </div>
        </div>

        <div class="mb-3">
            <label for="password">Password</label>
            <div class="input-group">
                <input type="password" class="form-control" id="password" placeholder="Password" required>
                <div class="invalid-feedback" style="width: 100%;">
                    Your username is required.
                </div>
            </div>
        </div>

        <!--                <div class="mb-3">-->
        <!--                    <label for="email">Email <span class="text-muted">(Optional)</span></label>-->
        <!--                    <input type="email" class="form-control" id="email" placeholder="you@example.com">-->
        <!--                </div>-->

        <div class="mb-3">
            <label for="grpCode">Medical Group Code</label>
            <input type="text" class="form-control" id="grpCode" placeholder="ex.) GHP" required>
        </div>

        <div class="mb-3">
            <label for="access">Access Level</label>
            <input type="text" class="form-control" id="access" min="1" max="3" required>
        </div>
        <hr class="mb-4">
        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
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
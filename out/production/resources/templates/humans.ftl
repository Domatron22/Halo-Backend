<!doctype html>
<html lang="en">
<!--Css Styles-->
<style>
      .bd-placeholder-img {
        font-size: 1.125rem;
        text-anchor: middle;
        -webkit-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
        user-select: none;
      }

      @media (min-width: 768px) {
        .bd-placeholder-img-lg {
          font-size: 3.5rem;
        }
      }

        body {
          height: 100%;
        }

        body {
          align-items: center;
          text-align: center;
          display: -ms-flexbox;
          display: flex;
          -ms-flex-align: center;
          padding-top: 40px;
          padding-bottom: 40px;
          background-color: #f5f5f5;
        }
        .form-signin {
          margin: 20px auto;
          width: 100%;
          max-width: 320px;
          padding: 15px;

        }
        .form-signin .checkbox {
          font-weight: 400;
        }
        .form-signin .form-control {
          position: relative;
          box-sizing: border-box;
          height: auto;
          padding: 10px;
          font-size: 16px;
        }
        .form-signin .form-control:focus {
          z-index: 2;
        }

        .container {
          max-width: 960px;
        }

        .border-top { border-top: 1px solid #e5e5e5; }
        .border-bottom { border-bottom: 1px solid #e5e5e5; }
        .border-top-gray { border-top-color: #adb5bd; }

        .box-shadow { box-shadow: 0 .25rem .75rem rgba(0, 0, 0, .05); }

        .lh-condensed { line-height: 1.25; }
    </style>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Sign-in</title>


    <!-- Bootstrap core CSS -->
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link rel="canonical" href="https://getbootstrap.com/docs/4.0/examples/checkout/">

    <!-- Bootstrap core CSS -->
    <link href="../../static/bootstrap.min.css" rel="stylesheet">

    <meta name="theme-color" content="#563d7c">


</head>
<body class="text-center">
<form action="/newHuman" method="post" class="form-signin">
    <h1 class="h3 mb-3 font-weight-normal">New Human: </h1>
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

    <!--                <div class="mb-3">-->
    <!--                    <label for="email">Email <span class="text-muted">(Optional)</span></label>-->
    <!--                    <input type="email" class="form-control" id="email" placeholder="you@example.com">-->
    <!--                </div>-->

    <div class="mb-3">
        <label for="address">Medical Group Code</label>
        <input type="text" class="form-control" id="address" placeholder="ex.) GHP" required>
    </div>

    <div class="mb-3">
        <label for="address2">Access Level</label>
        <input type="text" class="form-control" id="address2" placeholder="Apartment or suite">
    </div>
    <hr class="mb-4">
</form>
<script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js">
</script>
<script>
    <script src=js/bootstrap.min.js>
</script>
</body>
</html>

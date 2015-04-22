<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
</head>
<body>
<#if RequestParameters['error']??>
<div class="alert alert-danger">
    Invalid code. Please try again.
</div>
</#if>
<div class="container">
    <form role="form" action="verifycode" method="post">
        Scan this demo QR code with <a href="https://support.google.com/accounts/answer/1066447?hl=en" target="_blank">Google Authenticator</a> app in order receive verification codes.
        <div>
            <img src="http://chart.googleapis.com/chart?chs=200x200&amp;chld=M%7C0&amp;cht=qr&amp;chl=otpauth://totp/Example:user?secret=JBSWY3DPEHPK3PXP&issuer=Example" />
        </div>
        <div class="form-group">
            <label for="code">Verification code:</label>
            <input type="text" class="form-control" id="code" name="code"/>
        </div>
        <!-- needed for  Cross Site Request Forgery -->
        <input type="hidden" id="csrf_token" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary">Verify</button>
    </form>
</div>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
</body>
</html>
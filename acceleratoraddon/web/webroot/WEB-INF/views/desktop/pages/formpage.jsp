<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<c:set value="hybris.local:9002/store/_ui/addons/wsclientgenerator/desktop/common/css/form.css" context="" var="stylesheetPath"/>


<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Free Bootstrap Contact Form With PHP - reusable form</title>
        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" >
        <!-- Optional theme -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" >
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <link rel="stylesheet" href="wsclientgenerator" >
        <script src="form.js"></script>
    </head>
    <body >
        <div class="container-fluid">
            <div class="row">
				
				<div class="col-md-2 col-md-offset-1 detailsConf"  >
					<div class="row">
						<div class="col-sm-12 form-group">
							<label>URI : </label> http://127.0.0.1:3000/customer/xml
						</div>
						<div class="col-sm-12 form-group">
							<label>Method : </label> GET
						</div>
						<div class="col-sm-12 form-group">
							<label>Enable : </label> True
						</div>
					</div>
					<p> dsgffedg </p>
				</div>
				
                <div class="col-md-5 col-md-offset-1" id="form_container">
					<div class="title">Simulation of web services calls </div>
                    <form role="form" method="post" id="reused_form">
						<div class="row">
							<div class="col-sm-6 form-group">
								<label for="category">Please select a category</label>
								<select class="form-control" name="category" id="category">
									<option>Price</option>
									<option>Order</option>
									<option>Customer</option>
									<option>Stock</option>
								</select>
							</div>
							<div class="col-sm-6 form-group">
								<label for="configuration">Please select a configuration of web service</label>
								<select class="form-control" name="configuration" id="configuration">
									<option>Price</option>
									<option>Order</option>
									<option>Customer</option>
									<option>Stock</option>
								</select>
							</div>
						</div>
                        
                        <div class="row">
                            <div class="col-sm-6 form-group">
                                <label for="name"> Your Name:</label>
                                <input type="text" class="form-control" id="name" name="name" required>
                            </div>
                            <div class="col-sm-6 form-group">
                                <label for="email"> Email:</label>
                                <input type="email" class="form-control" id="email" name="email" required>
                            </div>
                        </div>
						
						
						<div class="row">
                            <div class="col-sm-12 form-group">
                                <label for="message"> Response:</label>
                                <textarea class="form-control" type="textarea" name="message" id="message" maxlength="6000" rows="7"></textarea>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-12 form-group">
                                <button type="submit" class="btn btn-lg btn-default pull-right" >Send &rarr;</button>
                            </div>
                        </div>
                    </form>
                    <div id="success_message" style="width:100%; height:100%; display:none; "> <h3>Posted your message successfully!</h3> </div>
                    <div id="error_message" style="width:100%; height:100%; display:none; "> <h3>Error</h3> Sorry there was an error sending your form. </div>
                </div>
            </div>
        </div>
    </body>
</html>

<div class="col-md-4 detailsConf">
				<div class="row">
					<div class="col-sm-12 form-group">
						<label>URI : </label> http://127.0.0.1:3000/customer/xml
					</div>
					<div class="col-sm-12 form-group">
						<label>Method : </label> GET
					</div>
					<div class="col-sm-12 form-group">
						<label>Enable : </label> True
					</div>
				</div>
				<p>dsgffedg</p>
			</div>
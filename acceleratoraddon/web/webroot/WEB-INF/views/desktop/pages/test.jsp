
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/desktop/nav"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common"%>



<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>

<template:page pageTitle="${pageTitle}">


	<div class="container-fluid">
		<div class="title">Simulation of web services calls</div>
		<div class="row">

			<div class="col-md-10" id="form_container">
				<form role="form" method="post" id="wsConfigurationChoice">
					<div class="row">
						<div class="col-sm-6 form-group">
							<label for="category">Please select a category</label> <select
								class="form-control" name="category" id="category">
								<option>Price</option>
								<option>Order</option>
								<option>Customer</option>
								<option>Stock</option>
							</select>
						</div>
						<div class="col-sm-6 form-group">
							<label for="configuration">Please select a configuration
								of web service</label> <select class="form-control" name="configuration"
								id="configuration">
								<option>Price</option>
								<option>Order</option>
								<option>Customer</option>
								<option>Stock</option>
							</select>
						</div>
					</div>
				</form>
				<div class="col-sm-8 wsConfigurationDetails">
					<label>URI : </label> http://127.0.0.1:3000/customer/xml <br>
					<label>Method : </label> GET <br> <label>Status : </label>
					<p>
						<small class="label label-success">Active</small>
					</p>
					<br>
					<p>dsgffedg</p>
				</div>
				<form role="form" method="post" id="reused_form">
					<div class="row">
						<div class="col-sm-6 form-group">
							<label for="name"> Your Name:</label> <input type="text"
								class="form-control" id="name" name="name" required>
						</div>
						<div class="col-sm-6 form-group">
							<label for="email"> Email:</label> <input type="email"
								class="form-control" id="email" name="email" required>
						</div>
					</div>


					<div class="row">
						<div class="col-sm-12 form-group">
							<label for="message"> Response:</label>
							<textarea class="form-control" type="textarea" name="message"
								id="message" maxlength="6000" rows="7"></textarea>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-12 form-group">
							<button type="submit" class="btn btn-success">Send
								request &rarr;</button>
						</div>
					</div>
				</form>
				<div id="success_message"
					style="width: 100%; height: 100%; display: none;">
					<h3>Posted your message successfully!</h3>
				</div>
				<div id="error_message"
					style="width: 100%; height: 100%; display: none;">
					<h3>Error</h3>
					Sorry there was an error sending your form.
				</div>
			</div>

		</div>
	</div>

	<script>
		jQuery(document).ready(function($) {
			
			searchAjax();
			//$("#search-form").submit(function(event) {

				// Prevent the form from submitting via the browser.

				//event.preventDefault();
				

			//});
		});

		function searchAjax() {
			//var data = {}
			//data["query"] = $("#query").val();

			$.ajax({
				type : "GET",
				//contentType : "application/json",
				url : "https://hybris.local:9002/store/hybris/en/wsconfigurations",
				//data : JSON.stringify(data),
				//dataType : 'json',
				timeout : 100000,
				success : function(data) {
					console.log("SUCCESS: ", data);
				},
				error : function(e) {
					console.log("ERROR: ", e);
				},
				done : function(e) {
					console.log("DONE");
				}
			});
		}
	</script>


</template:page>
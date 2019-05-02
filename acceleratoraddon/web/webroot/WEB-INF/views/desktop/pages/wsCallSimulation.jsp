
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
<script src="https://cdnjs.cloudflare.com/ajax/libs/ace/1.4.3/ace.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/ace/1.4.3/mode-json.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/ace/1.4.3/mode-xml.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/ace/1.4.3/theme-twilight.js"></script>
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.8.1/css/all.css"
	integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf"
	crossorigin="anonymous">




<template:page pageTitle="${pageTitle}">


	<div class="container-fluid wsclientgenerator">
		<div class="title">Simulation of web services calls</div>
		<div class="row">
			<div class="col-md-10" id="form_container">
				<form role="form" method="post" id="wsCall">
					<div class="row">
						<div class="col-sm-6 form-group perso">
							<input type="hidden" id="csrf" name="CSRFToken"
								value="${CSRFToken}"> <label for="category">Please
								select a functionality : </label> <select class="form-control perso"
								name="functionality" id="functionality"
								onchange="getAllConfigurations(this);">
								<option value=""></option>
								<option value="price">Price</option>
								<option value="order">Order</option>
								<option value="customer">Customer</option>
								<option value="stock">Stock</option>
							</select>
						</div>
						<div class="col-sm-6 form-group perso">
							<label for="configuration ">Please select a configuration
								of web service : </label> <select class="form-control "
								name="configurationID" id="configuration"
								onchange="getConfigurationDetails(this);">
							</select>
						</div>
					</div>
					<div class="col-sm-8 wsConfigurationDetails"
						id="wsConfigurationDetails">
						<div class="detailsWsConf">Web service configuration details</div>
						<div class="urlDetails">
							<div class="" id="method"></div>
							<div class="" id=uri></div>
						</div>
						<div class="extraUrlDetails">
							<div id="enable" class="status"></div>
							<div id="" class="accept text-secondary">
								Response format : <span id="accept"> </span>
							</div>
							<div id="contentType" class="contentType text-secondary"></div>
						</div>
						<div id="securityDetails" class="securityDetails">
							<fieldset>
								<legend class="legend_security">Security informations</legend>
								<div id="login" class=""></div>
								<div id="password" class=""></div>								
							</fieldset>
						</div>
					</div>
					<div id="path">
						<fieldset>
							<legend>URL path parameters</legend>
							<div class="row" id="pathParameters"></div>
						</fieldset>
					</div>
					<div id="query">
						<fieldset>
							<legend>Query parameters</legend>
							<div class="row" id="queryParameters"></div>
						</fieldset>
					</div>
					<div class="row">
						<div class="form-group perso" id="btn">
							<button id="submit" type="submit" class="btn btn-success">Send
								request &rarr;</button>
						</div>
					</div>
				</form>
				<div class="row" id="response">
					<div class="response">
						<hr class="sep_response">
						Response
					</div>
					<div class="col-sm-10 form-group perso" id="editor"></div>
				</div>
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
</template:page>
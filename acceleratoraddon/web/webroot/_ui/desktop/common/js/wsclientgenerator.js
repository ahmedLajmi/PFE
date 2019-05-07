jQuery(document).ready(
		function($) {

			$.ajaxPrefilter(function(options, originalOptions, jqXHR) {
				// Modify options, control originalOptions, store jqXHR, etc
				if ((options.type === "post" || options.type === "POST")
						&& (options.data !== null)) {
					var noData = (typeof options.data === "undefined");
					if (noData || options.data.indexOf("CSRFToken") === -1) {
						options.data = (!noData ? options.data + "&" : "")
								+ "CSRFToken=" + ACC.config.CSRFToken;
					}
				}
			});
		});

$('#wsCall').submit(function(e) {
	e.preventDefault();
	console.log($(this).serialize());
	$.ajax({
		type : "POST",
		url : "https://hybris.local:9002/store/hybris/en/wsConfigurationCall",
		traditional : true,
		data : $(this).serialize(),
		dataType : 'json',
		timeout : 100000,
		success : function(data) {
			console.log("SUCCESS: ", data);
			$('#response').show();
			var editor = ace.edit("editor");
			var XMLMode = ace.require("ace/mode/xml").Mode;
			var JSONMode = ace.require("ace/mode/json").Mode;
			editor.setTheme("ace/theme/twilight");
			editor.setReadOnly(true);
			editor.getSession().setUseWorker(false);
			if (document.getElementById("accept").textContent == 'xml') {
				editor.session.setMode(new XMLMode());
			} else {
				editor.session.setMode(new JSONMode());
			}

			editor.selectAll();
			editor.removeLines();
			try{
				var o = JSON.parse(data.responseBody) // may throw if json is malformed
				val = JSON.stringify(o, null, 4) // 4 is the indent size
				editor.insert(val);
			}
			catch(err){
				editor.insert(data.responseBody);
			}
		},
		error : function(e) {
			console.log("ERROR: ", e);
		},
		done : function(e) {
			console.log("DONE");
		}
	});

});

function getAllConfigurations(func) {
	if (func.value != '') {
		$('#wsConfigurationDetails').hide();
		$("#statiqueParam").hide();
		$('#securityDetails').hide();
		$("#query").hide();
		$("#path").hide();
		$("#queryParameters").empty();
		$("#pathParameters").empty();
		$('#submit').hide();
		$('#response').hide();
		$
				.ajax({
					type : "POST",
					url : "https://hybris.local:9002/store/hybris/en/wsAllConfigurations",
					traditional : true,
					data : {
						functionality : func.value,
						CSRFToken : document.getElementById("csrf").value
					},
					dataType : 'json',
					timeout : 100000,
					success : function(data) {
						console.log("SUCCESS: ", data);
						$('#configuration').empty();
						$('#wsConfigurationDetails').hide();
						var o = new Option("", "");
						$("#configuration").append(o);
						data.forEach(function(item, index, array) {
							var o = new Option(item.name, item.id);
							$("#configuration").append(o);
							console.log(item, index);
							console.log("Object: ", item.method);
						});
					},
					error : function(e) {
						console.log("ERROR: ", e);
					},
					done : function(e) {
						console.log("DONE");
					}
				});
	} else {
		$("#query").hide();
		$("#path").hide();
		$("#queryParameters").empty();
		$("#pathParameters").empty();
		$('#wsConfigurationDetails').hide();
		$('#configuration').empty();
		$('#response').hide();
		$('#submit').hide();
		$('#securityDetails').hide();
		$("#statiqueParam").hide();
	}

}

function getConfigurationDetails(func) {
	$("#statiqueParam").hide();
	$("#securityDetails").hide();
	if (func.value != '') {
		$
				.ajax({
					type : "POST",
					url : "https://hybris.local:9002/store/hybris/en/wsConfigurationDetails",
					traditional : true,
					data : {
						configurationID : func.value,
						functionality : document
								.getElementById("functionality").value,
						CSRFToken : document.getElementById("csrf").value
					},
					dataType : 'json',
					timeout : 100000,
					success : function(response) {
						console.log("SUCCESS: ", response);
						$('#submit').show();
						$('#wsConfigurationDetails').show();
						document.getElementById("uri").textContent = response.url;
						document.getElementById("method").textContent = response.method;
						document.getElementById("accept").textContent = response.accept;
						if (response.enable == true) {
							document.getElementById("enable").innerHTML = 'Enable : <i class="far fa-check-circle"></i>';
							document.getElementById("enable").className = "status text-success"
						} else {
							document.getElementById("enable").innerHTML = 'Enable : <i class="far fa-times-circle"></i>';
							document.getElementById("enable").className = "status text-danger"
						}
						if (response.contentType != null) {
							document.getElementById("contentType").textContent = 'Request format :'
									+ response.contentType;
						} else {
							$('#contentType').hide();
						}
						if (response.securityParameters != null
								&& response.securityParameters.length > 0) {
							response.securityParameters
									.forEach(function(item, index, array) {
										if (item.key == "login") {
											document.getElementById("login").textContent = item.value;
										} else if (item.key == "password") {
											document.getElementById("password").textContent = item.value;
										}
									});
							$('#securityDetails').show();
						}
						if ((response.persoParameters != null && response.persoParameters.length > 0)
								|| (response.headerParameters != null && response.headerParameters.length > 0)) {
							$("#statiqueParam").show();
							$("#div_headerParam").hide();
							$("#div_queryParam").hide();
							if (response.persoParameters.length > 0) {
								$("#QueryParam").empty();
								var cpt = 1;
								response.persoParameters.forEach(function(item,
										index, array) {
									$("#QueryParam").append(
											'<tr>' + '<th scope="row">' + cpt
													+ '</th>' + '<td>'
													+ item.key + '</td>'
													+ '<td>' + item.value
													+ '</td>' + '</tr>');
									cpt++;
								});
								$("#div_queryParam").show();
							}
							if (response.headerParameters.length > 0) {
								$("#headerParam").empty();
								var cpt = 1;
								response.headerParameters.forEach(function(
										item, index, array) {
									$("#headerParam").append(
											'<tr>' + '<th scope="row">' + cpt
													+ '</th>' + '<td>'
													+ item.key + '</td>'
													+ '<td>' + item.value
													+ '</td>' + '</tr>');
									cpt++;
								});
								$("#div_headerParam").show();
							}
							if ($("#div_headerParam").is(":visible")
									&& $("#div_queryParam").is(":visible")) {
								document.getElementById("div_headerParam").className = "col-sm-5"
								document.getElementById("div_queryParam").className = "col-sm-5"
							} 
							else if ($("#div_headerParam").is(":visible")
									&& !$("#div_queryParam").is(":visible")) {
								document.getElementById("div_headerParam").className = "col-sm-10"
							}
							else if (!$("#div_headerParam").is(":visible")
									&& $("#div_queryParam").is(":visible")) {
								document.getElementById("div_queryParam").className = "col-sm-10"
							}
						}
						if (response.pathParameters != null) {
							$("#pathParameters").empty();
							if (response.pathParameters.length > 0) {
								response.pathParameters
										.forEach(function(item, index, array) {
											$("#pathParameters")
													.append(
															'<div class="col-sm-6 form-group perso"> '
																	+ '<label> Please enter the value for "'
																	+ item.key
																	+ '" parameter :</label> <input type="text" class="form-control" id="path_'
																	+ item.key
																	+ '" name="path_'
																	+ item.key
																	+ '" required>'
																	+ '</div>');
										});
								$("#path").show();
							}
						}
						if (response.queryParameters != null) {
							$("#queryParameters").empty();
							if (response.queryParameters.length > 0) {
								response.queryParameters
										.forEach(function(item, index, array) {
											$("#queryParameters")
													.append(
															'<div class="col-sm-6 form-group perso"> '
																	+ '<label> Please enter the value for "'
																	+ item.key
																	+ '" parameter :</label> <input type="text" class="form-control" id="query_'
																	+ item.key
																	+ '" name="query_'
																	+ item.key
																	+ '" required>'
																	+ '</div>');
										});
								$("#query").show();
							}
						}
					},
					error : function(e) {
						console.log("ERROR: ", e);
					},
					done : function(e) {
						console.log("DONE");
					}
				});
	} else {
		$("#query").hide();
		$("#path").hide();
		$("#queryParameters").empty();
		$("#pathParameters").empty();
		$('#wsConfigurationDetails').hide();
		$('#response').hide();
		$('#submit').hide();
		$('#securityDetails').hide();
		$("#statiqueParam").hide();
	}

}
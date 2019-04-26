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
			if (document.getElementById("accept").textContent == 'xml') {
				editor.session.setMode(new XMLMode());
			} else {
				editor.session.setMode(new JSONMode());
			}

			editor.selectAll();
			editor.removeLines();
			editor.insert(data.responseBody);

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
		$("#parameters").empty();
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
		$("#parameters").empty();
		$('#wsConfigurationDetails').hide();
		$('#configuration').empty();
		$('#response').hide();
		$('#submit').hide();
	}

}

function getConfigurationDetails(func) {
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
						document.getElementById("enable").textContent = response.enable;
						document.getElementById("accept").textContent = response.accept;
						if (response.parameters != null) {
							$("#parameters").empty();
							response.parameters
									.forEach(function(item, index, array) {
										$("#parameters")
												.append(
														'<div class="col-sm-6 form-group perso"> '
																+ '<label> Please enter the value for "'
																+ item.key
																+ '" parameter :</label> <input type="text" class="form-control" id="'
																+ item.key
																+ '" name="'
																+ item.key
																+ '" required>'
																+ '</div>');
									});
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
		$("#parameters").empty();
		$('#wsConfigurationDetails').hide();
		$('#response').hide();
		$('#submit').hide();
	}

}
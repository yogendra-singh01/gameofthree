<!DOCTYPE html>
<html>
<head>
<title>Welcome to Game of Three</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
	crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.2.1.min.js"
	integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4="
	crossorigin="anonymous""></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.4/sockjs.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
<style>
td {
	width: 25%;
}
</style>
<script>
    var stompClient = null;

    function setGameStartStop(connected) {
        $("#start").prop("disabled", connected);
        $("#name").prop("disabled", connected);
        $("#stop").prop("disabled", !connected);
        $("#send").prop("disabled", !connected);
        if (connected) {
            $("#gameBoard").show();
        }
        else {
            $("#gameBoard").hide();
        }
        $("#progressInformation").html("");
    }

    function start() {
    	var valid = isNameValid();
    	if(!valid){
    		$('div#nameError').html("Please provide a valid name");
    		return;
    	}
    	$('div#nameError').html("");
        var socket = new SockJS('/gameofthree/gameofthree-sockjs');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
        	setGameStartStop(true);
            console.log('Connected: ' + frame);
            stompClient.subscribe('/game/result', function (gameStatus) {
                showGameStatus(gameStatus.body);
            });
            stompClient.send("/app/start", {}, $("#name").val());
        });
    }

    function isNameValid(){
    	var playerName = $("#name").val();
    	if(playerName!=null && playerName != ""){
    		return true;
    	}
    	else{
    		return false;
    	}
    }
    function stop() {
        if (stompClient != null) {
        	stompClient.send("/app/stop", {}, {});
            stompClient.disconnect();
        }
        setGameStartStop(false);
        console.log("Disconnected");
    }

    function sendNumber() {
    	stompClient.send("/app/status", {}, JSON.stringify({'name': $("#name").val(),'value': $("#value").val()}) );
    }

 
    function showGameStatus(message) {
        $("#progressInformation").append("<tr><td>" + message + "</td></tr>");
    }
    

    $(function () {
        $("form").on('submit', function (e) {
            e.preventDefault();
        });
        $( "#start" ).click(function() { start(); });
        $( "#stop" ).click(function() { stop(); });
        $( "#send" ).click(function() { sendNumber(); });
    });
    
    </script>
</head>
<body>
	<noscript>
		<h2 style="color: #ff0000">Please
			enable Javascript and reload this page!</h2>
	</noscript>
	<div id="main-content" class="container">
		<div class="row">
			<div class="col-md-12">
				<form class="form-inline">
					<table>
						<tr>
							<td><label for="start">Enter your name and then
									press start:</label></td>
							<td><input type="text" id="name" class="form-control"
								placeholder="Your name here...">
								<div id="nameError"></div></td>
							<td><button id="start" class="btn btn-default" type="submit">Start</button><button id="stop" class="btn btn-default" type="submit"
									disabled="disabled">Stop</button></td>
						</tr>
						<tr>
							<td><label for="name">Enter value</label></td>
								<td><input
								type="text" id="value" class="form-control"
								placeholder="Value...."></td>
							<td><button id="send" class="btn btn-default" type="submit" disabled="disabled">Send</button></td>

						</tr>

					</table>
				</form>
			</div>
			
		</div>
		<div class="row">
			<div class="col-md-12">
				<table id="gameBoard" class="table table-striped">
					<thead>
						<tr>
							<th>Game Board</th>
						</tr>
					</thead>
					<tbody id="progressInformation">
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
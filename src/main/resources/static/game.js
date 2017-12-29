$(function() {

	var btnNewGameEnabled = false;

	var game = null;
	var board = null;

	var gameRefresh = null;

	var btnNewGame = $('#btnNewGame');
	var divCurrentGame = $('#divCurrentGame');

	// first time only, draw the board
	getBoardData();

	$(btnNewGame).click(function() {
		btnNewGameEnabled = false;
		$.get('/api/games/create', function(data1, status) {
			game = data1;
			getBoardData();
		});
	});

	function initGameRefresh() {
		gameRefresh = window.setInterval(function() {
			getGameData();
		}, 500);
	}

	function getBoardData() {
		$.get('/api/games/current/board', function(data, status) {
			if (data) {
				board = data;
				drawBoard();
				initGameRefresh();
			} else {
				// enable if no game started
				btnNewGameEnabled = true;
			}
		});
	}

	function getGameData() {
		$.get('/api/games/current', function(data, status) {
			game = data;
			drawAll();
		});
	}

	function drawAll() {
		// drawBoard();
		// var oldImgSelector = 'img[src*="warrior' + num + '"]';
		// remove ALL IMAGES and draw them again
		$('img').remove();
		// player1
		drawCastle(1);
		drawGold(1);
		drawPlayer(1);
		// player2
		drawCastle(2);
		drawGold(2);
		drawPlayer(2);
	}

	function drawBoard() {
		var currentGameBoard = '<table>';
		for (var rowIndex = 0; rowIndex < board.rows.length; rowIndex++) {
			var row = board.rows[rowIndex];
			currentGameBoard += '<tr>';
			for (var colIndex = 0; colIndex < row.fields.length; colIndex++) {
				var field = row.fields[colIndex];
				var cls = "";
				if (field.type == "GRASS") {
					cls += 'grass';
				} else if (field.type == "MOUNTAIN") {
					cls += 'mountain';
				} else if (field.type == "WATER") {
					cls += 'water';
				}
				var id = 'field-' + rowIndex + '-' + colIndex;
				currentGameBoard += '<td id="' + id + '" class="' + cls
						+ '"></td>';
			}
			currentGameBoard += '</tr>';
		}
		currentGameBoard += '</table>';
		$(divCurrentGame).html(currentGameBoard);
	}

	function drawPlayer(num) {
		var p = game['player' + num].position;
		var fieldSelector = '#field-' + p.row + '-' + p.column;
		var playerHTML = '<img src="images/warrior' + num + '.png">';
		$(fieldSelector).html(playerHTML);
	}

	function drawCastle(num) {
		var p = game['player' + num].castlePosition;
		var fieldSelector = '#field-' + p.row + '-' + p.column;
		var castleHTML = '<img src="images/castle' + num + '.png">';
		$(fieldSelector).html(castleHTML);
	}

	function drawGold(num) {
		var p = board['player' + num + 'GoldPosition'];
		var fieldSelector = '#field-' + p.row + '-' + p.column;
		var castleHTML = '<img src="images/gold.png">';
		$(fieldSelector).html(castleHTML);
	}

});

$(function() {

	var btnNewGameEnabled = false;

	var game = null;

	var btnNewGame = $('#btnNewGame');
	var divCurrentGame = $('#divCurrentGame');

	$.get('/api/games/current', function(data, status) {
		if (data) {
			game = data;
			drawAll();
		} else {
			// enable if no game started
			btnNewGameEnabled = true;
		}
	});

	$(btnNewGame).click(function() {
		$.get('/api/games/create', function(data, status) {
			game = data;
			drawAll();
		});

		btnNewGameEnabled = false;
	});

	function drawAll() {
		drawBoard();
		// player1
		drawPlayer(1);
		drawCastle(1);
		drawGold(1);
		// player2
		drawPlayer(2);
		drawCastle(2);
		drawGold(2);
	}

	function drawBoard() {
		var currentGameBoard = '<table>';
		for (var rowIndex = 0; rowIndex < game.board.rows.length; rowIndex++) {
			var row = game.board.rows[rowIndex];
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
		var playerHTML = '<img src="images/warrior' + num + '.png">'
		$(fieldSelector).html(playerHTML);
	}

	function drawCastle(num) {
		var p = game['player' + num].castlePosition;
		var fieldSelector = '#field-' + p.row + '-' + p.column;
		var castleHTML = '<img src="images/castle' + num + '.png">'
		$(fieldSelector).html(castleHTML);
	}

	function drawGold(num) {
		var p = game.board['player' + num + 'GoldPosition'];
		var fieldSelector = '#field-' + p.row + '-' + p.column;
		var castleHTML = '<img src="images/gold.png">'
		$(fieldSelector).html(castleHTML);
	}

});

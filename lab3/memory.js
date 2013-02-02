var Memory = {
  scores: [0,0],
  currentPlayer: 0,
  memoryMatrix: [[],[],[],[]],
  picks: [],
  
  displayScores: function() {
    $('.current_player').text(Memory.currentPlayer + 1);
    $('.player_0_score').text(Memory.scores[0]);
    $('.player_1_score').text(Memory.scores[1]);
  },
  generateMatrix: function() {
    var numbers = [1,1,2,2,3,3,4,4,5,5,6,6,7,7,8,8];
    var i,j;
    numbers.sort(function(){ return 0.5 - Math.random() });

    for(i = 0; i < 4; i++) {
      for(j = 0; j < 4; j++) {
        this.memoryMatrix[i][j] = {
          image: "images/" + numbers[i*4+j] + ".jpg", 
          faceDown: true
        }
        $('.card#'+i+''+j).css('background', 
                               'url('+this.memoryMatrix[i][j].image+')');
      }
    }
  },
  turnCard: function(card) {
    if(this.picks.length === 2) {
      this.updateBoard();
      this.displayScores();
      this.picks = [];
      return;
    }

    $(card).hide();
    var cardIndex = $(card).parent().attr('id');
    var i = cardIndex[0];
    var j = cardIndex[1];

    this.picks.push(this.memoryMatrix[i][j]);

    if(this.picks.length === 1) {
      return;
    } 
    
    if(this.picks[0].image === this.picks[1].image) {
      this.scores[this.currentPlayer]++;
      this.picks[0].faceDown = false;
      this.picks[1].faceDown = false;
    } 
    this.currentPlayer = (this.currentPlayer + 1) % 2;
  },

  updateBoard: function() {
    for(i = 0; i < 4; i++) {
      for(j = 0; j < 4; j++) {
        if(this.memoryMatrix[i][j].faceDown) {
          $('#' + i + '' + j + ' span').show();
        }
      }
    }
  }
};

/**
 * 
 */
var typeRadios = document.getElementsByName('type');
for (var i = 0; i < typeRadios.length; i++){
	console.log(typeRadios[i].name);
	typeRadios[i].onclick = radioOnClick;
}
function radioOnClick(){
	var index = this.value;
    var questionHTML = 'What Question Would You Like to Ask?' +
    	'<br>' +
    	'<input type="text" id="question">' +
    	'<br>' +
    	'How much is this question worth?' +
    	'<input type="text" id="score" value="1">' +
    	'<br>' +
    	'How much time should is permitted on this question? (0 for no limit)' +
    	'<input type="text" name="timeLimit" value="0">' +
    	'<br>';
    var questionBody = document.getElementById('QuestionForm');
    
    switch(+index){
      case 0:
       document.getElementById('type').setAttribute('value','Question-Response');
        break;
      case 1:
    	  
        document.getElementById('type').setAttribute('value','Fill in the Blank');
        questionHTML+='Replace the blanks in your question with "@@@@"';
        questionHTML+='<br>';
        break;
      case 2:
        document.getElementById('type').setAttribute('value','Multiple Choice');
        questionHTML+='How many choices are there?' +
        '<input type="text" name="numChoices">' +
        '<br>';
        break;
      case 3:
        document.getElementById('type').setAttribute('value','Picture Response');
        questionHTML+='What url can we find the image at?' +
        '<input type="text" name="url">' +
        '<br>';
        break;
      case 4:
        document.getElementById('type').setAttribute('value','Matching');
        questionHTML+='How many pairs are there?' +
        '<input type="text" name="numPairs">' +
        '<br>';
        break;
      default:
        console.log("Things have gone wrong");
    }
    questionHTML+='<input type="submit" id="saveQuestion" name="Save Question" value="Save Question">';
    var div = document.getElementById('QuestionData');
    div.innerHTML = questionHTML;
}
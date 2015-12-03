/**
 * 
 */
var addQuestionButton = document.getElementById('addQuestion');
addQuestionButton.onclick = showQuestionHTML;

function showQuestionHTML(){
    var doneOrAddButtonsForm = document.getElementById('Done/AddButtons');
    doneOrAddButtonsForm.innerHTML = '';
    var radioButtonDiv = document.getElementById('RadioButtons');
    radioButtonDiv.innerHTML = 
        'What type of Question do you want to make?' +
        '<br>' +
        '<input type="radio" name="type" value="0">Question-Response' +
        '<br>' +
        '<input type="radio" name="type" value="1">Fill in the Blank' +
        '<br>' +
        '<input type="radio" name="type" value="2">Multiple Choice' +
        '<br>' +
        '<input type="radio" name="type" value="3">Picture Response' +
        '<br>' +
        '<input type="radio" name="type" value="4">Matching' +
        '<br>';
    var typeRadios = document.getElementsByName('type');
    for (var i = 0; i < typeRadios.length; i++){
    	console.log(typeRadios[i].name);
    	typeRadios[i].onclick = radioOnClick;
    }
    function radioOnClick(){
    	var index = this.value;
        var questionHTML = 'What Question Would You Like to Ask?' +
        	'<br>' +
        	'<input type="text" name="question" id="question">' +
        	'<br>' +
        	'How much is this question worth?' +
        	'<input type="text" name="score" id="score">' +
        	'<br>';
        var questionBody = document.getElementById('QuestionForm');
        
        switch(+index){
          case 0:
           document.getElementById('type').setAttribute('value','Response');
            break;
          case 1:
            document.getElementById('type').setAttribute('value','Blank');
            questionHTML+='Replace the blanks in your question with "@@@@"';
            questionHTML+='<br>';
            break;
          case 2:
            document.getElementById('type').setAttribute('value','MultipleChoice');
            questionHTML+='How many choices are there?' +
            '<input type="text" name="numChoices">' +
            '<br>';
            break;
          case 3:
            document.getElementById('type').setAttribute('value','Picture');
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
}
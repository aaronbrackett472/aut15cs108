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
        '<div class="prereqs"> <div class="placeholder-text"> What type of Question do you want to make? </div></div>' +
        '<div style="padding-top: 20px;"></div><div class="prereqs"> <div class="placeholder-text">' +
        '<p><input type="radio" name="type" value="0">Question-Response</p>' +
        '' +
        '<p><input type="radio" name="type" value="1">Fill in the Blank</p>' +
        '' +
        '<p><input type="radio" name="type" value="2">Multiple Choice</p>' +
        '' +
        '<p><input type="radio" name="type" value="3">Picture Response</p>' +
        '' +
        '<p><input type="radio" name="type" value="4">Matching</p>' +
        '</div></div><div style="padding-top: 40px;"></div>';
    var typeRadios = document.getElementsByName('type');
    for (var i = 0; i < typeRadios.length; i++){
    	console.log(typeRadios[i].name);
    	typeRadios[i].onclick = radioOnClick;
    }
    function radioOnClick(){
    	var index = this.value;
        var questionHTML = '<div class="prereqs"> <div class="placeholder-text">What Question Would You Like to Ask?</div></div>' +
        	'<div style="padding-top: 20px;"></div><div class="placeholder-text">' +
        	'<input type="text" name="question" id="question" class="med-box">' +
        	'</div><div style="padding-top: 20px;"></div>' +
        	'<div class="prereqs"> <div class="placeholder-text">How much is this question worth?</div></div>' +
        	'<div style="padding-top: 20px;"></div><div class="placeholder-text"><input class="med-box" type="text" name="score" id="score">' +
        	'</div><div style="padding-top: 20px;"></div>';
        var questionBody = document.getElementById('QuestionForm');
        
        switch(+index){
          case 0:
           document.getElementById('type').setAttribute('value','Response');
            break;
          case 1:
            document.getElementById('type').setAttribute('value','Blank');
            questionHTML+='<div class="prereqs"> <div class="placeholder-text">Replace the blanks in your question with "@@@@"</div></div>';
            questionHTML+='<br>';
            break;
          case 2:
            document.getElementById('type').setAttribute('value','MultipleChoice');
            questionHTML+='<div class="prereqs"> <div class="placeholder-text">How many choices are there?</div></div>' +
            '<div style="padding-top: 20px;"></div><input type="text" name="numChoices" class="med-box">' +
            '';
            break;
          case 3:
            document.getElementById('type').setAttribute('value','Picture');
            questionHTML+='<div class="prereqs"> <div class="placeholder-text">What url can we find the image at?</div></div>' +
            '<div style="padding-top: 20px;"></div><input type="text" name="url" class="med-box">' +
            '<div style="padding-top: 20px;"></div>';
            break;
          case 4:
            document.getElementById('type').setAttribute('value','Matching');
            questionHTML+='<div class="prereqs"> <div class="placeholder-text">How many pairs are there?</div></div>' +
            '<div style="padding-top: 20px;"></div><input type="text" name="numPairs" class="med-box">' +
            '<div style="padding-top: 20px;"></div>';
            break;
          default:
            console.log("Things have gone wrong");
        }
        questionHTML+='<div class="add-class-container" style="text-align:center;"><button type="submit" id="saveQuestion">Save Question</button></div>';
        var div = document.getElementById('QuestionData');
        div.innerHTML = questionHTML;
    }
}
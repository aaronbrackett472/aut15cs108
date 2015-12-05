var answerHTML = document.getElementById('answerData').innerHTML;
var type = document.getElementById('type').getAttribute('value');
if (type == 'Question-Response' || type == 'Picture Response'){
  answerHTML+='<p>What Answer are you looking for?</p>'+
  '<div style="padding-top: 20px;"></div>'+
  '<input type="text" name="answer" class="med-box">'+
  '<div style="padding-top: 20px;"></div>';
}
if (type == 'Fill in the Blank'){
	answerHTML+='<p>What Answer(s) are you looking for?</p>'+
	'<div style="padding-top: 20px;"></div>';
	var numBlanks = +document.getElementById('number').value;
	for (var i = 0; i < numBlanks; i++){
	  answerHTML+='<input type="text" name="answer" class="med-box">'+
	  '<div style="padding-top: 20px;"></div>';
	}
}
if (type == 'Multiple Choice'){
	answerHTML+='<p>What answers are you looking for? Also, check the correct one(s)</p>'+
	'<div style="padding-top: 20px;"></div>';
	var numChoices = +document.getElementById('number').value;
	for (var i = 0; i < numChoices; i++){
		answerHTML+='<input type="text" name="answer" class="med-box">'+
		'<input type="checkbox" name="correct" value="' + i + '">'+
		'<div style="padding-top: 10px;"></div>';
	}
	var correctRadioButtons = document.getElementsByName('correct');
	for (var i = 0; i < correctRadioButtons.length; i++){
		correctRadioButtons[i].onclick = choiceOnClick;
	}
}
if (type == 'Matching'){
	answerHTML+='Fill in the prompts on the left, and the Answers on the right'+
	'<div style="padding-top: 20px;"></div>';
	var numPairs = +document.getElementById('number').value;
	for (var i = 0; i < numPairs; i++){
		answerHTML+='<input type="text" name="prompt" class="small-box"><input type="text" name="answer" class="small-box"><br>'+
		'<div style="padding-top: 10px;"></div>';
	}
}
if (type == 'List'){
	answerHTML+='<p>Fill in the expected items of the list. If one item can be multiple acceptable answers, separate them in the same box with semicolons. If your list is ordered, enter them in order.</p>'+
	'<div style="padding-top: 20px;"></div>';
	var numItems = +document.getElementById('number').value;
	for (var i = 0; i < numItems; i++){
	  answerHTML+='<input type="text" name="answer" class="med-box">'+
	  '<div style="padding-top: 20px;"></div>';
	}
}
answerHTML+='<div class="add-class-container" style="text-align:center;"><button type="submit" name="answerSumbit">Add this Answer!</button></div>';
document.getElementById('answerData').innerHTML = answerHTML;

function choiceOnClick(){
	var correctChoices = document.getElementById('correctChoices').getAttribute('value');
	if (this.checked){
		if (correctChoices.indexOf(this.value+';') == -1){
			correctChoices+=this.value+';';
		}
	}
	else if (!this.checked){
		if (correctChoices.indexOf(this.value+';') != -1){
			correctChoices.replace(this.value+';', '');
		}
	}
	document.getElementById('correctChoices').setAttribute('value', correctChoices);
}

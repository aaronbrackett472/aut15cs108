var answerHTML = document.getElementById('answerData').innerHTML;
var type = document.getElementById('type').getAttribute('value');
if (type == 'Question-Response'){
  answerHTML+='<p>What Answer are you looking for?</p>'+
  '<div style="padding-top: 20px;"></div>'+
  '<input type="text" name="answer" class="med-box">'+
  '<div style="padding-top: 20px;"></div>';
}
if (type == 'Fill in the Blank' || type == 'Picture Response'){
	answerHTML+='<p>What Answer(s) are you looking for? Separate multiple blanks with Semicolons.</p>'+
	'<div style="padding-top: 20px;"></div>';
	var numBlanks = +document.getElementById('number').value;
	for (var i = 0; i < numBlanks; i++){
	  answerHTML+='<input type="text" name="answer" value="default" class="med-box">'+
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

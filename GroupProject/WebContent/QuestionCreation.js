/**
 * 
 */
var typeRadios = document.forms['Questions'].elements['type'];
for (var i = 0; i < typeRadios.length; i++){
  typeRadios[i].onClick = function(i){
    var questionHTML = 'What Question Would You Like to Ask?\n
    <input type="text" id="question">\n
    <br>\n
    How much is this question worth?
    <input type="text" id="score" value="1">\n
    How much time should is permitted on this question? (0 for no limit)
    <input type="text" name="score" value="0">\n';
    var questionBody = document.getElementById('Question');
    switch(i){
      case 0:
        /*questionHTML+='What Answer are you looking for?\n
        <input type="text" name="answer">\n
        <br>\n';*/
        document.getElementById('type').setAttribute('value','Question-Response');
        break;
      case 1:
        document.getElementById('type').setAttribute('value','Fill in the Blank');
        questionHTML+='Replace the blanks in your question with "@@@@"\n
        <br>\n'
        break;
      case 2:
        document.getElementById('type').setAttribute('value','Multiple Choice');
        questionHTML+='How many choices are there?
        <input type="text" name="numChoices">\n
        <br>\n'
        break;
      case 3:
        document.getElementById('type').setAttribute('value','Picture Response');
        questionHTML+='What url can we find the image at?\n
        <input type="text" name="url">\n
        <br>\n'
        break;
      case 4:
        document.getElementById('type').setAttribute('value','Matching');
        questionHTML+='How many pairs are there?
        <input type="text" name="numPairs">\n
        <br>\n'
        break;
      default:
        console.log("Things have gone wrong");
    }
    questionHTML+='<input type="submit" id="saveQuestion" name="Save Question" value="Save Question">\n'
    questionBody.innerHTML = questionHTML;
  }
}
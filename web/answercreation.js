 switch(i){
        case 0:
          questionHTML+='What Answer are you looking for?\n
          <input type="text" name="answer">\n
          <br>\n';
          break;
        case 1:
          questionHTML+='Replace the blanks in your question with "@@@@"\n
          <br>\n
          What Answer are you looking for? Separate multiple blanks with Semicolons.\n
          <input type="text" name="answer">\n
          <br>\n';
          break;
        case 2:
          questionHTML+='What answers are you looking for? Also, check the correct one\n
          <input type="text" name="answer">
          <input type="radio" name="correct" checked>\n
          <button type="button" name="addChoice">Add Another Choice</button>\n
          <br>\n';
          var addChoiceButtonListener = true;
          break;
        case 3:
          questionHTML+='What url can we find the image at?\n
          <input type="text" name="url">\n
          <br>\n
          What Answer are you looking for?\n
          <input type="text" name="answer">\n
          <br>\n'
          break;
        case 4:
          questionHTML+='Fill in the prompts on the left, and the Answers on the right\n
          <input type="text" name="prompt"><input type="text" name="answer">\n
          <input type="text" name="prompt"><input type="text" name="answer">\n
          <button type="button" name="addPair">Add Another Pair</button>\n
          <br>\n'
          var addPairButtonListener = true;
          break;
        default:
          console.log("Things have gone wrong");
      }


          if (addChoiceButtonListener==true){
      var choiceButton = document.buttons["addChoice"];
      choiceButton.onClick = function(){
        var currentHTML = questionBody.innerHTML;
        currentHTML+='<input type="text" name="answer">
        <input type="radio" name="correct" checked>\n
        <button type="button" name="addChoice">Add Another Choice</button>\n
        <br>\n'
      }
    }
    if (addPairButtonListener==true){
      var pairButton = document.buttons["addPair"];
      pairButton.onClick = function(){
        var currentHTML = questionBody.innerHTML;
        currentHTML+='Fill in the prompts on the left, and the Answers on the right\n
        <input type="text" name="prompt"><input type="text" name="answer">\n
        <input type="text" name="prompt"><input type="text" name="answer">\n
        <button type="button" name="addPair">Add Another Pair</button>\n
        <br>\n';
      }
    }

            What Answer are you looking for? Separate multiple blanks with Semicolons.\n
        <input type="text" name="answer">\n
        <br>\n';


      case 0:
        questionHTML+='What Answer are you looking for?\n
        <input type="text" name="answer">\n
        <br>\n';
        break;

         questionHTML+='Fill in the prompts on the left, and the Answers on the right\n
        <input type="text" name="prompt"><input type="text" name="answer">\n
        <input type="text" name="prompt"><input type="text" name="answer">\n
        <button type="button" name="addPair">Add Another Pair</button>\n
        <br>\n'
        var addPairButtonListener = true;
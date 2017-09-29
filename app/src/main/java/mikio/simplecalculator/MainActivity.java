package mikio.simplecalculator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity implements View.OnClickListener
    {
        private StringBuilder inputString = new StringBuilder();
        private TextView tvDisplay;
        //TODO
        //  beim drücken auf "=" soll inputsring bleiben und
        // in neuerzeile "="+result erscheinen (Verlauf)
        //Klammern und +- muss in die inputValidierung aufgenommen werden
        //weitere Eingabefehler abfangen
        // alles in so kleine Methoden wie möglich auslagern, was auslagerbar ist
        //eventuell Teile in Kassen auslagern

        //SimpleCalc Version
        //Display mit History
        //Design

        @Override
        protected void onCreate(Bundle savedInstanceState)
            {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
                tvDisplay = (TextView) findViewById(R.id.tvDisplay);
            }
        @Override
        protected void onResume()
            {
                super.onResume();

                findViewById(R.id.buttonClear).setOnClickListener(this);
                findViewById(R.id.buttonDivide).setOnClickListener(this);
                findViewById(R.id.buttonMultiply).setOnClickListener(this);
                findViewById(R.id.buttonBack).setOnClickListener(this);
                findViewById(R.id.buttonSubstract).setOnClickListener(this);
                findViewById(R.id.buttonAdd).setOnClickListener(this);
                findViewById(R.id.buttonEquals).setOnClickListener(this);
                findViewById(R.id.buttonDecimal).setOnClickListener(this);

                findViewById(R.id.button0).setOnClickListener(this);
                findViewById(R.id.button1).setOnClickListener(this);
                findViewById(R.id.button2).setOnClickListener(this);
                findViewById(R.id.button3).setOnClickListener(this);
                findViewById(R.id.button4).setOnClickListener(this);
                findViewById(R.id.button5).setOnClickListener(this);
                findViewById(R.id.button6).setOnClickListener(this);
                findViewById(R.id.button7).setOnClickListener(this);
                findViewById(R.id.button8).setOnClickListener(this);
                findViewById(R.id.button9).setOnClickListener(this);
            }
        @Override
        protected void onPause()
            {
                findViewById(R.id.buttonClear).setOnClickListener(null);
                findViewById(R.id.buttonDivide).setOnClickListener(null);
                findViewById(R.id.buttonMultiply).setOnClickListener(null);
                findViewById(R.id.buttonBack).setOnClickListener(null);
                findViewById(R.id.buttonSubstract).setOnClickListener(null);
                findViewById(R.id.buttonAdd).setOnClickListener(null);
                findViewById(R.id.buttonEquals).setOnClickListener(null);
                findViewById(R.id.buttonDecimal).setOnClickListener(null);

                findViewById(R.id.button0).setOnClickListener(null);
                findViewById(R.id.button1).setOnClickListener(null);
                findViewById(R.id.button2).setOnClickListener(null);
                findViewById(R.id.button3).setOnClickListener(null);
                findViewById(R.id.button4).setOnClickListener(null);
                findViewById(R.id.button5).setOnClickListener(null);
                findViewById(R.id.button6).setOnClickListener(null);
                findViewById(R.id.button7).setOnClickListener(null);
                findViewById(R.id.button8).setOnClickListener(null);
                findViewById(R.id.button9).setOnClickListener(null);

                super.onPause();
            }
        @Override
        public void onClick(View view)
            {
                switch (view.getId()){
                    case R.id.buttonClear:
                        emptyInputString();
                        updateDisplay(inputString.toString());
                        break;
                    case R.id.buttonDivide:
                        updateDisplay(getDisplayContent() + "/");
                        break;
                    case R.id.buttonMultiply:
                        updateDisplay(getDisplayContent() + "*");
                        break;
                    case R.id.buttonBack:
                        inputString.append(getDisplayContent());
                        int lenght = inputString.length();
                        if (lenght != 0){
                            inputString.delete(lenght-1, lenght);
                            updateDisplay(inputString.toString());
                            emptyInputString();
                        }
                        break;
                    case R.id.buttonSubstract:
                        updateDisplay(getDisplayContent() + "-");
                        break;
                    case R.id.buttonAdd:
                        updateDisplay(getDisplayContent()+"+");
                        break;
                    case R.id.buttonEquals:
                        inputString.append(getDisplayContent());
                        evaluateInputString();
                        break;
                    case R.id.buttonDecimal:
                        tvDisplay.setText(tvDisplay.getText() + ".");
                        break;

                    case R.id.button0:
                        tvDisplay.setText(tvDisplay.getText() + "0");
                        break;
                    case R.id.button1:
                        tvDisplay.setText(tvDisplay.getText() + "1");
                        break;
                    case R.id.button2:
                        tvDisplay.setText(tvDisplay.getText() + "2");
                        break;
                    case R.id.button3:
                        tvDisplay.setText(tvDisplay.getText() + "3");
                        break;
                    case R.id.button4:
                        tvDisplay.setText(tvDisplay.getText() + "4");
                        break;
                    case R.id.button5:
                        tvDisplay.setText(tvDisplay.getText() + "5");
                        break;
                    case R.id.button6:
                        tvDisplay.setText(tvDisplay.getText() + "6");
                        break;
                    case R.id.button7:
                        tvDisplay.setText(tvDisplay.getText() + "7");
                        break;
                    case R.id.button8:
                        tvDisplay.setText(tvDisplay.getText() + "8");
                        break;
                    case R.id.button9:
                        tvDisplay.setText(tvDisplay.getText() + "9");
                        break;
                }
            }
        public void evaluateInputString(){
            if(!(isValidInput(inputString.toString()))){
                showError("Invalid input. Please correct.");
                return;
            }

            char inputChar = ' ';
            boolean operationHasBeenAdded = false;
            int counter =0;

            StringBuilder operandString = new StringBuilder();
            List<Double> operandList = new ArrayList<Double>();
            List<String> operationList = new ArrayList<String>();

            for (int i = 0;i<inputString.length();i++){
                //handle case if first numeral is signed
                if(i==0 && hasSign(inputString.toString())){
                    setOperandString(operandString, inputString.charAt(0));
                    for (int j =1; i<inputString.length();j++){
                        if(!isOperator(inputString.charAt(j))) setOperandString(operandString, inputString.charAt(j));
                        else {
                            i=j;
                            break;
                        }
                    }
                }
                inputChar = inputString.charAt(i);
                if(isOperator(inputChar)){
                    operationList.add(counter,String.valueOf(inputChar));
                    operationHasBeenAdded=true;
                }
                else if(!isOperator(inputChar) && (Character.isDigit(inputChar) || isDecimalDot(inputChar))){
                    if(operationHasBeenAdded){
                        setOperandList(operandList, operandString, counter);
                        counter++;
                        operationHasBeenAdded=false;
                    }
                    setOperandString(operandString, inputChar);
                }
                else showError("Invalid input.");
            }
            operandList.add(counter, Double.valueOf(operandString.toString()));
            operandString.setLength(0);
            calculateResult(operandList, operationList);
        }
        public void calculateResult(List<Double> operandList, List<String> operationList){
            double result = 0;
            for (int i =0; i<operationList.size();i++){
                switch (operationList.get(i)){
                    case "+":
                        if(i==0) result = operandList.get(i) + operandList.get(i+1);
                        else result += operandList.get(i+1);
                        break;
                    case "*":
                        if(i==0) result = operandList.get(i) * operandList.get(i+1);
                        else result *= operandList.get(i+1);
                        break;
                    case "-":
                        if(i==0) result = operandList.get(i) - operandList.get(i+1);
                        else result -= operandList.get(i+1);
                        break;
                    case "/":
                        if(i==0) {
                            if(operandList.get(i+1) != 0) result = operandList.get(i) / operandList.get(i+1);
                            else showError("Division by zero is not possible.");
                        }
                        else {
                            if(operandList.get(i+1) != 0) result /= operandList.get(i+1);
                            else showError("Division by zero is not possible.");
                        }
                        break;
                }
            }
            updateDisplay(String.valueOf(result));
            emptyInputString();
        }
        public void setOperandList(List<Double> operandList, StringBuilder operandString, int counter){
            // only reference types can be setted in a void method
            operandList.add(counter,Double.valueOf(operandString.toString()));
            emptyOperandString(operandString);
        }
        public void setOperandString(StringBuilder operandString, char inputChar){
            operandString.append(inputChar);
        }
        public void emptyOperandString(StringBuilder operandString){
            //empties StringBuilderObject. Not = null because of InvocationTargetException
            operandString.setLength(0);
        }
        public void emptyInputString(){
            inputString.setLength(0);
        }
        public void updateDisplay(String content){
            tvDisplay.setText(content);
        }
        public void showError(String content){
            Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
        }

        public boolean hasSign(String input){
            boolean hasSign = false;
            if(input.length()>0 && (input.charAt(0) == '+' || input.charAt(0) == '-')){
                hasSign=true;
            }
            return hasSign;
        }
        public boolean isOperator(char inputChar){
            boolean isOperator =false;
            if(inputChar == '+' || inputChar == '*' || inputChar == '-' || inputChar == '/'){
                isOperator=true;
            }
            return isOperator;
        }
        public boolean isDecimalDot(char inputChar){
            boolean isDecimalDot = false;
            if (inputChar == '.') isDecimalDot = true;
            return isDecimalDot;
        }
        public boolean isValidInput(String inputString){
            boolean isValidInput = false;
            char inputChar=' ';
            char precursor=' ';

            for (int i =0; i<inputString.length();i++){
                inputChar = inputString.charAt(i);
                if (i>0) {
                    precursor = inputString.charAt(i-1);
                }
                // Forbidden Inputcases: ++;  3+; 2..9; ..; Any Character except "+-*/.0..9()"
                // )9-8; /0;
                // correct: -3; +4;

                if(isValidDouble(String.valueOf(inputChar))){
                    isValidInput=true;
                }
                else if(isOperator(inputChar) && i==0){
                    if(inputChar=='-' || inputChar=='+') isValidInput=true;
                    else {
                        isValidInput=false;
                        break;
                    }
                }
                else if(isOperator(inputChar) && i==inputString.length()){
                    isValidInput=false;
                    break;
                }
                else if (isOperator(precursor) && isOperator(inputChar)){
                    isValidInput=false;
                    break;
                }
                else if (isDecimalDot(precursor) && isOperator(inputChar)){
                    isValidInput=false;
                    break;
                }
                else {
                        isValidInput=true;
                    }
            }
            return isValidInput;
        }
        public boolean isValidDouble(String number){
            boolean isValidDouble=false;
            for (int i=0;i<number.length();i++){
                if (i==0 && Character.isDigit(number.charAt(0))){
                    isValidDouble=true;
                }
                else if (i!=0 && i!= number.length()-1 && (Character.isDigit(number.charAt(i)) || isDecimalDot(number.charAt(i)))){
                    isValidDouble =true;
                }
                else if(i == number.length()-1 && Character.isDigit(number.charAt(i))){
                    isValidDouble=true;
                }
                else isValidDouble = false;
            }
            return isValidDouble;
        }

        public String getDisplayContent(){
            return tvDisplay.getText().toString();
        }

    }

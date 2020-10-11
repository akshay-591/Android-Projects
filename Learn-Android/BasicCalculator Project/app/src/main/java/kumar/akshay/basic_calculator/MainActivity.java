package kumar.akshay.basic_calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    /* Declaring instance variables of Button,TextView,String andDouble class */
    private Button add, sub, multi, one, two, three, four, five, six, seven, eight, nine, zero, dot, divide, clear, equal;

    private TextView resultview, endview;
    private String result = "", operator = "", newvalue1 = "", newvalue2 = "", printvalue = "";
    private Double value1 = null;
    private Double value2 = null;
    private Double res = null;
//@Overriding onCreate Method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //calling Super method and passing Current State
        super.onCreate(savedInstanceState);

        //Attaching layout
        setContentView(R.layout.activity_main);

        //attaching view objects id to activity objects(instance Button Variables)
        /*Attaching TextView Id to activity objects(Instance variable)*/
        resultview = (TextView) findViewById(R.id.resultview);
        endview = (TextView) findViewById(R.id.endview);
      /*Attching operator Buttons Id to activity objects (instance variable)*/
        add = (Button) findViewById(R.id.add);
        sub = (Button) findViewById(R.id.sub);
        multi = (Button) findViewById(R.id.multi);
        divide = (Button) findViewById(R.id.divide);
        equal = (Button) findViewById(R.id.equal);

        /*Attaching Number Button Id to activity  objects(instacne Button variables)*/
        one = (Button) findViewById(R.id.one);
        two = (Button) findViewById(R.id.two);
        three = (Button) findViewById(R.id.three);
        four = (Button) findViewById(R.id.four);
        five = (Button) findViewById(R.id.five);
        six = (Button) findViewById(R.id.six);
        seven = (Button) findViewById(R.id.seven);
        eight = (Button) findViewById(R.id.eight);
        nine = (Button) findViewById(R.id.nine);
        zero = (Button) findViewById(R.id.zero);
        dot = (Button) findViewById(R.id.dot);
        // back=(Button)findViewById(R.id.back);
        //Attaching Clear Screen Button Id for Clearing Text view
        clear = (Button) findViewById(R.id.clear);
        /*calling onClick() Method of OnClickListener interface Class By creating a Refernce to
         it for Nuber Buttons */

        View.OnClickListener number = new View.OnClickListener() {
            //this Listeners will only work for Number Buttons and Dot Button
            @Override
            public void onClick(View v) {
                /*casting Data of clickd Button from "v" reference of View class and Storing
                it in Button Objeect so that it can be recognized properly*/
                Button b = (Button) v;
                /*Cheking if operator is pressed or not if not then Data will be Stored in
                 "newValue1" variable else(if operator is present before new value) data will be stored in newvalue2*/

                if (operator.equals("")) {
                    //taking button text (ex-1or2or3) from 'b' refernce of Button class and adding it in String printvalue to print it on resultView screen

                   // taking button text (ex-1or2or3) from 'b' refernce of Button class and Storing it in 'newvalue1' String for Calculation purpose
                    if (newvalue1.equals("")&&b.getText().toString().equals("."))
                    {
                        newvalue1+=("0"+b.getText().toString());
                        printvalue +=("0"+ b.getText().toString());
                        resultview.setText(printvalue);
                    }
                    else {
                        newvalue1 += b.getText().toString();
                        printvalue +=b.getText().toString();
                        resultview.setText(printvalue);
                    }
                }
                else {
                    if (newvalue1.equals("")&&b.getText().toString().equals("."))
                    {
                        newvalue2+="0"+b.getText().toString();
                        resultview.setText(printvalue + operator + newvalue2);
                    }
                    else {
                        newvalue2 += b.getText().toString();
                        resultview.setText(printvalue + operator + newvalue2)=-09we4
                    }

                }
            }
        };
//caling setOnClickListener() method of Button Class and passing object Reference of OnClickListener Class
        //indirectly we creating an action when Number Buttons are pressed
        one.setOnClickListener(number);
        two.setOnClickListener(number);
        three.setOnClickListener(number);
        four.setOnClickListener(number);
        five.setOnClickListener(number);
        six.setOnClickListener(number);
        seven.setOnClickListener(number);
        eight.setOnClickListener(number);
        nine.setOnClickListener(number);
        zero.setOnClickListener(number);
        dot.setOnClickListener(number);

//now /*calling onClick() Method of OnClickListener interface Class By creating a object Refernce to
//         it for operator Buttons */
        View.OnClickListener op = new View.OnClickListener() {
            //this Listeners will only work for operator Buttons and Dot Button
            @Override
            public void onClick(View v) {
                /*casting data of clickd Button from reference "v"  of View class and Storing
                it in Button Object reference so that it can be recognized properly*/
                Button b2 = (Button) v;
                if (newvalue1.equals(".")||newvalue2.equals("."))
                {
                    return;
                }
                if (resultview.getText().toString().equals("")) {
                    //if text view is empty and operetor button is pressed it will automaticaly add '0' before operator and also into newvalue1
                    //reset operator String so that new operator not get merged with new one
                    operator = "";
                    //storing new operator in operator String
                    operator = b2.getText().toString();
                    //adding '0' in printvalue String to display on resulttextview
                    printvalue += "0";
                    //adding '0' to new value
                    newvalue1 += "0";
                    resultview.setText(printvalue + operator);
                    //since operator is already pressed so we will add newvalue1 to value1
                    //new number will be added to newvalue2
                    value1 = Double.parseDouble(newvalue1);
                }
                //checking if operator empty or not and newvalue2 is empty or not
                // if both condition pass it will take us to operation () method where calculation will be done
                //this condition act when operator will be pressed after entring second value so first calculation will be done so that
                //whatever number we entered next it will store as a new number
            else if (!operator.equals("") && !newvalue2.equals("")) {
                //storing newvalue2 in value2 variable
                    value2 = Double.parseDouble(newvalue2);
                    //addding both operator and newvalue to printvalue to string so that both can be displayed on resulttextview
                    printvalue += operator + newvalue2;
                    //reserting newvalue2 string to store new value
                    newvalue2 = "";
                    //reseting result string to store new result
                    result = "";
                    //calling operation() method to do calcution according to operatore entered before newvalue2
                    result = operation();
                    //reseting operator to store new operator
                    operator = "";
                    //storing new operator which get eneterd after newvalue2
                    operator = b2.getText().toString();
                    //displying all data in resulttextview
                    resultview.setText(printvalue + operator);
                    //displaying result in endview
                    endview.setText(result);

                }
            //if above both condition fails this block of statements will act
            else {
                //operetor reset
                    operator = "";
                    //new operator stored
                    operator = b2.getText().toString();
                    //newvalue1 will get stored in value1
                    value1 = Double.parseDouble(newvalue1);
                    //displaying data
                    resultview.setText(printvalue + operator);
                }

            }
        };
       //caling setOnClickListener() method of Button Class and passing object Reference of OnClickListener Class
        //indirectly we creating an action when operetor Buttons are pressed
        add.setOnClickListener(op);
        sub.setOnClickListener(op);
        multi.setOnClickListener(op);
        divide.setOnClickListener(op);
//creting action when clear Button (C) is pressed
        //it will reset all String and value variable
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resultview.setText("");
                endview.setText("");
                newvalue1 = "";
                newvalue2 = "";
                value1 = null;
                value2 = null;
                res = null;
                printvalue = "";
                operator = "";
                result = "";
            }
        });

    }
//intilizing operation () method
    //its output is String so that it can displayed on endtextview
    private String operation() {
        //working according to operator
        //stroring result in "res", "value1"(so that new value will be act with the result value), newvalue1 variable

        if (operator.equals("+")) {
            res = (value1) + (value2);
            value1 = res;
            newvalue1 = "";
            newvalue1 = res.toString();
        } else if (operator.equals("-")) {
            res = (value1) - (value2);
            value1 = res;
            newvalue1 = "";
            newvalue1 = res.toString();
        } else if (operator.equals("*")) {
            res = (value1) * (value2);
            value1 = res;
            newvalue1 = "";
            newvalue1 = res.toString();
        } else if (operator.equals("/")) {
            res = (value1) / (value2);
            value1 = res;
            newvalue1 = "";
            newvalue1 = res.toString();
        }

        //res is converting into String by toString() method for returning it
        return res.toString();
    }


}


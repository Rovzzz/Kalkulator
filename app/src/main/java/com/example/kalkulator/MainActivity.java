package com.example.kalkulator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView resultField; // текстовое поле для вывода результата
    EditText numberField;   // поле для ввода числа
    TextView operationField;    // текстовое поле для вывода знака операции
    Double operand = null;  // операнд операции
    String lastOperation = "="; // последняя операция
    Spinner spinner;
    String[] str_array;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // получаем все поля по id из activity_main.xml
        resultField = findViewById(R.id.resultField);
        numberField = findViewById(R.id.numberField);
        operationField = findViewById(R.id.operationField);
        spinner = findViewById(R.id.spinner);
        str_array = new String[10];
        for (int i = 0;i<str_array.length;i++){
            str_array[i] = "";
        }
    }
    // сохранение состояния
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("OPERATION", lastOperation);
        if(operand!=null)
            outState.putDouble("OPERAND", operand);
        super.onSaveInstanceState(outState);
    }
    // получение ранее сохраненного состояния
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        lastOperation = savedInstanceState.getString("OPERATION");
        operand= savedInstanceState.getDouble("OPERAND");
        resultField.setText(operand.toString());
        operationField.setText(lastOperation);
    }
    // обработка нажатия на числовую кнопку
    public void onNumberClick(View view){

        Button button = (Button)view;
        numberField.append(button.getText());

        if(lastOperation.equals("=") && operand!=null){
            operand = null;
        }
    }
    // обработка нажатия на кнопку операции
    public void onOperationClick(View view){

        Button button = (Button)view;
        String op = button.getText().toString();
        String number = numberField.getText().toString();
        // если введенно что-нибудь
        if(number.length()>0){
            number = number.replace(',', '.');
            try{
                performOperation(Double.valueOf(number), op);
            }catch (NumberFormatException ex){
                numberField.setText("");
            }
        }
        lastOperation = op;
        operationField.setText(lastOperation);
    }

    private void performOperation(Double number, String operation){

        // если операнд ранее не был установлен (при вводе самой первой операции)
        if(operand ==null){
            operand = number;
        }
        else{
            if(lastOperation.equals("=")){
                lastOperation = operation;
            }
            switch(lastOperation){
                case "=":
                     operand =number;
                    ArrayAdapter<String> adapter_1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,str_array);
                    spinner.setAdapter(adapter_1);
                    break;
                case "/":
                    if(number==0){
                        operand =0.0;
                    }
                    else{
                        operand /=number;
                        str_array[0] = String.valueOf(operand);
                        for(int i = 9;i > 0;i--){
                            str_array[i] = str_array[i-1];
                        }
                        adapter_1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,str_array);
                        spinner.setAdapter(adapter_1);
                    }
                    break;
                case "*":
                    operand *=number;
                    str_array[0] = String.valueOf(operand);
                    for(int i = 9;i > 0;i--){
                        str_array[i] = str_array[i-1];
                    }
                    adapter_1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,str_array);
                    spinner.setAdapter(adapter_1);
                    break;
                case "+":
                    operand +=number;
                    str_array[0] = String.valueOf(operand);
                    for(int i = 9;i > 0;i--){
                        str_array[i] = str_array[i-1];
                    }
                    adapter_1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,str_array);
                    spinner.setAdapter(adapter_1);
                    break;
                case "-":
                    operand -=number;
                    str_array[0] = String.valueOf(operand);
                    for(int i = 9;i > 0;i--){
                        str_array[i] = str_array[i-1];
                    }
                    adapter_1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,str_array);
                    spinner.setAdapter(adapter_1);
                    break;

            }
        }
        resultField.setText(operand.toString().replace('.', ','));
        numberField.setText("");
    }

    public void ACclick(View view) {
        resultField.setText("0");
    }

}

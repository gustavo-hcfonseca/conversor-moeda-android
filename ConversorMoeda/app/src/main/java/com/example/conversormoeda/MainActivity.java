package com.example.conversormoeda;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    // declarando de variáveis
    private EditText real, dolar, euro, iene, guarani;
    private EditText campoAtual;
    private boolean isUpdating = false;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //atribuindo os editTexts às respectivas variáveis
        real =  findViewById(R.id.editReal);
        dolar =  findViewById(R.id.editDolar);
        euro =  findViewById(R.id.editEuro);
        iene =  findViewById(R.id.editIene);
        guarani =  findViewById(R.id.editGuarani);
        
        // adicionando o textWatcher aos campos de entrada
        real.addTextChangedListener(textWatcher);
        dolar.addTextChangedListener(textWatcher);
        euro.addTextChangedListener(textWatcher);
        iene.addTextChangedListener(textWatcher);
        guarani.addTextChangedListener(textWatcher);
    }
        //declarando o textWatcher
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!isUpdating) {
                double valorEntrada = 0.0;
                if (!s.toString().isEmpty()) {
                    valorEntrada = Double.parseDouble(s.toString());
                }

                // Definindo os valores dos campos com base na moeda de origem
                campoAtual = (EditText) getCurrentFocus();
                atualizarCampos(valorEntrada);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void atualizarCampos(double valorEntrada) {
        isUpdating = true;

        double taxaDolar = 0.20;
        double taxaEuro = 0.19;
        double taxaGuarani = 1444.59;
        double taxaIene = 29.68;

        if (campoAtual == real) {
            dolar.setText(formatarValor(valorEntrada * taxaDolar));
            euro.setText(formatarValor(valorEntrada * taxaEuro));
            guarani.setText(formatarValor(valorEntrada * taxaGuarani));
            iene.setText(formatarValor(valorEntrada * taxaIene));
        } else if (campoAtual == dolar) {
            real.setText(formatarValor(valorEntrada / taxaDolar));
            euro.setText(formatarValor(valorEntrada * (taxaEuro / taxaDolar)));
            guarani.setText(formatarValor(valorEntrada * (taxaGuarani / taxaDolar)));
            iene.setText(formatarValor(valorEntrada * (taxaIene / taxaDolar)));
        } else if (campoAtual == euro) {
            real.setText(formatarValor(valorEntrada / taxaEuro));
            dolar.setText(formatarValor(valorEntrada / (taxaEuro / taxaDolar)));
            guarani.setText(formatarValor(valorEntrada * (taxaGuarani / taxaEuro)));
            iene.setText(formatarValor(valorEntrada * (taxaIene / taxaEuro)));
        } else if (campoAtual == guarani) {
            real.setText(formatarValor(valorEntrada / taxaGuarani));
            dolar.setText(formatarValor(valorEntrada / (taxaGuarani / taxaDolar)));
            euro.setText(formatarValor(valorEntrada / (taxaGuarani / taxaEuro)));
            iene.setText(formatarValor(valorEntrada * (taxaIene / taxaGuarani)));
        } else if (campoAtual == iene) {
            real.setText(formatarValor(valorEntrada / taxaIene));
            dolar.setText(formatarValor(valorEntrada / (taxaIene / taxaDolar)));
            euro.setText(formatarValor(valorEntrada / (taxaIene / taxaEuro)));
            guarani.setText(formatarValor(valorEntrada / (taxaIene / taxaGuarani)));
        }

        isUpdating = false;
    }


    private String formatarValor(double valor) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return decimalFormat.format(valor);
    }
}
package com.jpblo19.me.coreapp.tools.engines.PrinterZebra;

/**
 * Modified by juan.ortiz on 10/08/2016.
 */
/***********************************************
 * CONFIDENTIAL AND PROPRIETARY
 *
 * The source code and other information contained herein is the confidential and the exclusive property of
 * ZIH Corp. and is subject to the terms and conditions in your end user license agreement.
 * This source code, and any other information contained herein, shall not be copied, reproduced, published,
 * displayed or distributed, in whole or in part, in any medium, by any means, for any purpose except as
 * expressly permitted under such license agreement.
 *
 * Copyright ZIH Corp. 2012
 *
 * ALL RIGHTS RESERVED
 ***********************************************/

import com.jpblo19.me.coreapp.R;
import com.jpblo19.me.coreapp.activities.CoreActivity;
import com.zebra.sdk.printer.PrinterLanguage;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zebra.sdk.comm.BluetoothConnection;
import com.zebra.sdk.comm.Connection;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.printer.ZebraPrinterFactory;
import com.zebra.sdk.printer.ZebraPrinterLanguageUnknownException;


public class PrintConnectivityActivity extends CoreActivity {

    private static String TAG_CLASS = "PRINT_CONNECTIVITY ACTIVITY";
    private Context ctx;
    private ProgressDialog progress;

    private Connection printerConnection;
    private ZebraPrinter printer;
    private TextView statusField;
    private EditText macAddress;
    private Button bto_print;

    private String PRINT_MODE = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_connectivity);

        PRINT_MODE = "TEST";
        tools.Log_i("PRINT MODE ["+PRINT_MODE+"]",TAG_CLASS);

        macAddress = (EditText) this.findViewById(R.id.macInput);
        macAddress.setText(PrintSettingsHelper.getBluetoothAddress(this));

        statusField = (TextView) this.findViewById(R.id.statusText);

        bto_print = (Button) this.findViewById(R.id.bto_print);
        bto_print.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                new Thread(new Runnable() {
                    public void run() {
                        enablePrintButton(false);
                        Looper.prepare();
                        doConnectionTest();
                        Looper.loop();
                        Looper.myLooper().quit();
                    }
                }).start();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (printerConnection != null && printerConnection.isConnected()) {
            disconnect();
        }
    }

    private void enablePrintButton(final boolean enabled) {
        runOnUiThread(new Runnable() {
            public void run() {
                bto_print.setEnabled(enabled);
            }
        });
    }

    public ZebraPrinter connect() {
        setStatus("Conectando...", Color.YELLOW);
        printerConnection = new BluetoothConnection(getMacAddressFieldText());
        PrintSettingsHelper.saveBluetoothAddress(this, getMacAddressFieldText());

        try {
            printerConnection.open();
            setStatus("Conectando...", Color.GREEN);
        } catch (ConnectionException e) {
            setStatus("ERROR - Fallo Conexion", Color.RED);
            PrintSleeper.sleep(1000);
            disconnect();
        }

        ZebraPrinter printer = null;

        if (printerConnection.isConnected()) {
            try {
                printer = ZebraPrinterFactory.getInstance(printerConnection);
                setStatus("Detectando Lenguaje", Color.YELLOW);
                PrinterLanguage pl = printer.getPrinterControlLanguage();
                setStatus("Lenguaje: " + pl, Color.BLUE);
            } catch (ConnectionException e) {
                setStatus("ERROR - Lenguaje Printer", Color.RED);
                printer = null;
                PrintSleeper.sleep(1000);
                disconnect();
            } catch (ZebraPrinterLanguageUnknownException e) {
                setStatus("ERROR - Lenguaje Printer", Color.RED);
                printer = null;
                PrintSleeper.sleep(1000);
                disconnect();
            }
        }

        return printer;
    }

    public void disconnect() {
        try {
            setStatus("Desconectando...", Color.RED);
            if (printerConnection != null) {
                printerConnection.close();
            }
            setStatus("No conectado", Color.RED);
        } catch (ConnectionException e) {
            setStatus("ERROR - Fallo Conexion", Color.RED);
        } finally {
            enablePrintButton(true);
        }
    }

    private void setStatus(final String statusMessage, final int color) {
        runOnUiThread(new Runnable() {
            public void run() {
                statusField.setBackgroundColor(color);
                statusField.setText(statusMessage);
            }
        });
        PrintSleeper.sleep(1000);
    }

    private String getMacAddressFieldText() {
        return macAddress.getText().toString();
    }

    private void doConnectionTest() {
        printer = connect();
        if (printer != null) {
            sendTestLabel();
        } else {
            disconnect();
        }
    }

    private void sendTestLabel() {
        try {
            byte[] configLabel = getConfigLabel();
            printerConnection.write(configLabel);
            setStatus("Enviado datos...", Color.BLUE);
            PrintSleeper.sleep(1500);
            if (printerConnection instanceof BluetoothConnection) {
                String friendlyName = ((BluetoothConnection) printerConnection).getFriendlyName();
                setStatus(friendlyName, Color.MAGENTA);
                PrintSleeper.sleep(500);
            }
        } catch (ConnectionException e) {
            setStatus(e.getMessage(), Color.RED);
        } finally {
            disconnect();
        }
    }

    private byte[] getConfigLabel() {
        String data = "ERROR - NADA QUE IMPRIMIR";

        if(PRINT_MODE.equals("TEST"))
            data = qkcache.PRINT_TEST_RECEIPT;

        byte[] configLabel = data.getBytes();
        return configLabel;
    }

}

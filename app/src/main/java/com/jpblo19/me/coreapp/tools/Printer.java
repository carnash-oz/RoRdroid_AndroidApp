package com.jpblo19.me.coreapp.tools;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Set;
import java.util.UUID;

/**
 * Created by jpblo19 on 6/2/16.
 */
public class Printer extends LogMessage{

    private static String TAG_CLASS = "PRINTER MODULE";

    private BluetoothDevice main_device;

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothSocket main_socket;

    private OutputStream main_outputStream;

    private boolean flag_conn;

    public Printer(){
        Log_i("--------------[PRINT MODULE]-------------", TAG_CLASS);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        flag_conn = false;

        try{
            if(bluetoothAdapter.isEnabled()){
                Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

                if(pairedDevices.size() > 0){

                    for(BluetoothDevice device : pairedDevices){
                        if(device.getName().equals("MP90113")){
                            main_device = device;
                            break;
                        }
                    }

                    UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
                    main_socket = main_device.createRfcommSocketToServiceRecord(uuid);
                    bluetoothAdapter.cancelDiscovery();

                    Log_i("DEVICE BONDSTATE: " + main_device.getBondState(), TAG_CLASS);
                    if(main_device.getBondState() == 12){
                        try {
                            Log_i("DEVICE CONNECTING...", TAG_CLASS);
                            main_socket.connect();
                            main_outputStream = main_socket.getOutputStream();
                            flag_conn = true;
                            Log_i("COMPLETE CONNECTION.", TAG_CLASS);
                        }catch (IOException ei){
                            Log_e("DEVICE SOCKET ERROR. REASON: "+ei,TAG_CLASS);
                        }
                    }else{
                        flag_conn = false;
                        Log_e("DEVICE DISCONNECTED",TAG_CLASS);
                    }
                }else{
                    Log_e("NO BLUETOOTH DEVICE CONNECTED",TAG_CLASS);
                }
            }else{
                Log_e("BLUETOOTH IS OFF",TAG_CLASS);
            }
        }catch (Exception e){
            Log_e("SOMETHING WRONG HAPPEND. REASON: "+e,TAG_CLASS);
        }
    }

    public void printMessage(String data){
        if(flag_conn == true) {

            byte[] buffer_conn = {0x1b, '@'};
            byte[] buffer_data = data.getBytes();

            buffer_conn[1] = (byte) buffer_data.length;

            try {

                Log_i("RUN OUTPUT STREAM. BUFFER CONN SIZE: "+buffer_conn.length, TAG_CLASS);
                for(int i = 0; i < buffer_conn.length; i++) {
                    Log_i("SEND BYTE: "+buffer_conn[i], TAG_CLASS);
                    main_outputStream.write(buffer_conn[i]);
                }

                Log_i("RUN OUTPUT STREAM. BUFFER DATA SIZE: "+buffer_data.length, TAG_CLASS);
                for(int i = 0; i < buffer_data.length; i++) {
                    Log_i("SEND BYTE: "+buffer_data[i], TAG_CLASS);
                    main_outputStream.write(buffer_data[i]);
                }

                main_outputStream.flush();

                ClosePrinterConn();
                Log_i("CLOSE OUTPUT STREAM", TAG_CLASS);

            } catch (Exception e) {
                Log_e("OUTPUT STREAM ERROR. REASON: "+e, TAG_CLASS);
                ClosePrinterConn();
            }

        }else{
            Log_e("NO CONNECTION DETECTED", TAG_CLASS);
        }
    }

    private void ClosePrinterConn(){
        try {
            main_outputStream.close();
            main_socket.close();
            Log_i("CLOSE CONNECTION.", TAG_CLASS);
        } catch (IOException e) {
            Log_e("CAN'T CLOSE CONNECTION. REASON: "+e, TAG_CLASS);
        }
    }

}

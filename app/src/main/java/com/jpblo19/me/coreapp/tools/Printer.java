package com.jpblo19.me.coreapp.tools;

import datamaxoneil.connection.ConnectionBase;
import datamaxoneil.connection.Connection_Bluetooth;
import datamaxoneil.printer.DocumentLP;

/**
 * Created by jpblo19 on 6/2/16.
 */
public class Printer extends LogMessage{

    private static String TAG_CLASS = "PRINTER MODULE";

    private final String KEY_CLIENT_CONN = "00:17:AC:15:F3:00";

    public Printer(){
        Log_i("Instances Module", TAG_CLASS);
        ConnectionBase conn = null;
        try {

            //-----[INIT CONNECTION]-----
            Log_i("Start Connection ID: "+KEY_CLIENT_CONN, TAG_CLASS);
            conn = Connection_Bluetooth.createClient(KEY_CLIENT_CONN);
            conn.open();

            //====Legacy Printers (OC2, OC3, MF4Te, etc.)========//
            Log_i("Instance Printer.", TAG_CLASS);
            DocumentLP docLP = new DocumentLP("!"); //LinePrint mode.“!” is the font name

            //-----[PRINTING]-----------
            Log_i("Creating data message...", TAG_CLASS);
            docLP.writeText("-");
            docLP.writeText(" -");
            docLP.writeText("  -");
            docLP.writeText("   -");
            docLP.writeText("    -");
            docLP.writeText("     -");
            docLP.writeText("      -");
            docLP.writeText("       -");
            docLP.writeText("1234567890abcdefghijklmnopqrstuvwxyz");
            docLP.writeText("---------[BREAKLINE]----------------");
            docLP.writeText("1234567890abcdefghijklmnopqrstuvwxyz");
            docLP.writeText("---------[BREAKLINE]----------------");
            docLP.writeText("1234567890abcdefghijklmnopqrstuvwxyz");
            docLP.writeText("---------[BREAKLINE]----------------");
            docLP.writeText("1234567890abcdefghijklmnopqrstuvwxyz");
            docLP.writeText("-");
            docLP.writeText(" -");
            docLP.writeText("  -");
            docLP.writeText("   -");
            docLP.writeText("    -");
            docLP.writeText("     -");
            docLP.writeText("      -");
            docLP.writeText("       -");

            Log_i("Send data message...", TAG_CLASS);
            conn.write(docLP.getDocumentData());
            conn.close();

            Log_i("Close connection. End Printing", TAG_CLASS);
        } catch (Exception e) {
            Log_i("Printer Instance Error. Reason: "+e, TAG_CLASS);
        }

    }

}

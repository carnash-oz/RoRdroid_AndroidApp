package com.jpblo19.me.coreapp.tools;

import datamaxoneil.connection.ConnectionBase;
import datamaxoneil.connection.Connection_Bluetooth;
import datamaxoneil.connection.Connection_TCP;
import datamaxoneil.printer.DocumentDPL;
import datamaxoneil.printer.DocumentDPL.*;
import datamaxoneil.printer.DocumentEZ;
import datamaxoneil.printer.DocumentLP;
import datamaxoneil.printer.DocumentExPCL_LP;
import datamaxoneil.printer.DocumentExPCL_PP;
import datamaxoneil.printer.DocumentExPCL_PP.*;
import datamaxoneil.printer.ParametersDPL;
import datamaxoneil.printer.ParametersDPL.*;
import datamaxoneil.printer.ParametersEZ;
import datamaxoneil.printer.ParametersExPCL_LP;
import datamaxoneil.printer.ParametersExPCL_LP.*;
import datamaxoneil.printer.ParametersExPCL_PP;
import datamaxoneil.printer.ParametersExPCL_PP.*;
import datamaxoneil.printer.ParametersLP;
import datamaxoneil.printer.UPSMessage;
import datamaxoneil.printer.configuration.dpl.*;
import datamaxoneil.printer.configuration.ez.*;
import datamaxoneil.printer.configuration.expcl.*;

/**
 * Created by jpblo19 on 6/2/16.
 */
public class Printer extends LogMessage{

    private static String TAG_CLASS = "PRINTER MODULE";

    public Printer(){
        Log_i("--------------[PRINT MODULE]-------------", TAG_CLASS);
        ConnectionBase conn = null;
        try {

            //-----[INIT CONNECTION]-----
            conn = Connection_Bluetooth.createClient("00:17:AC:15:F3:00");
            conn.open();

            //-----[JOB QUEUE]-----------
            //====DPL Printers(eg. RL3, RL4, etc.)========//
            DocumentDPL docDPL = new DocumentDPL();
            ParametersDPL paramDPL = new ParametersDPL();

            //====Legacy Printers (OC2, OC3, MF4Te, etc.)========//
            DocumentEZ docEZ = new DocumentEZ("MF204"); //EZ mode. MF204 is the font name
            ParametersEZ paramEZ = new ParametersEZ();

            DocumentLP docLP = new DocumentLP("!"); //LinePrint mode.“!” is the font name
            ParametersLP paramLP = new ParametersLP();

            //====Apex Printers(Apex 2, Apex 3, etc..)========//
            DocumentExPCL_LP docExPCL_LP = new DocumentExPCL_LP(3); //Line Print mode. “3” is the font index.
            ParametersExPCL_LP paramExPCL_LP = new ParametersExPCL_LP();

            DocumentExPCL_PP docExPCL_PP = new DocumentExPCL_PP(PaperWidth.PaperWidth_384); //Page print mode
            ParametersExPCL_PP paramExPCL_PP = new ParametersExPCL_PP();

            //-----[PRINTING]-----------

            docEZ.writeText("DEBUG_LOG - PRINTER TEST: EVT SEND PRINT LAUNCH [EZ]",50,1);
            conn.write(docEZ.getDocumentData());

            docLP.writeText("DEBUG_LOG - PRINTER TEST: EVT SEND PRINT LAUNCH [LP]");
            conn.write(docLP.getDocumentData());


            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

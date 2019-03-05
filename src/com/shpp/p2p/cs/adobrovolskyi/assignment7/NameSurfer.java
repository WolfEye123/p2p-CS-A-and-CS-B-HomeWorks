package com.shpp.p2p.cs.adobrovolskyi.assignment7;

/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 *
 *
 */

import com.shpp.cs.a.simple.SimpleProgram;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * . Artem Dobrovolskyi @2018
 */
public class NameSurfer extends SimpleProgram implements NameSurferConstants {

	/* Method: init() */

    //add name field
    private JTextField nameField;
    //initiates a database
    private NameSurferDataBase dataBase;
    //initiates a canvas
    private NameSurferGraph graph;
    /**
     * This method has the responsibility for reading in the data base
     * and initializing the interactors at the top of the window.
     */
    public void init() {
        JLabel label = new JLabel("Name");
        add(label,NORTH);

        nameField = new JTextField(GRAPH_MARGIN_SIZE);
        nameField.setActionCommand("EnterPressed");
        nameField.addActionListener(this);
        add(nameField, NORTH);

        JButton graphButton = new JButton("Graph");
        add(graphButton, NORTH);
        graphButton.addActionListener(this);

        JButton clearButton = new JButton("Clear");
        add(clearButton, NORTH);
        clearButton.addActionListener(this);


        //add canvas to the window
        graph = new NameSurferGraph();
        add(graph);

        //initiates a database from a file with names and ratings
        dataBase = new NameSurferDataBase(NAMES_DATA_FILE);
    }

	/* Method: actionPerformed(e) */

    /**
     * This class is responsible for detecting when the buttons are
     * clicked, so you will have to define a method to respond to
     * button actions.
     */
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        String name = nameField.getText();

        //setting actions for buttons
        if (cmd.equals("Clear")) {
            graph.clear();
            graph.update();
        } else if (cmd.equals("Graph") || cmd.equals("EnterPressed")){
            NameSurferEntry entry = dataBase.findEntry(name);
            if (entry != null){
                graph.addEntry(entry);
                graph.update();
            }
        }
    }
}

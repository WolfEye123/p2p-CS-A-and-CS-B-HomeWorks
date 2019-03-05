package com.shpp.p2p.cs.adobrovolskyi.assignment7;

/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes
 * or the window is resized.
 */

import acm.graphics.GCanvas;
import acm.graphics.GLabel;
import acm.graphics.GLine;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;

/**
 * . Artem Dobrovolskyi @2018
 */
public class NameSurferGraph extends GCanvas implements NameSurferConstants, ComponentListener {

    //ArrayList for storing objects after processing in the database
    private ArrayList<NameSurferEntry> nameSurfers;
    //application width after update window
    private double width;
    //application height after update window
    private double height;

    /**
     * Creates a new NameSurferGraph object that displays the data.
     */
    public NameSurferGraph() {
        addComponentListener(this);
        nameSurfers = new ArrayList<>();
    }


    /**
     * Clears the list of name surfer entries stored inside this class.
     */
    public void clear() {
        nameSurfers.clear();
    }


    /* Method: addEntry(entry) */

    /**
     * Adds a new NameSurferEntry to the list of entries on the display.
     * Note that this method does not actually draw the graph, but
     * simply stores the entry; the graph is drawn by calling update.
     *
     * @param name
     */
    public void addEntry(NameSurferEntry name) {
        nameSurfers.add(name);
    }


    /**
     * Updates the display image by deleting all the graphical objects
     * from the canvas and then reassembling the display according to
     * the list of entries. Your application must call update after
     * calling either clear or addEntry; update is also called whenever
     * the size of the canvas changes.
     */
    public void update() {
        removeAll();

        //adding lines to create a graph
        addGraphs();

        //adding a graph after the user has entered a name
        if (nameSurfers.size() > 0) {
            for (int i = 0; i < nameSurfers.size(); i++) {
                NameSurferEntry entry = nameSurfers.get(i);
                addEntryGraph(entry, i);
            }
        }
    }

    /**
     * this method draws a graph by calculating the position of the points on the name rating in a certain decade
     * @param entry Object by name on which the graph will be built
     * @param namesNumber number of usernames entered
     */
    private void addEntryGraph(NameSurferEntry entry, int namesNumber) {
        System.out.println(entry.toString());

        //add graph with name and rating
        for (int i = 0; i < NDECADES - 1; i++) {
            String name = entry.getName();
            int rank = entry.getRank(i);
            String rankString = Integer.toString(rank);
            String label = name + " " + rankString;
            int rank1 = entry.getRank(i);
            int rank2 = entry.getRank(i + 1);
            double firstPointRankPosition = GRAPH_MARGIN_SIZE + (getHeight() - GRAPH_MARGIN_SIZE * 2) * rank1 / MAX_RANK;
            double secondPointRankPosition = GRAPH_MARGIN_SIZE + (getHeight() - GRAPH_MARGIN_SIZE * 2) * rank2 / MAX_RANK;
            double yZeroPosition = getHeight() - GRAPH_MARGIN_SIZE;
            double namePointX = i * (getWidth() / NDECADES) + GRAPH_MARGIN_SIZE / 2.0 + NDECADES / 2.0;
            double firstLinePointX = i * (getWidth() / NDECADES) + GRAPH_MARGIN_SIZE / 2.0;
            double secondLinePointX = (i + 1) * (getWidth() / NDECADES) + GRAPH_MARGIN_SIZE / 2.0;
            double namePointY;
            double firstLinePointY = 0;
            double secondLinePointY = 0;

            //adding name and rank to graph
            if (rank != 0) {
                namePointY = GRAPH_MARGIN_SIZE + (getHeight() - GRAPH_MARGIN_SIZE * 2) * rank / MAX_RANK - NDECADES / 2.0;
            } else {
                label = name + " *";
                namePointY = yZeroPosition - NDECADES / 2.0;
            }
            GLabel nameLabel = new GLabel(label, namePointX, namePointY);

            //adding lines by rating to create a graph
            if (rank1 != 0 && rank2 != 0) {
                firstLinePointY = firstPointRankPosition;
                secondLinePointY = secondPointRankPosition;
            } else if (rank1 == 0 && rank2 == 0) {
                firstLinePointY = yZeroPosition;
                secondLinePointY = yZeroPosition;
            } else if (rank1 == 0) {
                firstLinePointY = yZeroPosition;
                secondLinePointY = secondPointRankPosition;
            } else if (rank2 == 0) {
                firstLinePointY = firstPointRankPosition;
                secondLinePointY = yZeroPosition;
            }
            GLine line = new GLine(firstLinePointX, firstLinePointY, secondLinePointX, secondLinePointY);

            //change the color of the lines and names depending on the number of entered names
            if (namesNumber % 4 == 0) {
                nameLabel.setColor(Color.RED);
                line.setColor(Color.RED);
            } else if (namesNumber % 4 == 1) {
                nameLabel.setColor(Color.BLUE);
                line.setColor(Color.BLUE);
            } else if (namesNumber % 4 == 2) {
                nameLabel.setColor(Color.MAGENTA);
                line.setColor(Color.MAGENTA);
            }

            //adding lines and names to the graph
            add(nameLabel);
            add(line);
        }
    }

    /**
     * this method draws lines to create a graph
     */
    private void addGraphs() {
        for (int i = 10, j = 0; i < width; i += width / NDECADES, j++) {
            add(new GLine(i, 0, i, height));
            addYears(i, j);
        }
        add(new GLine(0, GRAPH_MARGIN_SIZE, width, GRAPH_MARGIN_SIZE));
        add(new GLine(0, height - GRAPH_MARGIN_SIZE, width, height - GRAPH_MARGIN_SIZE));
    }

    /**
     * this method adds years of predetermined decades for plotting
     * @param i years x coordinate
     * @param j coefficient for calculating years
     */
    private void addYears(int i, int j) {
        if (j < 10) {
            GLabel years = new GLabel("19" + j + "0");
            years.setLocation(i, height - GRAPH_MARGIN_SIZE + years.getAscent());
            add(years);
        } else {
            String year;
            if (j % 10 == 0) {
                year = "00";
            } else {
                year = "10";
            }
            GLabel years = new GLabel("20" + year);
            years.setLocation(i, height - GRAPH_MARGIN_SIZE + years.getAscent());
            add(years);
        }
    }


    /* Implementation of the ComponentListener interface */
    public void componentHidden(ComponentEvent e) {
    }

    public void componentMoved(ComponentEvent e) {
    }

    public void componentResized(ComponentEvent e) {
        Component canvas = e.getComponent();
        width = canvas.getWidth();
        height = canvas.getHeight();
        update();
    }

    public void componentShown(ComponentEvent e) {
    }
}

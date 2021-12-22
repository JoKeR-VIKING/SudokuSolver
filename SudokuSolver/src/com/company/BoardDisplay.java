package com.company;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BoardDisplay implements ActionListener
{
    JFrame frame;
    JMenuBar menuBar;

    BoardDisplay(boolean isPossible, int[][] board)
    {
        frame = new JFrame();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(9, 9));
        menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        JMenuItem reload = new JMenuItem("New Game");
        reload.addActionListener(this);
        menu.add(reload);
        menuBar.add(menu);

        if (!isPossible)
        {
            JOptionPane.showMessageDialog(frame, "The Board is not possible to solve !");
            return;
        }

        for (int i=0;i<9;i++)
        {
            for (int j=0;j<9;j++)
            {
                JButton button = new JButton(Integer.toString(board[i][j]));
                button.setFocusable(false);
                button.setFocusPainted(false);
                button.setEnabled(false);
                frame.add(button);
            }
        }

        frame.setJMenuBar(menuBar);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent)
    {
        frame.dispose();
        new SudokuSolver();
    }
}
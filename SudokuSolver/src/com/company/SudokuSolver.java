package com.company;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SudokuSolver implements ActionListener
{
    JFrame frame;
    JMenu menu;
    JMenuItem solver;
    JMenuBar menuBar;
    int[][] board;
    Vector<Vector<JComboBox<Integer>>> buttonBoard;

    SudokuSolver()
    {
        frame = new JFrame();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(9, 9));
        menuBar = new JMenuBar();
        solver = new JMenuItem("Start Solving");
        menu = new JMenu("File");
        board = new int[9][9];

        menu.add(solver);
        solver.addActionListener(this);
        buttonBoard = new Vector<>();

        Integer[] numbers = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

        for (int i=0;i<9;i++)
        {
            buttonBoard.add(new Vector<>());

            for (int j=0;j<9;j++)
            {
                JComboBox<Integer> comboBox = new JComboBox<>(numbers);
                buttonBoard.get(i).add(comboBox);
                comboBox.setFocusable(false);
                frame.add(comboBox);
            }
        }

        menuBar.add(menu);
        frame.setJMenuBar(menuBar);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent)
    {
        if (actionEvent.getSource() == solver)
        {
            for (int i=0;i<9;i++)
            {
                for (int j=0;j<9;j++)
                {
                    try
                    {
                        Object obj = buttonBoard.get(i).get(j).getSelectedItem();
                        if (obj != null)
                        {
                            board[i][j] = Integer.parseInt(obj.toString());
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(frame, "Illegal Board is Set!");
                        buttonBoard.clear();
                        return;
                    }
                }
            }

            frame.dispose();

            JFrame loadingFrame = new JFrame();
            loadingFrame.setSize(300, 300);
            loadingFrame.setLocationRelativeTo(null);
            loadingFrame.add(new JLabel("Loading..."));
            loadingFrame.setVisible(true);

            boolean isPossible = solve(0, 0);

            loadingFrame.dispose();
            new BoardDisplay(isPossible, board);
        }
    }

    public boolean solve(int i, int j)
    {
        if (i == 8 && j == 9)
            return true;

        if (j == 9)
        {
            i++;
            j = 0;
        }

        for (int no=1;no<=9;no++)
        {
            if (board[i][j] != 0)
            {
                boolean isPossible = solve(i, j+1);
                if (isPossible)
                    return true;
            }
            else if (isValid(i, j, no))
            {
                board[i][j] = no;
                boolean isPossible = solve(i, j+1);
                if (isPossible)
                    return true;

                board[i][j] = 0;
            }
        }

        return false;
    }

    public boolean isValid(int i, int j, int no)
    {
        for (int x=0;x<9;x++)
        {
            if (board[i][x] == no)
                return false;
        }

        for (int x=0;x<9;x++)
        {
            if (board[x][j] == no)
                return false;
        }

        int x, y;
        if (i < 3)
            x = 0;
        else if (i < 6)
            x = 3;
        else
            x = 6;

        if (j < 3)
            y = 0;
        else if (j < 6)
            y = 3;
        else
            y = 6;

        for (int k=x;k<x+3;k++)
        {
            for (int l=y;l<y+3;l++)
            {
                if (board[k][l] == no)
                    return false;
            }
        }

        return true;
    }
}

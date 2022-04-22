package Mood.Gui.Minigames.impl.TicTac;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToe implements ActionListener
{
    Random random;
    JFrame frame;
    JPanel title_panel;
    JPanel button_panel;
    JLabel textfield;
    JButton[] buttons;
    boolean player1_turn;
    
    public TicTacToe() {
        this.random = new Random();
        this.frame = new JFrame();
        this.title_panel = new JPanel();
        this.button_panel = new JPanel();
        this.textfield = new JLabel();
        this.buttons = new JButton[9];
        this.frame.setDefaultCloseOperation(3);
        this.frame.setSize(800, 800);
        this.frame.getContentPane().setBackground(new Color(50, 50, 50));
        this.frame.setLayout(new BorderLayout());
        this.frame.setVisible(true);
        this.textfield.setBackground(new Color(25, 25, 25));
        this.textfield.setForeground(new Color(25, 255, 0));
        this.textfield.setFont(new Font("Ink Free", 1, 75));
        this.textfield.setHorizontalAlignment(0);
        this.textfield.setText("Tic-Tac-Toe");
        this.textfield.setOpaque(true);
        this.title_panel.setLayout(new BorderLayout());
        this.title_panel.setBounds(0, 0, 800, 100);
        this.button_panel.setLayout(new GridLayout(3, 3));
        this.button_panel.setBackground(new Color(150, 150, 150));
        while (0 < 9) {
            this.buttons[0] = new JButton();
            this.button_panel.add(this.buttons[0]);
            this.buttons[0].setFont(new Font("MV Boli", 1, 120));
            this.buttons[0].setFocusable(false);
            this.buttons[0].addActionListener(this);
            int n = 0;
            ++n;
        }
        this.title_panel.add(this.textfield);
        this.frame.add(this.title_panel, "North");
        this.frame.add(this.button_panel);
        this.firstTurn();
    }
    
    @Override
    public void actionPerformed(final ActionEvent actionEvent) {
        while (0 < 9) {
            if (actionEvent.getSource() == this.buttons[0]) {
                if (this.player1_turn) {
                    if (this.buttons[0].getText() == "") {
                        this.buttons[0].setForeground(new Color(255, 0, 0));
                        this.buttons[0].setText("X");
                        this.player1_turn = false;
                        this.textfield.setText("A K\u00f6r k\u00f6vetkezik.");
                        this.check();
                    }
                }
                else if (this.buttons[0].getText() == "") {
                    this.buttons[0].setForeground(new Color(0, 0, 255));
                    this.buttons[0].setText("O");
                    this.player1_turn = true;
                    this.textfield.setText("Az X k\u00f6vetkezik.");
                    this.check();
                }
            }
            int n = 0;
            ++n;
        }
    }
    
    public void firstTurn() {
        Thread.sleep(2000L);
        if (this.random.nextInt(2) == 0) {
            this.player1_turn = true;
            this.textfield.setText("Az X k\u00f6vetkezik.");
        }
        else {
            this.player1_turn = false;
            this.textfield.setText("A K\u00f6r k\u00f6vetkezik.");
        }
    }
    
    public void check() {
        if (this.buttons[0].getText() == "X" && this.buttons[1].getText() == "X" && this.buttons[2].getText() == "X") {
            this.xWins(0, 1, 2);
        }
        if (this.buttons[3].getText() == "X" && this.buttons[4].getText() == "X" && this.buttons[5].getText() == "X") {
            this.xWins(3, 4, 5);
        }
        if (this.buttons[6].getText() == "X" && this.buttons[7].getText() == "X" && this.buttons[8].getText() == "X") {
            this.xWins(6, 7, 8);
        }
        if (this.buttons[0].getText() == "X" && this.buttons[3].getText() == "X" && this.buttons[6].getText() == "X") {
            this.xWins(0, 3, 6);
        }
        if (this.buttons[1].getText() == "X" && this.buttons[4].getText() == "X" && this.buttons[7].getText() == "X") {
            this.xWins(1, 4, 7);
        }
        if (this.buttons[2].getText() == "X" && this.buttons[5].getText() == "X" && this.buttons[8].getText() == "X") {
            this.xWins(2, 5, 8);
        }
        if (this.buttons[0].getText() == "X" && this.buttons[4].getText() == "X" && this.buttons[8].getText() == "X") {
            this.xWins(0, 4, 8);
        }
        if (this.buttons[2].getText() == "X" && this.buttons[4].getText() == "X" && this.buttons[6].getText() == "X") {
            this.xWins(2, 4, 6);
        }
        if (this.buttons[0].getText() == "O" && this.buttons[1].getText() == "O" && this.buttons[2].getText() == "O") {
            this.oWins(0, 1, 2);
        }
        if (this.buttons[3].getText() == "O" && this.buttons[4].getText() == "O" && this.buttons[5].getText() == "O") {
            this.oWins(3, 4, 5);
        }
        if (this.buttons[6].getText() == "O" && this.buttons[7].getText() == "O" && this.buttons[8].getText() == "O") {
            this.oWins(6, 7, 8);
        }
        if (this.buttons[0].getText() == "O" && this.buttons[3].getText() == "O" && this.buttons[6].getText() == "O") {
            this.oWins(0, 3, 6);
        }
        if (this.buttons[1].getText() == "O" && this.buttons[4].getText() == "O" && this.buttons[7].getText() == "O") {
            this.oWins(1, 4, 7);
        }
        if (this.buttons[2].getText() == "O" && this.buttons[5].getText() == "O" && this.buttons[8].getText() == "O") {
            this.oWins(2, 5, 8);
        }
        if (this.buttons[0].getText() == "O" && this.buttons[4].getText() == "O" && this.buttons[8].getText() == "O") {
            this.oWins(0, 4, 8);
        }
        if (this.buttons[2].getText() == "O" && this.buttons[4].getText() == "O" && this.buttons[6].getText() == "O") {
            this.oWins(2, 4, 6);
        }
    }
    
    public void xWins(final int n, final int n2, final int n3) {
        this.buttons[n].setBackground(Color.GREEN);
        this.buttons[n2].setBackground(Color.GREEN);
        this.buttons[n3].setBackground(Color.GREEN);
        while (0 < 9) {
            this.buttons[0].setEnabled(false);
            int n4 = 0;
            ++n4;
        }
        this.textfield.setText("Az X Gy\u0151z\u00f6tt!");
    }
    
    public void oWins(final int n, final int n2, final int n3) {
        this.buttons[n].setBackground(Color.GREEN);
        this.buttons[n2].setBackground(Color.GREEN);
        this.buttons[n3].setBackground(Color.GREEN);
        while (0 < 9) {
            this.buttons[0].setEnabled(false);
            int n4 = 0;
            ++n4;
        }
        this.textfield.setText("A k\u00f6r Gy\u0151z\u00f6tt!");
    }
}

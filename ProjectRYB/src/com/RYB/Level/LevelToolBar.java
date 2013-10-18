package com.RYB.Level;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;

/**
 * The toolbar used for adding items into a level. 
 * 
 * @author Aaron
 */
public class LevelToolBar {
    public static final String  ENTITY_PLAYER = "Player",
                                ENTITY_END = "End",
                                ENTITY_BLOCK = "Block";
    
    private JDialog toolBar;
    
    private JButton player, end, block;
                    
    
    private JButton red, blue, yellow, gray;
    private Color dimColor = Color.WHITE, defaultColor = Color.DARK_GRAY,
                  dimText = Color.LIGHT_GRAY, defaultText = Color.BLACK;
    
    public LevelToolBar(){
        toolBar = new JDialog();
        toolBar.add(makePanel());
        initDialog();
    }
  
    private JPanel makePanel(){
        //Init Panel
        int rows = 2, cols = 4;
        JPanel tmp = new JPanel();
        tmp.setLayout(new GridLayout(rows, cols));
        
        
        initEntityTools();
        initBlockColorTools();
        
        tmp.add(player);
        tmp.add(end);
        tmp.add(block);
        tmp.add(new JPanel());
        
        tmp.add(red);
        tmp.add(blue);
        tmp.add(yellow);
        tmp.add(gray);
        
        return tmp;
    }
    private void initDialog(){
        toolBar.setTitle("Tools");
        toolBar.pack();
        toolBar.setVisible(true);
        toolBar.setAlwaysOnTop(true);
        toolBar.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        toolBar.setResizable(false);        
    }
    
    private void initEntityTools(){
        player = new JButton("P");
        end = new JButton("E");
        block = new JButton("B");
        
        ActionListener EntityTypeListener = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleEntity(e.getActionCommand());
            }  
        };    
        
        player.addActionListener(EntityTypeListener);
        end.addActionListener(EntityTypeListener);
        block.addActionListener(EntityTypeListener);
        toggleEntity("B");
    }
    private void initBlockColorTools(){
        red = new JButton("R");
        blue = new JButton("B");
        yellow = new JButton("Y");
        gray = new JButton("Gray");
        
        dim(red);
        undim(blue);
        undim(yellow);
        undim(gray);
        
        ActionListener BlockColorListener = new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                toggleColor(e.getActionCommand());
            }
            
        };
        
        red.addActionListener(BlockColorListener);
        blue.addActionListener(BlockColorListener);
        yellow.addActionListener(BlockColorListener);
        gray.addActionListener(BlockColorListener);
    }
    
    private void dim(JButton b){
        b.setBackground(dimColor);
        b.setForeground(dimText);
    }
    private void undim(JButton b){
        b.setBackground(defaultColor);
        b.setForeground(defaultText);
    }
    private boolean isDim(JButton b){
        return b.getBackground().equals(dimColor);
    }
    private void toggleButtonDim(JButton b){
        if (!isDim(b)){
            dim(b);
        }
        else{
            undim(b);
        }
    }
    
    private void toggleColor(String buttonPressed){
        switch (buttonPressed) {
            case "R":
                undim(gray);
                toggleButtonDim(red);
                
                if (!isDim(red) && !isDim(blue) && !isDim(yellow)){
                    dim(red);
                }
                break;
            case "B":
                undim(gray);
                toggleButtonDim(blue);
                
                if (!isDim(red) && !isDim(blue) && !isDim(yellow)){
                    dim(blue);
                }                
                break;
            case "Y":
                undim(gray);
                toggleButtonDim(yellow);
                
                if (!isDim(red) && !isDim(blue) && !isDim(yellow)){
                    dim(yellow);
                }               
                break;
            case "Gray":
                undim(red);
                undim(blue);
                undim(yellow);
                dim(gray);
                break;
            default:
                break;
        }
        
        //Make sure one color is on
        if (!red.isEnabled() && !blue.isEnabled() && !yellow.isEnabled() && !gray.isEnabled()){
            gray.setEnabled(true);
        }
    }    
    private void toggleEntity(String buttonPressed){
        player.setEnabled(true);
        end.setEnabled(true);
        block.setEnabled(true);
        
        switch (buttonPressed) {
            case "P":
                player.setEnabled(false);
                break;
            case "E":
                end.setEnabled(false);
                break;
            case "B":
                block.setEnabled(false);
                break;
            default:
                block.setEnabled(false);
                break;
        }
    }
    
    
    public String getEntityToolSelected(){
        if (!player.isEnabled()){
            return ENTITY_PLAYER;
        }
        else if (!end.isEnabled()){
            return ENTITY_END;
        }
        else if (!block.isEnabled()){
            return ENTITY_BLOCK;
        }
        else{
            return null;
        }
    }
    public String getBlockColorSelected(){
        if (isDim(gray)){
            return "Gray";
        }
        else if (isDim(red) && isDim(yellow) && isDim(blue)){
            return "Block";
        }
        else if (isDim(red) && isDim(yellow)){
            return "Orange";
        }
        else if (isDim(red) && isDim(blue)){
            return "Purple";
        }
        else if (isDim(yellow) && isDim(blue)){
            return "Green";
        }
        else if (isDim(red)){
            return "Red";
        }
        else if (isDim(yellow)){
            return "Yellow";
        }
        else if (isDim(blue)){
            return "Blue";
        }
        else{
            return null;
        }
    }
}

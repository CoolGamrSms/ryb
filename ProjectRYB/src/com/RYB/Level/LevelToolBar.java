package com.RYB.Level;

import java.awt.Dimension;
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
    private JDialog toolBar;
    private JButton player, end, block;
    
    public LevelToolBar(){
        toolBar = new JDialog();
        
        initEntityTools();
        toolBar.add(makePanel());
        initDialog();
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
    private JPanel makePanel(){
        JPanel tmp = new JPanel();
        
        tmp.add(player);
        tmp.add(end);
        tmp.add(block);
        
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
    
    private void toggleEntity(String type){
        player.setEnabled(true);
        end.setEnabled(true);
        block.setEnabled(true);
        
        if (type.equals("P")){
            player.setEnabled(false);
        }
        else if (type.equals("E")){
            end.setEnabled(false);
        }
        else if (type.equals("B")){
            block.setEnabled(false);
        }
        else{
            block.setEnabled(false);
        }
    }
    public String getEntityToolSelected(){
        if (!player.isEnabled()){
            return "Player";
        }
        else if (!end.isEnabled()){
            return "End";
        }
        else if (!block.isEnabled()){
            return "Block";
        }
        else{
            return null;
        }
    }
}

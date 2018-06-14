/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica.Graphics;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author edacal
 */
public class MainPanel extends JPanel{
    private Dimension FrameSize;
    private BufferedImage background;
    private int backgroundHeight;
    private int backgroundWidth;
    private int widthDiv;
    private int heightDiv;
    
    public MainPanel() throws IOException {
        background = ImageIO.read(getClass().getResource("../Resources/grass.jpg"));   
        backgroundHeight = background.getHeight();
        backgroundWidth = background.getWidth();
    }
    
    @Override
    public void paint(Graphics g) {
        FrameSize = this.getSize();
        heightDiv = FrameSize.height / background.getHeight();
        widthDiv  = FrameSize.width / background.getWidth();
        for(int i = 0; i <= widthDiv; ++i){
            for(int j = 0; j <= heightDiv; ++j) {
                
                g.drawImage(background,i*backgroundWidth,j*backgroundHeight,this);
            }
        }
        
        
        //g.drawImage(im,50,50,this);
    }
    
}

package br.com.ifsul.vaporepico.view.component;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class JPanelWithBackground extends JPanel {

   private static final long serialVersionUID = -7087607615716355690L;

   private final Image backgroundImage;

   public JPanelWithBackground(final InputStream file) throws IOException {
      backgroundImage = ImageIO.read(file);
   }

   public void paintComponent(final Graphics g) {
      super.paintComponent(g);

      g.drawImage(backgroundImage, 0, 0, this);
   }
}
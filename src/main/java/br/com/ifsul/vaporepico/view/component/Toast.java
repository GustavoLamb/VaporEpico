package br.com.ifsul.vaporepico.view.component;

import javax.swing.*;
import java.awt.*;

public class Toast extends JFrame {

   private static final long serialVersionUID = 8669439367667668395L;
   private String toastText;

   private final JWindow window;

   public Toast(final String toastText, final Integer x, final Integer y) {

      window = new JWindow();

      window.setBackground(new Color(0, 0, 0, 0));

      final JPanel panel = new JPanel() {

         private static final long serialVersionUID = -7896736240504826867L;

         @Override
         public void paintComponent(final Graphics g) {

            final Integer width = g.getFontMetrics().stringWidth(toastText);
            final Integer heigth = g.getFontMetrics().getHeight();

            g.setColor(Color.BLACK);
            g.fillRect(10, 10, width + 30, heigth + 10);
            g.setColor(Color.BLACK);
            g.drawRect(10, 10, width + 30, heigth + 10);

            g.setColor(new Color(255, 255, 255, 240));
            g.drawString(toastText, 25, 27);

            Integer shadow = 250;

            for (int i = 0; i < 4; i++) {
               shadow -= 60;
               g.setColor(new Color(0, 0, 0, shadow));
               g.drawRect(10 - i, 10 - i, width + 30 + i * 2, heigth + 10 + i * 2);
            }
         }
      };

      window.add(panel);
      window.setLocation(x, y);
      window.setSize(300, 100);
   }

   public void showToast() {

      try {
         window.setOpacity(1);
         window.setVisible(true);

         Thread.sleep(2000);

         for (double d = 1.0; d > 0.2; d -= 0.1) {
            Thread.sleep(100);
            window.setOpacity((float) d);
         }

         window.setVisible(false);

      } catch (final Exception e) {
         e.printStackTrace();
      }
   }
}

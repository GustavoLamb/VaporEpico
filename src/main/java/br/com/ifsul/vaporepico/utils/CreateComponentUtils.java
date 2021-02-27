package br.com.ifsul.vaporepico.utils;

import br.com.ifsul.vaporepico.domain.TipoInput;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.IOException;

import static br.com.ifsul.vaporepico.domain.Cores.TRANSPARENT;
import static br.com.ifsul.vaporepico.domain.Fontes.MONTSERRAT;
import static br.com.ifsul.vaporepico.domain.TipoInput.SENHA;
import static java.util.Objects.isNull;

/* Classe utils para facilitar nas criação dos componentes */

public class CreateComponentUtils {

   public static JLabel createGenericInputIcon(final String imagePath) throws IOException {
      final JLabel label = new JLabel();
      label.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
      label.setIcon(new ImageIcon(ImageIO.read(CreateComponentUtils.class.getResourceAsStream(imagePath))));
      return label;
   }

   public static JTextField createGenericInput(final TipoInput tipo, final String text) {
      final JTextField textField = SENHA == tipo ? new JPasswordField() : new JTextField();
      textField.setFont(MONTSERRAT);
      textField.setForeground(Color.WHITE);
      textField.setText(text);
      textField.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
      textField.setOpaque(false);
      return textField;
   }

   public static JLabel createGenericTextLabel(final String text, final Color textColor, final Font fontConfig) {
      final JLabel label = new JLabel();
      label.setText(text);
      label.setForeground(textColor);
      label.setFont(fontConfig);
      return label;
   }

   public static JButton createGenericButton(final Color bg, final Integer width, final Integer height,
                                             final String text) {
      final JButton button = new JButton();
      button.setText(text);
      button.setForeground(new Color(230, 59, 46));
      button.setFont(MONTSERRAT);
      button.setBorder(BorderFactory.createEmptyBorder());
      button.setPreferredSize(new Dimension(width, height));
      button.setBackground(bg);

      return button;
   }

   public static JPanel createGenericContainer(final Border border, final Color bg, final Integer width,
                                               final Integer height) {
      final JPanel panel = new JPanel();
      panel.setBorder(border);
      if (isNull(bg)) {
         panel.setBackground(TRANSPARENT);
      } else {
         panel.setBackground(bg);
      }
      panel.setPreferredSize(new Dimension(width, height));
      panel.setLayout(new BorderLayout());

      return panel;
   }

}

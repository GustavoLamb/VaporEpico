package br.com.ifsul.vaporepico.view.component;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;

import static br.com.ifsul.vaporepico.domain.Cores.CARD_BACKGROUND;
import static br.com.ifsul.vaporepico.domain.Cores.TRANSPARENT;
import static br.com.ifsul.vaporepico.domain.Fontes.MONTSERRAT;
import static br.com.ifsul.vaporepico.utils.CreateComponentUtils.createGenericTextLabel;

public class JPanelGameCard extends JPanel {
   private static final long serialVersionUID = -2032363420440345327L;

   private JLabel gameImage;
   private JLabel precoLabel;
   private JPanel bottomPanel;

   public JPanelGameCard(final String gameImagePath, final BigDecimal preco,
                         final Long idJogo, final JFrame mainFrame) throws IOException {

      gameImage = new JLabel();
      gameImage.setIcon(new ImageIcon(ImageIO.read(getClass().getResourceAsStream(gameImagePath))));

      final NumberFormat formatter = NumberFormat.getCurrencyInstance();

      precoLabel = createGenericTextLabel(formatter.format(preco.doubleValue()), Color.WHITE, MONTSERRAT);
      precoLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 30));

      bottomPanel = new JPanel();
      bottomPanel.setLayout(new BorderLayout());
      bottomPanel.add(precoLabel, BorderLayout.EAST);
      bottomPanel.setBackground(CARD_BACKGROUND);
      bottomPanel.setPreferredSize(new Dimension(378, 70));


      this.setLayout(new BorderLayout());
      this.setBackground(TRANSPARENT);

      this.setPreferredSize(new Dimension(378, 247));
      this.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
      this.add(gameImage, BorderLayout.NORTH);
      this.add(bottomPanel, BorderLayout.CENTER);
   }

}

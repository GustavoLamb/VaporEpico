package br.com.ifsul.vaporepico.view;

import br.com.ifsul.vaporepico.domain.Cores;
import br.com.ifsul.vaporepico.domain.TipoInput;
import br.com.ifsul.vaporepico.domain.entity.JogosEntity;
import br.com.ifsul.vaporepico.domain.entity.JogosRepository;
import br.com.ifsul.vaporepico.view.component.JPanelGameCard;
import br.com.ifsul.vaporepico.view.component.JPanelWithBackground;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static br.com.ifsul.vaporepico.domain.Fontes.MONTSERRAT;
import static br.com.ifsul.vaporepico.utils.CreateComponentUtils.*;
import static java.lang.String.format;

@Component
public class TerceiroFrame extends JFrame {

   private static final long serialVersionUID = 6789098987073204340L;

   private static final String VAPOR_EPICO = "VaporEpico";
   private static final String BACKGROUND_TEMPLATE = "/background/LojaBackground.png";
   private static final String FLECHA_IMAGE = "/icons/Flecha.png";
   private static final String CESTA = "/icons/Cesta.png";
   private static final String LUPA = "/icons/Search.png";

   @Autowired
   private JogosRepository repository;

   private JTextField searchInput;
   private JLabel searchImage;
   private JLabel carrinhoLabel;
   private JPanel carrinhoContainer;
   private JLabel bibliotecaLabel;
   private JLabel flecha;
   private JPanel searchContainer;
   private JPanel bibliotecaContainer;
   private JPanel topGamePanel;
   private JPanel centerGamePanel;
   private JPanel bottomGamePanel;
   private JPanel centerPanel;
   private JPanel topPanel;
   private JPanel bottomPanel;
   private JPanel background;

   public TerceiroFrame() throws IOException {
      super(VAPOR_EPICO);

      bibliotecaLabel = createGenericTextLabel("BIBLIOTECA", Color.WHITE, MONTSERRAT);

      flecha = new JLabel();
      flecha.setIcon(new ImageIcon(ImageIO.read(getClass().getResourceAsStream(FLECHA_IMAGE))));
      flecha.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 40));
      flecha.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(final MouseEvent e) {
            //TODO abir segundo frame
         }
      });

      final Border simpleBorder = new EmptyBorder(10, 10, 10, 10);
      bibliotecaContainer = createGenericContainer(simpleBorder, Cores.BACKGROUND_PANNEL, 249, 50);
      bibliotecaContainer.add(flecha, BorderLayout.WEST);
      bibliotecaContainer.add(bibliotecaLabel, BorderLayout.CENTER);
      final GridBagConstraints bibliotecaConstraints = new GridBagConstraints();
      bibliotecaConstraints.fill = GridBagConstraints.HORIZONTAL;
      bibliotecaConstraints.gridx = 0;
      bibliotecaConstraints.gridy = 0;

      searchInput = createGenericInput(TipoInput.COMUM, "Digite para buscar algo");
      searchInput.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
      searchImage = createGenericInputIcon(LUPA);
      searchImage.addMouseListener(new MouseAdapter() {

         @SneakyThrows
         @Override
         public void mouseClicked(final MouseEvent e) {
            removeComponents(topGamePanel);
            removeComponents(centerGamePanel);

            final Iterable<JogosEntity> iterable = repository.findAllByNomeLike(searchInput.getText());
            createGameCards(iterable);
         }
      });

      searchContainer = createGenericContainer(simpleBorder, Cores.BACKGROUND_PANNEL, 905, 50);
      searchContainer.add(searchImage, BorderLayout.WEST);
      searchContainer.add(searchInput, BorderLayout.CENTER);
      final GridBagConstraints searchConstraints = new GridBagConstraints();
      searchConstraints.fill = GridBagConstraints.HORIZONTAL;
      searchConstraints.insets = new Insets(0, 10, 0, 10);
      searchConstraints.gridx = 1;
      searchConstraints.gridy = 0;

      carrinhoLabel = new JLabel();
      carrinhoLabel.setIcon(new ImageIcon(ImageIO.read(getClass().getResourceAsStream(CESTA))));

      carrinhoContainer = createGenericContainer(null, Cores.BACKGROUND_PANNEL, 50, 50);
      carrinhoContainer.add(carrinhoLabel, BorderLayout.WEST);
      final GridBagConstraints carrinhoConstraints = new GridBagConstraints();
      carrinhoConstraints.fill = GridBagConstraints.HORIZONTAL;
      carrinhoConstraints.gridx = 2;
      carrinhoConstraints.gridy = 0;

      topPanel = createGenericContainer(null, null, 1214, 60);
      topPanel.setLayout(new GridBagLayout());
      topPanel.add(bibliotecaContainer, bibliotecaConstraints);
      topPanel.add(searchContainer, searchConstraints);
      topPanel.add(carrinhoContainer, carrinhoConstraints);

      topGamePanel = createGenericContainer(null, null, 1214, 247);
      topGamePanel.setLayout(new GridBagLayout());

      centerGamePanel = createGenericContainer(null, null, 1214, 247);
      centerGamePanel.setLayout(new GridBagLayout());

      final Border centerPanelBorder = BorderFactory.createEmptyBorder(5, 0, 0, 0);
      centerPanel = createGenericContainer(centerPanelBorder, Cores.BACKGROUND_PANNEL, 1214, 500);
      centerPanel.setLayout(new BorderLayout());
      centerPanel.add(topGamePanel, BorderLayout.NORTH);
      centerPanel.add(centerGamePanel, BorderLayout.CENTER);

      bottomPanel = createGenericContainer(null, null, 1214, 30);

      background = new JPanelWithBackground(getClass().getResourceAsStream(BACKGROUND_TEMPLATE));
      background.setLayout(new BorderLayout());
      background.setBorder(new EmptyBorder(15, 15, 15, 15));
      background.add(topPanel, BorderLayout.NORTH);
      background.add(centerPanel, BorderLayout.CENTER);
      background.add(bottomPanel, BorderLayout.SOUTH);

      background.setPreferredSize(new Dimension(1280, 720));

      this.addComponentListener(new ComponentAdapter() {

         @SneakyThrows
         @Override
         public void componentShown(final ComponentEvent e) {
            createGameCards(repository.findAll());
         }
      });

      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setContentPane(background);
      this.pack();
      this.setResizable(false);
   }

   private void createGameCards(final Iterable<JogosEntity> iterable) throws IOException {
      final List<JogosEntity> jogosList = new ArrayList<JogosEntity>();
      iterable.forEach(jogosList::add);
      int row = 0;

      for (final JogosEntity jogo : jogosList) {

         final JPanel gameCard = new JPanelGameCard(format("/icons/game/%s", jogo.getImagem()),
             jogo.getPreco(), jogo.getId(), this);
         final GridBagConstraints gameCardConstraints = new GridBagConstraints();
         gameCardConstraints.fill = GridBagConstraints.HORIZONTAL;
         gameCardConstraints.gridx = row;
         gameCardConstraints.gridy = 0;

         if (topGamePanel.getComponentCount() == 3) {
            row = 0;
         }

         if (topGamePanel.getComponentCount() != 3) {
            topGamePanel.add(gameCard, gameCardConstraints);
         } else {
            centerGamePanel.add(gameCard, gameCardConstraints);
         }
         row++;
         if (topGamePanel.getComponentCount() == 3 && centerGamePanel.getComponentCount() == 3) {
            break;
         }
      }
      refresh();
   }

   private void removeComponents(final JPanel panel) {
      final java.awt.Component[] components = panel.getComponents();

      for (final java.awt.Component c : components) {
         panel.remove(c);
      }
   }

   private void refresh() {
      this.invalidate();
      this.validate();
   }
}

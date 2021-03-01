package br.com.ifsul.vaporepico.view;

import br.com.ifsul.vaporepico.domain.Cores;
import br.com.ifsul.vaporepico.domain.TipoInput;
import br.com.ifsul.vaporepico.domain.entity.JogosEntity;
import br.com.ifsul.vaporepico.domain.entity.JogosRepository;
import br.com.ifsul.vaporepico.domain.entity.UsuarioRepository;
import br.com.ifsul.vaporepico.view.component.JPanelGameCard;
import br.com.ifsul.vaporepico.view.component.JPanelWithBackground;
import br.com.ifsul.vaporepico.view.component.Toast;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.Transient;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static br.com.ifsul.vaporepico.domain.Fontes.MONTSERRAT;
import static br.com.ifsul.vaporepico.utils.CreateComponentUtils.*;
import static java.lang.String.format;
import static java.util.Objects.isNull;

@org.springframework.stereotype.Component
public class LojaFrame extends JFrame {

   private static final long serialVersionUID = 6789098987073204340L;

   private static final String VAPOR_EPICO = "VaporEpico";
   private static final String BACKGROUND_TEMPLATE = "/background/LojaBackground.png";
   private static final String FLECHA_IMAGE = "/icons/Flecha.png";
   private static final String CESTA = "/icons/Cesta.png";
   private static final String LUPA = "/icons/Search.png";

   @Autowired
   private JogosRepository jogosRepository;

   @Autowired
   private UsuarioRepository usuarioRepository;

   private Long userId;
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
   private LojaFrame context = this;

   public LojaFrame() throws IOException {
      super(VAPOR_EPICO);
   }

   public void showUI(final Long idUsuario) throws IOException {
      userId = idUsuario;

      bibliotecaLabel = createGenericTextLabel("BIBLIOTECA", Color.WHITE, MONTSERRAT);

      flecha = new JLabel();
      flecha.setIcon(new ImageIcon(ImageIO.read(getClass().getResourceAsStream(FLECHA_IMAGE))));
      flecha.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 40));

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

            final Iterable<JogosEntity> iterable = jogosRepository.findAllByNomeLike(searchInput.getText());
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
      carrinhoLabel.addMouseListener(new MouseAdapter() {

         @SneakyThrows
         @Override
         public void mouseClicked(final MouseEvent e) {
            removeComponents(topGamePanel);
            removeComponents(centerGamePanel);

            final Iterable<JogosEntity> iterable = jogosRepository.findAll();
            createGameCards(iterable);
         }
      });

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
            createGameCards(jogosRepository.findAll());
         }
      });

      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setResizable(false);
      this.setContentPane(background);
      this.pack();
      this.setVisible(true);
   }

   private void createGameCards(final Iterable<JogosEntity> iterable) throws IOException {
      final List<JogosEntity> jogosList = new ArrayList<JogosEntity>();
      iterable.forEach(jogo -> {
         if (isNull(jogo.getUsuario())) {
            jogosList.add(jogo);
         }
      });
      int row = 0;

      for (final JogosEntity jogo : jogosList) {

         final JPanel gameCard = new JPanelGameCard(format("/icons/game/%s", jogo.getImagem()),
             jogo.getPreco(), jogo.getId(), this);


         gameCard.addMouseListener(new MouseAdapter() {

            @Transient
            @Override
            public void mouseClicked(final MouseEvent e) {
               usuarioRepository.findById(userId)
                   .ifPresent(usuario -> {
                      jogo.setUsuario(usuario);
                      jogosRepository.save(jogo);
                   });

               final Toast toast = new Toast("Jogo comprado",
                   getLocationOnScreen().x + 580,
                   getLocationOnScreen().y + 650);

               toast.showToast();

               topGamePanel.remove(e.getComponent());
               centerGamePanel.remove(e.getComponent());
               context.refresh();
            }
         });

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
      this.setContentPane(background);
      this.invalidate();
      this.validate();
   }
}

package br.com.ifsul.vaporepico.view;

import br.com.ifsul.vaporepico.domain.entity.JogosEntity;
import br.com.ifsul.vaporepico.domain.entity.UsuarioEntity;
import br.com.ifsul.vaporepico.domain.entity.UsuarioRepository;
import br.com.ifsul.vaporepico.view.component.JPanelWithBackground;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

import static br.com.ifsul.vaporepico.domain.Cores.BACKGROUND_COLOR;
import static br.com.ifsul.vaporepico.domain.Fontes.MONTSERRAT;
import static br.com.ifsul.vaporepico.utils.CreateComponentUtils.createGenericContainer;
import static br.com.ifsul.vaporepico.utils.CreateComponentUtils.createGenericTextLabel;

@Component
public class UsuarioFrame extends JFrame {

   private static final long serialVersionUID = -3992544256441225197L;

   private static final String VAPOR_EPICO = "VaporEpico";
   private static final String BACKGROUND_TEMPLATE = "/background/Background.png";
   private static final String FLECHA_IMAGE = "/icons/Flecha.png";
   private static final String USER_IMAGE = "/icons/ImagemUser.png";

   @Autowired
   private UsuarioRepository repository;

   @Autowired
   private LojaFrame lojaFrame;

   private UsuarioEntity usuario;

   private JPanel nomeContainer;
   private JLabel nomeLabel;
   private JPanel emailContainer;
   private JLabel emailLabel;
   private JPanel dataContainer;
   private JLabel dataLabel;
   private JLabel jogosLabel;
   private JList<JogosEntity> listaJogos;
   private JPanel infoContainer;
   private JPanel usuarioContainer;
   private JLabel userImage;
   private JLabel flecha;
   private JPanel background;
   private UsuarioFrame context = this;

   public UsuarioFrame() {
      super(VAPOR_EPICO);
   }

   @Transactional
   public void showUI(final Long idUsuario) throws IOException {

      usuario = repository.findById(idUsuario)
          .get();

      final Border labelBorder = BorderFactory.createEmptyBorder(0, 10, 0, 0);
      nomeLabel = createGenericTextLabel(usuario.getNome(), Color.WHITE, MONTSERRAT);
      nomeLabel.setBorder(labelBorder);

      final Border textContainerBorder = BorderFactory.createLineBorder(Color.WHITE);
      nomeContainer = createGenericContainer(textContainerBorder, BACKGROUND_COLOR, 300, 45);
      nomeContainer.add(nomeLabel, BorderLayout.CENTER);
      final GridBagConstraints nomeConstraints = new GridBagConstraints();
      nomeConstraints.insets = new Insets(10, 0, 0, 0);
      nomeConstraints.gridx = 0;
      nomeConstraints.gridy = 0;

      emailLabel = createGenericTextLabel(usuario.getEmail(), Color.WHITE, MONTSERRAT);
      emailLabel.setBorder(labelBorder);

      emailContainer = createGenericContainer(textContainerBorder, BACKGROUND_COLOR, 300, 45);
      emailContainer.add(emailLabel, BorderLayout.CENTER);
      final GridBagConstraints emailConstraints = new GridBagConstraints();
      emailConstraints.insets = new Insets(10, 0, 0, 0);
      emailConstraints.gridx = 0;
      emailConstraints.gridy = 1;

      final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
      dataLabel = createGenericTextLabel(usuario.getDataNascimento().format(formatter),
          Color.WHITE, MONTSERRAT);
      dataLabel.setBorder(labelBorder);

      dataContainer = createGenericContainer(textContainerBorder, BACKGROUND_COLOR, 300, 45);
      dataContainer.add(dataLabel, BorderLayout.CENTER);
      final GridBagConstraints dataConstraints = new GridBagConstraints();
      dataConstraints.insets = new Insets(10, 0, 0, 0);
      dataConstraints.gridx = 0;
      dataConstraints.gridy = 2;

      jogosLabel = createGenericTextLabel("Jogos", Color.WHITE, MONTSERRAT);
      final GridBagConstraints jogosConstraints = new GridBagConstraints();
      jogosConstraints.insets = new Insets(10, 0, 0, 0);
      jogosConstraints.gridx = 0;
      jogosConstraints.gridy = 3;

      final DefaultListModel<JogosEntity> model = new DefaultListModel<>();
      usuario.getJogos().forEach(model::addElement);

      listaJogos = new JList<JogosEntity>(model);
      listaJogos.setBackground(BACKGROUND_COLOR);
      listaJogos.setFont(MONTSERRAT);
      listaJogos.setForeground(Color.WHITE);
      listaJogos.setFixedCellHeight(30);
      listaJogos.setFixedCellWidth(100);
      listaJogos.setBorder(textContainerBorder);
      listaJogos.setLayoutOrientation(JList.VERTICAL);

      listaJogos.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(final MouseEvent e) {
            if (e.getClickCount() == 1) {
               listaJogos.clearSelection();
            }
         }
      });

      final JScrollPane scrollPane = new JScrollPane();
      scrollPane.setPreferredSize(new Dimension(300, 100));
      scrollPane.setViewportView(listaJogos);

      final GridBagConstraints listaJogosConstraints = new GridBagConstraints();
      listaJogosConstraints.insets = new Insets(10, 0, 0, 0);
      listaJogosConstraints.gridx = 0;
      listaJogosConstraints.gridy = 4;

      infoContainer = createGenericContainer(null, BACKGROUND_COLOR, 300, 430);
      infoContainer.setLayout(new GridBagLayout());
      infoContainer.add(nomeContainer, nomeConstraints);
      infoContainer.add(emailContainer, emailConstraints);
      infoContainer.add(dataContainer, dataConstraints);
      infoContainer.add(dataContainer, dataConstraints);
      infoContainer.add(jogosLabel, jogosConstraints);
      infoContainer.add(scrollPane, listaJogosConstraints);

      userImage = new JLabel();
      userImage.setIcon(new ImageIcon(ImageIO.read(getClass().getResourceAsStream(USER_IMAGE))));
      userImage.setHorizontalAlignment(0);

      usuarioContainer = createGenericContainer(null, null, 300, 507);
      usuarioContainer.setLayout(new BorderLayout());
      usuarioContainer.add(userImage, BorderLayout.NORTH);
      usuarioContainer.add(infoContainer, BorderLayout.CENTER);

      final GridBagConstraints usuarioConstraints = new GridBagConstraints();
      usuarioConstraints.anchor = GridBagConstraints.CENTER;
      usuarioConstraints.weighty = 1;
      usuarioConstraints.insets = new Insets(0, 0, 100, 0);
      usuarioConstraints.gridwidth = 2;
      usuarioConstraints.gridx = 0;
      usuarioConstraints.gridy = 1;

      flecha = new JLabel();
      flecha.setIcon(new ImageIcon(ImageIO.read(getClass().getResourceAsStream(FLECHA_IMAGE))));
      final GridBagConstraints flechaConstraints = new GridBagConstraints();
      flechaConstraints.anchor = GridBagConstraints.NORTHWEST;
      flechaConstraints.weightx = 1;
      flechaConstraints.weighty = 1;
      flechaConstraints.gridx = 0;
      flechaConstraints.gridy = 0;

      flecha.addMouseListener(new MouseAdapter() {
         @SneakyThrows
         @Override
         public void mouseClicked(final MouseEvent e) {
            lojaFrame.showUI(idUsuario);
            context.dispose();
         }
      });

      background = new JPanelWithBackground(getClass().getResourceAsStream(BACKGROUND_TEMPLATE));
      background.setLayout(new GridBagLayout());
      background.add(flecha, flechaConstraints);
      background.add(usuarioContainer, usuarioConstraints);

      background.setPreferredSize(new Dimension(1280, 720));

      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setContentPane(background);
      this.pack();
      this.setResizable(false);
      this.setVisible(true);
   }
}

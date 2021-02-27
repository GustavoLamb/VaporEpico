package br.com.ifsul.vaporepico.view;

import br.com.ifsul.vaporepico.domain.TipoInput;
import br.com.ifsul.vaporepico.view.component.JPanelWithBackground;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import static br.com.ifsul.vaporepico.domain.TipoInput.COMUM;
import static br.com.ifsul.vaporepico.domain.TipoInput.SENHA;

@Component
public class LoginScreen extends JFrame {
   private static final long serialVersionUID = -6737522500686622909L;

   private static final String VAPOR_EPICO = "VaporEpico";
   private static final String BACKGROUND_TEMPLATE = "/background/Background.png";
   private static final String CARRINHO_IMAGE = "/icons/Carrinho.png";
   private static final String SENHA_IMAGE = "/icons/Senha.png";
   private static final String USER_IMAGE = "/icons/User.png";
   private static final Color BACKGROUND_COLOR = new Color(248, 90, 62);
   private static final Font MONTSERRAT = new Font("Montserrat", Font.BOLD, 16);
   private static final Font MONTSERRAT_MEDIUM = new Font("Montserrat", Font.BOLD, 14);

   private final JLabel registroLabel;
   private final JPanel registroContainer;
   private final JButton loginButton;
   private final JPanel buttonContainer;
   private final JTextField usuarioInput;
   private final JLabel usuarioImage;
   private final JPanel usuarioContainer;
   private final JTextField senhaInput;
   private final JLabel senhaImage;
   private final JPanel senhaContainer;
   private final JPanel inputsContainer;
   private final JLabel carrinho;
   private final JPanel form;
   private final JPanel background;

   public LoginScreen() throws IOException {
      super(VAPOR_EPICO);

      registroLabel = createGenericTextLabel("Não tem conta? Registre-se", Color.WHITE, MONTSERRAT_MEDIUM);
      registroLabel.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(final MouseEvent e) {
            // TODO adicionar ação ao clicar no texto
         }
      });

      registroContainer = createGenericContainer(null, BACKGROUND_COLOR, 300, 17);
      registroContainer.add(registroLabel, BorderLayout.EAST);

      loginButton = createGenericButton(Color.WHITE, 300, 45, "LOGIN");
      loginButton.addActionListener(e -> {
         // TODO adicionar ação do botão
      });

      final Border containerBorder = BorderFactory.createEmptyBorder(50, 0, 0, 0);
      buttonContainer = createGenericContainer(containerBorder, BACKGROUND_COLOR, 300, 115);
      buttonContainer.add(loginButton, BorderLayout.NORTH);
      buttonContainer.add(registroContainer, BorderLayout.SOUTH);

      usuarioInput = createGenericInput(COMUM, "Usuário");

      usuarioImage = createGenericInputIcon(USER_IMAGE);

      final Border inputContainerBorder = BorderFactory.createLineBorder(Color.WHITE);
      usuarioContainer = createGenericContainer(inputContainerBorder, BACKGROUND_COLOR, 300, 45);
      usuarioContainer.add(usuarioImage, BorderLayout.WEST);
      usuarioContainer.add(usuarioInput, BorderLayout.CENTER);

      senhaInput = createGenericInput(SENHA, "Senha");

      senhaImage = createGenericInputIcon(SENHA_IMAGE);

      senhaContainer = createGenericContainer(inputContainerBorder, BACKGROUND_COLOR, 300, 45);
      senhaContainer.add(senhaImage, BorderLayout.WEST);
      senhaContainer.add(senhaInput, BorderLayout.CENTER);

      inputsContainer = createGenericContainer(containerBorder, BACKGROUND_COLOR, 300, 95);
      inputsContainer.add(usuarioContainer, BorderLayout.NORTH);
      inputsContainer.add(senhaContainer, BorderLayout.SOUTH);

      carrinho = createCarrinhoImage();

      form = createGenericContainer(null, BACKGROUND_COLOR, 300, 389);
      form.add(carrinho, BorderLayout.NORTH);
      form.add(inputsContainer, BorderLayout.CENTER);
      form.add(buttonContainer, BorderLayout.SOUTH);

      background = new JPanelWithBackground(getClass().getResourceAsStream(BACKGROUND_TEMPLATE));
      background.setLayout(new GridBagLayout());
      background.add(form, new GridBagConstraints());

      background.setPreferredSize(new Dimension(1280, 720));

      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setContentPane(background);
      this.pack();
      this.setResizable(false);
   }

   private JLabel createGenericInputIcon(final String imagePath) throws IOException {
      final JLabel label = new JLabel();
      label.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
      label.setIcon(new ImageIcon(ImageIO.read(getClass().getResourceAsStream(imagePath))));
      return label;
   }

   private JTextField createGenericInput(final TipoInput tipo, final String text) {
      final JTextField textField = SENHA == tipo ? new JPasswordField() : new JTextField();
      textField.setFont(MONTSERRAT);
      textField.setForeground(Color.WHITE);
      textField.setText(text);
      textField.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
      textField.setOpaque(false);
      return textField;
   }

   private JLabel createGenericTextLabel(final String text, final Color textColor, final Font fontConfig) {
      final JLabel label = new JLabel();
      label.setText(text);
      label.setForeground(textColor);
      label.setFont(fontConfig);
      return label;
   }

   private JButton createGenericButton(final Color bg, final Integer width, final Integer height,
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

   private JPanel createGenericContainer(final Border border, final Color bg, final Integer width,
                                         final Integer height) {
      final JPanel panel = new JPanel();
      panel.setBorder(border);
      panel.setBackground(bg);
      panel.setPreferredSize(new Dimension(width, height));
      panel.setLayout(new BorderLayout());

      return panel;
   }

   private JLabel createCarrinhoImage() throws IOException {

      final JLabel label = new JLabel();
      label.setIcon(new ImageIcon(ImageIO.read(getClass().getResourceAsStream(CARRINHO_IMAGE))));
      label.setHorizontalAlignment(0);

      return label;
   }

}

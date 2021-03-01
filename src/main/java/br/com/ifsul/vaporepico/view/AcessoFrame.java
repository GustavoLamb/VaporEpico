package br.com.ifsul.vaporepico.view;

import br.com.ifsul.vaporepico.domain.TipoInput;
import br.com.ifsul.vaporepico.domain.TipoUsuario;
import br.com.ifsul.vaporepico.domain.entity.UsuarioEntity;
import br.com.ifsul.vaporepico.domain.entity.UsuarioRepository;
import br.com.ifsul.vaporepico.view.component.JPanelWithBackground;
import br.com.ifsul.vaporepico.view.component.Toast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static br.com.ifsul.vaporepico.domain.Cores.BACKGROUND_COLOR;
import static br.com.ifsul.vaporepico.domain.Fontes.MONTSERRAT_MEDIUM;
import static br.com.ifsul.vaporepico.domain.TipoInput.SENHA;
import static br.com.ifsul.vaporepico.utils.CreateComponentUtils.*;
import static org.apache.commons.lang3.BooleanUtils.isFalse;
import static org.apache.logging.log4j.util.Strings.isNotBlank;

@Component
public class AcessoFrame extends JFrame {
   private static final long serialVersionUID = -6737522500686622909L;

   private static final String VAPOR_EPICO = "VaporEpico";
   private static final String BACKGROUND_TEMPLATE = "/background/Background.png";
   private static final String FLECHA_IMAGE = "/icons/Flecha.png";
   private static final String CARRINHO_IMAGE = "/icons/Carrinho.png";
   private static final String SENHA_IMAGE = "/icons/Senha.png";
   private static final String EMAIL_IMAGE = "/icons/Email.png";
   private static final String DATA_IMAGE = "/icons/Calendar.png";
   private static final String USER_IMAGE = "/icons/User.png";

   @Autowired
   private UsuarioRepository repository;

   @Autowired
   private LojaFrame lojaFrame;

   private JLabel registroLabel;
   private JPanel registroContainer;
   private JPanel emailContainer;
   private JButton loginButton;
   private JButton registrarButton;
   private JPanel buttonContainer;
   private JTextField usuarioInput;
   private JTextField emailInput;
   private JLabel emailImage;
   private JPanel dataContainer;
   private JLabel dataImage;
   private JTextField dataInput;
   private JLabel usuarioImage;
   private JPanel usuarioContainer;
   private JTextField senhaInput;
   private JLabel senhaImage;
   private JPanel senhaContainer;
   private JPanel confirmarSenhaContainer;
   private JTextField confirmarSenhaInput;
   private JLabel confirmarSenhaImage;
   private JPanel inputsContainer;
   private JLabel carrinho;
   private JLabel flecha;
   private JPanel form;
   private JPanel background;

   public AcessoFrame() throws IOException {
      super(VAPOR_EPICO);

      background = new JPanelWithBackground(getClass().getResourceAsStream(BACKGROUND_TEMPLATE));
      background.setLayout(new GridBagLayout());

      loginUI();

      background.setPreferredSize(new Dimension(1280, 720));

      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setContentPane(background);
      this.pack();
      this.setResizable(false);
   }

   private void loginUI() throws IOException {

      registroLabel = createGenericTextLabel("Não tem conta? Registre-se", Color.WHITE, MONTSERRAT_MEDIUM);
      registroLabel.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(final MouseEvent e) {
            background.remove(form);
            try {
               cadastrarUI();
            } catch (final Exception exception) {
               exception.printStackTrace();
            }
         }
      });

      registroContainer = createGenericContainer(null, BACKGROUND_COLOR, 300, 17);
      registroContainer.add(registroLabel, BorderLayout.EAST);

      loginButton = createGenericButton(Color.WHITE, 300, 45, "LOGIN");
      loginButton.addActionListener(e -> {
         if (validarCamposLogin()) {

            final Optional<UsuarioEntity> optionalUsuario = repository
                .findByNomeAndSenha(usuarioInput.getText(), senhaInput.getText());

            if (isFalse(optionalUsuario.isPresent())) {
               final Toast toast = new Toast("Usuário não encontrado",
                   getLocationOnScreen().x + 580,
                   getLocationOnScreen().y + 650);

               toast.showToast();
            } else {
               final Toast toast = new Toast("Usuário Existe",
                   getLocationOnScreen().x + 580,
                   getLocationOnScreen().y + 650);

               toast.showToast();

               try {
                  lojaFrame.showUI(optionalUsuario.map(UsuarioEntity::getId).orElse(null));
                  this.dispose();
               } catch (final IOException ioException) {
                  ioException.printStackTrace();
               }
            }
         } else {
            final Toast toast = new Toast("Preencha todos os campos",
                getLocationOnScreen().x + 580,
                getLocationOnScreen().y + 650);

            toast.showToast();
         }
      });

      final Border containerBorder = BorderFactory.createEmptyBorder(50, 0, 0, 0);
      buttonContainer = createGenericContainer(containerBorder, BACKGROUND_COLOR, 300, 115);
      buttonContainer.add(loginButton, BorderLayout.NORTH);
      buttonContainer.add(registroContainer, BorderLayout.SOUTH);

      usuarioInput = createGenericInput(TipoInput.COMUM, "Usuário");

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

      background.add(form, new GridBagConstraints());
      refresh();
   }

   private void cadastrarUI() throws IOException {

      registrarButton = createGenericButton(Color.WHITE, 300, 45, "REGISTRAR");
      registrarButton.addActionListener(e -> {
         if (validarCamposRegistro()) {
            final UsuarioEntity entity = new UsuarioEntity();
            entity.setNome(usuarioInput.getText());
            entity.setEmail(emailInput.getText());
            entity.setSenha(senhaInput.getText());
            entity.setDataNascimento(LocalDate.parse(dataInput.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            entity.setDataCriacao(LocalDate.now());
            entity.setTipo(TipoUsuario.COMUM);

            repository.save(entity);

            final Toast toast = new Toast("Usuário cadastrado",
                getLocationOnScreen().x + 580,
                getLocationOnScreen().y + 650);

            toast.showToast();

            background.remove(form);
            background.remove(flecha);
            try {
               loginUI();
            } catch (final Exception exception) {
               exception.printStackTrace();
            }
         } else {
            final Toast toast = new Toast("Erro no registro",
                getLocationOnScreen().x + 580,
                getLocationOnScreen().y + 650);

            toast.showToast();
         }
      });

      final Border containerBorder = BorderFactory.createEmptyBorder(10, 0, 0, 0);
      buttonContainer = createGenericContainer(containerBorder, BACKGROUND_COLOR, 300, 115);
      buttonContainer.add(registrarButton, BorderLayout.NORTH);

      usuarioInput = createGenericInput(TipoInput.COMUM, "Usuário");
      usuarioImage = createGenericInputIcon(USER_IMAGE);

      final Border inputContainerBorder = BorderFactory.createLineBorder(Color.WHITE);
      usuarioContainer = createGenericContainer(inputContainerBorder, BACKGROUND_COLOR, 300, 45);
      usuarioContainer.add(usuarioImage, BorderLayout.WEST);
      usuarioContainer.add(usuarioInput, BorderLayout.CENTER);
      final GridBagConstraints usuarioConstraints = new GridBagConstraints();
      usuarioConstraints.insets = new Insets(10, 0, 0, 0);
      usuarioConstraints.gridx = 0;
      usuarioConstraints.gridy = 0;

      emailInput = createGenericInput(TipoInput.COMUM, "Email");
      emailImage = createGenericInputIcon(EMAIL_IMAGE);

      emailContainer = createGenericContainer(inputContainerBorder, BACKGROUND_COLOR, 300, 45);
      emailContainer.add(emailImage, BorderLayout.WEST);
      emailContainer.add(emailInput, BorderLayout.CENTER);
      final GridBagConstraints emailConstraints = new GridBagConstraints();
      emailConstraints.insets = new Insets(10, 0, 0, 0);
      emailConstraints.gridx = 0;
      emailConstraints.gridy = 1;

      dataInput = createGenericInput(TipoInput.COMUM, "Data de nacimento");
      dataImage = createGenericInputIcon(DATA_IMAGE);

      dataContainer = createGenericContainer(inputContainerBorder, BACKGROUND_COLOR, 300, 45);
      dataContainer.add(dataImage, BorderLayout.WEST);
      dataContainer.add(dataInput, BorderLayout.CENTER);
      final GridBagConstraints dataConstraints = new GridBagConstraints();
      dataConstraints.insets = new Insets(10, 0, 0, 0);
      dataConstraints.gridx = 0;
      dataConstraints.gridy = 2;

      senhaInput = createGenericInput(SENHA, "Senha");

      senhaImage = createGenericInputIcon(SENHA_IMAGE);

      senhaContainer = createGenericContainer(inputContainerBorder, BACKGROUND_COLOR, 300, 45);
      senhaContainer.add(senhaImage, BorderLayout.WEST);
      senhaContainer.add(senhaInput, BorderLayout.CENTER);
      final GridBagConstraints senhaConstraints = new GridBagConstraints();
      senhaConstraints.insets = new Insets(10, 0, 0, 0);
      senhaConstraints.gridx = 0;
      senhaConstraints.gridy = 3;

      confirmarSenhaInput = createGenericInput(SENHA, "Confirmar senha");
      confirmarSenhaImage = createGenericInputIcon(SENHA_IMAGE);

      confirmarSenhaContainer = createGenericContainer(inputContainerBorder, BACKGROUND_COLOR, 300, 45);
      confirmarSenhaContainer.add(confirmarSenhaImage, BorderLayout.WEST);
      confirmarSenhaContainer.add(confirmarSenhaInput, BorderLayout.CENTER);
      final GridBagConstraints confirmarSenhaConstraints = new GridBagConstraints();
      confirmarSenhaConstraints.insets = new Insets(15, 0, 0, 0);
      confirmarSenhaConstraints.gridx = 0;
      confirmarSenhaConstraints.gridy = 4;

      inputsContainer = createGenericContainer(containerBorder, BACKGROUND_COLOR, 300, 315);
      inputsContainer.setLayout(new GridBagLayout());
      inputsContainer.add(usuarioContainer, usuarioConstraints);
      inputsContainer.add(emailContainer, emailConstraints);
      inputsContainer.add(dataContainer, dataConstraints);
      inputsContainer.add(senhaContainer, senhaConstraints);
      inputsContainer.add(confirmarSenhaContainer, confirmarSenhaConstraints);

      carrinho = createCarrinhoImage();

      form = createGenericContainer(null, null, 300, 507);
      form.add(carrinho, BorderLayout.NORTH);
      form.add(inputsContainer, BorderLayout.CENTER);
      form.add(buttonContainer, BorderLayout.SOUTH);

      flecha = new JLabel();
      flecha.setIcon(new ImageIcon(ImageIO.read(getClass().getResourceAsStream(FLECHA_IMAGE))));
      flecha.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(final MouseEvent e) {
            background.remove(form);
            background.remove(flecha);
            try {
               loginUI();
            } catch (final Exception exception) {
               exception.printStackTrace();
            }
         }
      });

      final GridBagConstraints formConstraints = new GridBagConstraints();
      formConstraints.anchor = GridBagConstraints.CENTER;
      formConstraints.weighty = 1;
      formConstraints.insets = new Insets(0, 0, 100, 0);
      formConstraints.gridwidth = 2;
      formConstraints.gridx = 0;
      formConstraints.gridy = 1;

      final GridBagConstraints flechaConstraints = new GridBagConstraints();
      flechaConstraints.anchor = GridBagConstraints.NORTHWEST;
      flechaConstraints.weightx = 1;
      flechaConstraints.weighty = 1;
      flechaConstraints.gridx = 0;
      flechaConstraints.gridy = 0;

      background.add(flecha, flechaConstraints);
      background.add(form, formConstraints);
      refresh();
   }

   private JLabel createCarrinhoImage() throws IOException {

      final JLabel label = new JLabel();
      label.setIcon(new ImageIcon(ImageIO.read(getClass().getResourceAsStream(CARRINHO_IMAGE))));
      label.setHorizontalAlignment(0);

      return label;
   }

   private Boolean validarCamposRegistro() {
      return isNotBlank(usuarioInput.getText())
          && isNotBlank(usuarioInput.getText())
          && isNotBlank(senhaInput.getText())
          && isNotBlank(confirmarSenhaInput.getText())
          && isNotBlank(dataInput.getText())
          && senhaInput.getText().equals(confirmarSenhaInput.getText());
   }

   private Boolean validarCamposLogin() {
      return isNotBlank(usuarioInput.getText())
          && isNotBlank(senhaInput.getText());
   }

   private void refresh() {
      background.validate();
      this.setContentPane(background);
      this.invalidate();
      this.validate();
   }
}

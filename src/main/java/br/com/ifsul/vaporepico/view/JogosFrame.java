package br.com.ifsul.vaporepico.view;

import br.com.ifsul.vaporepico.domain.TipoInput;
import br.com.ifsul.vaporepico.domain.entity.JogosEntity;
import br.com.ifsul.vaporepico.domain.entity.JogosRepository;
import br.com.ifsul.vaporepico.view.component.JPanelWithBackground;
import br.com.ifsul.vaporepico.view.component.Toast;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static br.com.ifsul.vaporepico.domain.Cores.BACKGROUND_COLOR;
import static br.com.ifsul.vaporepico.utils.CreateComponentUtils.*;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Component
public class JogosFrame extends JFrame {

   private static final long serialVersionUID = 8575658792611313407L;

   private static final String VAPOR_EPICO = "VaporEpico";
   private static final String BACKGROUND_TEMPLATE = "/background/Background.png";
   private static final String CONTROLE = "/icons/Controle.png";
   private static final String FLECHA_IMAGE = "/icons/Flecha.png";
   private static final String JOGO_ICON = "/icons/Jogo.png";
   private static final String PRECO_ICON = "/icons/Preco.png";
   private static final String IMAGEM_JOGO_ICON = "/icons/ImagemJogo.png";
   private static final String DESCRICAO_ICON = "/icons/Descricao.png";
   private static final String CALENDARIO_ICON = "/icons/Calendar.png";

   @Autowired
   private JogosRepository repository;

   @Autowired
   private LojaFrame lojaFrame;

   private Long userId;

   private JPanel buttonContainer;
   private JButton registrarButton;
   private JPanel imagemContainer;
   private JTextField imagemInput;
   private JLabel imagemJogoIcon;
   private JPanel dataLancamentoContainer;
   private JTextField dataLacamentoInput;
   private JLabel dataLancamentoIcon;
   private JPanel precoContainer;
   private JTextField precoInput;
   private JLabel precoIcon;
   private JPanel descricaoContainer;
   private JTextField descricaoInput;
   private JLabel descricaoIcon;
   private JPanel jogoContainer;
   private JTextField jogoInput;
   private JLabel jogoIcon;
   private JLabel controle;
   private JPanel inputsContainer;
   private JPanel cadastroContainer;
   private JLabel flecha;
   private JPanel background;

   private JogosFrame context = this;

   public JogosFrame() {
      super(VAPOR_EPICO);
   }

   public void showUI(final Long idUsuario) throws IOException {

      userId = idUsuario;

      registrarButton = createGenericButton(Color.WHITE, 300, 45, "REGISTRAR");
      registrarButton.addActionListener(e -> {
         if (validarCampos()) {
            final JogosEntity entity = new JogosEntity();
            entity.setNome(jogoInput.getText());
            entity.setDescricao(descricaoInput.getText());
            entity.setPreco(new BigDecimal(precoInput.getText()));
            entity.setDataLancamento(LocalDate.parse(dataLacamentoInput.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            entity.setImagem(imagemInput.getText());

            repository.save(entity);

            final Toast toast = new Toast("Jogo cadastrado",
                getLocationOnScreen().x + 580,
                getLocationOnScreen().y + 650);

            toast.showToast();

            try {
               lojaFrame.showUI(userId);
               this.dispose();
            } catch (final IOException ioException) {
               ioException.printStackTrace();
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

      jogoInput = createGenericInput(TipoInput.COMUM, "Jogo");
      jogoIcon = createGenericInputIcon(JOGO_ICON);

      final Border inputContainerBorder = BorderFactory.createLineBorder(Color.WHITE);
      jogoContainer = createGenericContainer(inputContainerBorder, BACKGROUND_COLOR, 300, 45);
      jogoContainer.add(jogoIcon, BorderLayout.WEST);
      jogoContainer.add(jogoInput, BorderLayout.CENTER);
      final GridBagConstraints jogoConstraints = new GridBagConstraints();
      jogoConstraints.insets = new Insets(10, 0, 0, 0);
      jogoConstraints.gridx = 0;
      jogoConstraints.gridy = 0;

      descricaoInput = createGenericInput(TipoInput.COMUM, "Descricao");
      descricaoIcon = createGenericInputIcon(DESCRICAO_ICON);

      descricaoContainer = createGenericContainer(inputContainerBorder, BACKGROUND_COLOR, 300, 45);
      descricaoContainer.add(descricaoIcon, BorderLayout.WEST);
      descricaoContainer.add(descricaoInput, BorderLayout.CENTER);
      final GridBagConstraints descricaoConstraints = new GridBagConstraints();
      descricaoConstraints.insets = new Insets(10, 0, 0, 0);
      descricaoConstraints.gridx = 0;
      descricaoConstraints.gridy = 1;

      precoInput = createGenericInput(TipoInput.COMUM, "Preço");
      precoIcon = createGenericInputIcon(PRECO_ICON);

      precoContainer = createGenericContainer(inputContainerBorder, BACKGROUND_COLOR, 300, 45);
      precoContainer.add(precoIcon, BorderLayout.WEST);
      precoContainer.add(precoInput, BorderLayout.CENTER);
      final GridBagConstraints precoConstraints = new GridBagConstraints();
      precoConstraints.insets = new Insets(10, 0, 0, 0);
      precoConstraints.gridx = 0;
      precoConstraints.gridy = 2;

      dataLacamentoInput = createGenericInput(TipoInput.COMUM, "Data lançamento"); // Colocar no formato dd/mm/yyyy
      dataLancamentoIcon = createGenericInputIcon(CALENDARIO_ICON);

      dataLancamentoContainer = createGenericContainer(inputContainerBorder, BACKGROUND_COLOR, 300, 45);
      dataLancamentoContainer.add(dataLancamentoIcon, BorderLayout.WEST);
      dataLancamentoContainer.add(dataLacamentoInput, BorderLayout.CENTER);
      final GridBagConstraints dataLancamentoConstraints = new GridBagConstraints();
      dataLancamentoConstraints.insets = new Insets(10, 0, 0, 0);
      dataLancamentoConstraints.gridx = 0;
      dataLancamentoConstraints.gridy = 3;

      imagemInput = createGenericInput(TipoInput.COMUM, "Imagem"); // Colocar nome do arquivo da pasta /icons/game/ e extensão (Exemplo CSGO.png)
      imagemJogoIcon = createGenericInputIcon(IMAGEM_JOGO_ICON);

      imagemContainer = createGenericContainer(inputContainerBorder, BACKGROUND_COLOR, 300, 45);
      imagemContainer.add(imagemJogoIcon, BorderLayout.WEST);
      imagemContainer.add(imagemInput, BorderLayout.CENTER);
      final GridBagConstraints imagemConstraints = new GridBagConstraints();
      imagemConstraints.insets = new Insets(10, 0, 0, 0);
      imagemConstraints.gridx = 0;
      imagemConstraints.gridy = 4;

      inputsContainer = createGenericContainer(null, null, 300, 315);
      inputsContainer.setLayout(new GridBagLayout());
      inputsContainer.add(jogoContainer, jogoConstraints);
      inputsContainer.add(descricaoContainer, descricaoConstraints);
      inputsContainer.add(precoContainer, precoConstraints);
      inputsContainer.add(dataLancamentoContainer, dataLancamentoConstraints);
      inputsContainer.add(imagemContainer, imagemConstraints);

      controle = new JLabel();
      controle.setIcon(new ImageIcon(ImageIO.read(getClass().getResourceAsStream(CONTROLE))));
      controle.setHorizontalAlignment(0);

      cadastroContainer = createGenericContainer(null, null, 300, 507);
      cadastroContainer.add(controle, BorderLayout.NORTH);
      cadastroContainer.add(inputsContainer, BorderLayout.CENTER);
      cadastroContainer.add(buttonContainer, BorderLayout.SOUTH);
      final GridBagConstraints cadastroConstraints = new GridBagConstraints();
      cadastroConstraints.anchor = GridBagConstraints.CENTER;
      cadastroConstraints.weighty = 1;
      cadastroConstraints.insets = new Insets(0, 0, 100, 0);
      cadastroConstraints.gridwidth = 2;
      cadastroConstraints.gridx = 0;
      cadastroConstraints.gridy = 1;

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
            lojaFrame.showUI(userId);
            context.dispose();
         }
      });

      background = new JPanelWithBackground(getClass().getResourceAsStream(BACKGROUND_TEMPLATE));
      background.setLayout(new GridBagLayout());
      background.add(flecha, flechaConstraints);
      background.add(cadastroContainer, cadastroConstraints);

      background.setPreferredSize(new Dimension(1280, 720));

      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setResizable(false);
      this.setContentPane(background);
      this.pack();
      this.setVisible(true);
   }

   private Boolean validarCampos() {
      return isNotBlank(jogoInput.getText())
          && isNotBlank(descricaoInput.getText())
          && isNotBlank(precoInput.getText())
          && isNotBlank(dataLacamentoInput.getText())
          && isNotBlank(imagemInput.getText());
   }

}

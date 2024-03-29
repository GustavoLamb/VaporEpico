package br.com.ifsul.vaporepico;

import br.com.ifsul.vaporepico.view.AcessoFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class VaporEpicoApplication {

   @Autowired
   private AcessoFrame acessoFrame;

   public static void main(final String[] args) {
      final SpringApplicationBuilder applicationBuilder = new SpringApplicationBuilder(VaporEpicoApplication.class);
      applicationBuilder.headless(false);
      applicationBuilder.run(args);
   }

   @PostConstruct
   public void run() {
      acessoFrame.setVisible(true);
   }
}

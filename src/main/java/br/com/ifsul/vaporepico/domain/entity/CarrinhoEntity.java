package br.com.ifsul.vaporepico.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Table(name = "carrinhos")
public class CarrinhoEntity implements Serializable {

   private static final long serialVersionUID = -8690960454574395181L;

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id_carrinho")
   private Long id;

   @Column(name = "data_criacao", nullable = false)
   private LocalDate dataCriacao;

   @ManyToOne
   @JoinColumn(name = "usuario", referencedColumnName = "id_usuario")
   private UsuarioEntity usuario;

   @ManyToMany
   @JoinTable(
       name = "carrinhos_jogos",
       joinColumns = @JoinColumn(name = "carrinho"),
       inverseJoinColumns = @JoinColumn(name = "jogo"))
   private List<JogosEntity> jogosCarrinho;

}

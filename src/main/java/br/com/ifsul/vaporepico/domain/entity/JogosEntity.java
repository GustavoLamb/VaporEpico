package br.com.ifsul.vaporepico.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Table(name = "jogos")
public class JogosEntity implements Serializable {

   private static final long serialVersionUID = 547727384616598242L;

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id_jogo")
   private Long id;

   @Column(name = "nome", nullable = false)
   private String nome;

   @Column(name = "preco", nullable = false)
   private BigDecimal preco;

   @Column(name = "descricao", nullable = false)
   private String descricao;

   @Column(name = "data_lancamento", nullable = false)
   private LocalDate dataLancamento;

   @Lob
   @Column(name = "imagem", length = 1000)
   private byte[] imagem;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "usuario", referencedColumnName = "id_usuario")
   private UsuarioEntity usuario;

   @ManyToMany(mappedBy = "jogosCarrinho")
   private List<CarrinhoEntity> carrinhosJogo;
}

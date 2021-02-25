package br.com.ifsul.vaporepico.domain.entity;

import br.com.ifsul.vaporepico.domain.TipoUsuario;
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
@Table(name = "usuarios")
public class UsuarioEntity implements Serializable {

   private static final long serialVersionUID = -7006384443233459887L;

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id_usuario")
   private Long id;

   @Column(name = "nome", nullable = false)
   private String nome;

   @Column(name = "email", nullable = false)
   private String email;

   @Column(name = "senha", nullable = false)
   private String senha;

   @Column(name = "data_nascimento", nullable = false)
   private LocalDate dataNascimento;

   @Column(name = "data_criacao", nullable = false)
   private LocalDate dataCriacao;

   @Enumerated(EnumType.ORDINAL)
   @Column(name = "tipo", nullable = false, columnDefinition = "ENUM('ADIMINISTRADOR', 'COMUM')")
   private TipoUsuario tipo;

   @OneToMany(mappedBy = "usuario")
   private List<JogosEntity> jogos;

   @OneToMany(mappedBy = "usuario")
   private List<CarrinhoEntity> carrinhos;
}

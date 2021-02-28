package br.com.ifsul.vaporepico.domain.entity;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsuarioRepository extends CrudRepository<UsuarioEntity, Long> {

   Optional<UsuarioEntity> findByNomeAndSenha(final String nome, final String senha);

}

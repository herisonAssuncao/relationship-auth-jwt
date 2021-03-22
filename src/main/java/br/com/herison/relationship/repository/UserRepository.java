package br.com.herison.relationship.repository;

import br.com.herison.relationship.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

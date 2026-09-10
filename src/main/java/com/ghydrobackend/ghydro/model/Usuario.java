package com.ghydrobackend.ghydro.model;

import com.ghydrobackend.ghydro.model.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id") // Boa prática para entidades usadas no Security
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    // Vínculo com a sua entidade de negócio. 
    // Para um ADMIN ou TÉCNICO, isso pode ser null. Para um PRODUTOR, terá o ID dele.
    @OneToOne
    @JoinColumn(name = "proprietario_id")
    private Proprietario proprietario;

    // =======================================================================
    // MÉTODOS OBRIGATÓRIOS DA INTERFACE UserDetails (Spring Security)
    // =======================================================================

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // É aqui que o Spring Security olha para saber o nível de acesso do usuário
        return List.of(new SimpleGrantedAuthority(this.role.getRole()));
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email; // O "username" do Spring será o nosso email
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
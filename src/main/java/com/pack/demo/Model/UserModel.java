package com.pack.demo.Model;

import lombok.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
@Data

@Entity
@Builder
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor  // ✅ Add this
@Component
@ToString
@Getter
@Setter
public class UserModel implements UserDetails{
    private String name;
    @Id
    private String id;
    private String password;
    private String email;
    private String phoneno;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       return null;
    }
    @Override
    public String getUsername() {
       return this.id;
    }
    @Override
    public String getPassword() {
       return this.password;
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

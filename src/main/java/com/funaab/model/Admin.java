package com.funaab.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
//@Indexed
@Table(name = "lecturer")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
@SequenceGenerator(
        name = "lecturer_sequence_gen",
        sequenceName = "lecturer_seq",
        allocationSize = 1)
public class Admin implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lecturer_sequence_gen")
    private Long id;

    @Column(name = "staff_id")
    private String staffId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "college")
    private String college;

    @Column(name = "role")
    private String role;


    @Column(name = "department")
    private String department;
    @JsonIgnore
    @Column(name = "password")
    private String password;
//    @ManyToOne
//    @JoinColumn(name = "admin_id")
//    private Admin admin;
@OneToMany(mappedBy = "lecturer", cascade = CascadeType.ALL, orphanRemoval = true)
private Set<Course> courses; // Courses assigned to the lecturer

    @Column(name = "status")
    private String status;

    @Column(name = "gender")
    private String gender;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "updated_date")
    private Date updatedDate;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("STUDENT"));
    }

    @Override
    public String getUsername() {
        return email;
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

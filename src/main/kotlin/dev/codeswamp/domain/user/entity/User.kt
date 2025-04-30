package dev.codeswamp.domain.user.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open val id: Long? = null;

    @Column(nullable = false, unique = true)
    var email: String? = null

    @Column(nullable = false, unique = true)
    var nickname: String? = null

    var profileUrl: String? = null

    @Column(nullable = false)
    var role: Role = Role.ROLE_GUEST
}
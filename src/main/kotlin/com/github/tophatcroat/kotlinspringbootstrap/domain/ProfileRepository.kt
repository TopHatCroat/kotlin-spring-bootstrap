package com.github.tophatcroat.kotlinspringbootstrap.domain

import com.github.tophatcroat.kotlinspringbootstrap.domain.model.Profile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProfileRepository : JpaRepository<Profile, Long>
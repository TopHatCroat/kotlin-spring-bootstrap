package com.github.tophatcroat.kotlinspringbootstrap.service

import com.github.tophatcroat.kotlinspringbootstrap.repository.UserRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service @Transactional
class UserService(val userRepository: UserRepository) {

    fun findByNameAndPassword(name: String, passwd: String) = userRepository.findByNameAndPassword(name, passwd)
}
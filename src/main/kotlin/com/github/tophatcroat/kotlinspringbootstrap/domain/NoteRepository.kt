package com.github.tophatcroat.kotlinspringbootstrap.domain

import com.github.tophatcroat.kotlinspringbootstrap.domain.model.Note
import com.github.tophatcroat.kotlinspringbootstrap.domain.model.User
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface NoteRepository : PagingAndSortingRepository<Note, Long> {
    fun findByUser(user: User): List<Note>
}

package com.github.tophatcroat.kotlinspringbootstrap.web

import com.github.tophatcroat.kotlinspringbootstrap.domain.NoteRepository
import com.github.tophatcroat.kotlinspringbootstrap.domain.model.Note
import com.github.tophatcroat.kotlinspringbootstrap.exception.DoesNotExistException
import com.github.tophatcroat.kotlinspringbootstrap.helpers.check
import com.github.tophatcroat.kotlinspringbootstrap.helpers.getUser
import com.github.tophatcroat.kotlinspringbootstrap.web.data.NoteRequest
import com.github.tophatcroat.kotlinspringbootstrap.web.data.NoteResponse
import com.github.tophatcroat.kotlinspringbootstrap.web.data.UserResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.time.Instant

@RestController
@RequestMapping("/notes", consumes = [MediaType.APPLICATION_JSON_UTF8_VALUE], produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
class NoteController(private val repository: NoteRepository) {

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun get(@PathVariable id: Long) = repository.findById(id)
            .orElseThrow { DoesNotExistException() }
            .let { NoteResponse(it.id!!, it.text, UserResponse(email = it.user!!.email), it.createdAt) }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getAll() = repository.findAll()
            .map { NoteResponse(it.id!!, it.text, UserResponse(email = it.user!!.email), it.createdAt) }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Validated @RequestBody request: NoteRequest, errors: BindingResult): NoteResponse {
        errors.check()

        var note = Note(
                text = request.text,
                user = getUser(),
                createdAt = Instant.now()
        )

        note = repository.save(note)

        return NoteResponse(note.id!!, note.text, createdAt = note.createdAt)
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun update(@PathVariable id: Long,
               @Validated @RequestBody request: NoteRequest,
               errors: BindingResult): NoteResponse {

        errors.check()

        val note = repository.findById(id).orElseThrow { DoesNotExistException() }
        note.text = request.text
        repository.save(note)

        return NoteResponse(note.id!!, note.text, createdAt = note.createdAt)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Long) = try {
        repository.deleteById(id)
    } catch (e: Exception) {
        throw DoesNotExistException()
    }

}

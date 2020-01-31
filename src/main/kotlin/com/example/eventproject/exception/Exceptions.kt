package com.example.eventproject.exception

import java.lang.RuntimeException
import java.util.*

class ResourceNotFoundException(message: String): RuntimeException(message)

class ResourceUpdateException(message: String): RuntimeException()

class ResourceCreateException(message: String): RuntimeException(message)

class ResourceDeleteException(message: String): RuntimeException()
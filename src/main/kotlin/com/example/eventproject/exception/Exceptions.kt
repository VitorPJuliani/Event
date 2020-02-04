package com.example.eventproject.exception

import java.lang.RuntimeException

class ResourceNotFoundException(message: String): RuntimeException(message)

class ResourceCreateException(message: String): RuntimeException(message)

class ResourceDeleteException(message: String): RuntimeException()